package com.example.cachua.photographerapp.View.Fragment.Photographer.Schedule;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Activity.MainPhotographerActivity;
import com.example.cachua.photographerapp.View.Adapter.ProfilePhotographerHistoryAdapter;
import com.example.cachua.photographerapp.View.Adapter.ScheduleAdapter;
import com.example.cachua.photographerapp.View.Fragment.BaseFragment;
import com.example.cachua.photographerapp.View.Fragment.User.Home.HomeFragment;
import com.example.cachua.photographerapp.View.Model.CouponsModel;
import com.example.cachua.photographerapp.View.Model.OrdersModel;
import com.example.cachua.photographerapp.View.config.Constants;
import com.example.cachua.photographerapp.View.config.SharedPreferencesUtils;
import com.example.photographer.hienoc.library.CompactCalendarView;
import com.example.photographer.hienoc.library.domain.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class SchedulePhotographerFragment extends BaseFragment {

    private CompactCalendarView compactCalendarView;
    private TextView tvMonth;
    private TextView tvCalender;
    private RecyclerView rcSchedule;
    private ScheduleAdapter scheduleAdapter;
    private ArrayList<String> saveDateStringArrayList = new ArrayList<>();
    ArrayList<OrdersModel> ordersModelArrayList = new ArrayList<>();
    private Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
    private SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("dd-M-yyyy hh:mm:ss a", Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private DecimalFormat df = new DecimalFormat("#00");

    public static SchedulePhotographerFragment sInstance;

    public static SchedulePhotographerFragment newInstance() {
        if (sInstance == null) {
            sInstance = new SchedulePhotographerFragment();
        }
        return sInstance;
    }

    @Override
    protected int getLayoutResource() {
        return (R.layout.shedule_photographer_fragment);
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        compactCalendarView = rootView.findViewById(R.id.compactCalendarView);
        tvMonth = rootView.findViewById(R.id.tvMonth);
        rcSchedule = rootView.findViewById(R.id.rcSchedule);
        tvCalender = rootView.findViewById(R.id.tvCalender);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ordersModelArrayList = new ArrayList<>();
        saveDateStringArrayList = new ArrayList<>();

        LoadDataOrder(ordersModelArrayList);
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        compactCalendarView.setIsRtl(false);
        compactCalendarView.displayOtherMonthDays(false);
        tvCalender.setText("Thang " + (currentCalender.getTime().getMonth() + 1));
        tvMonth.setText("Thang " + (currentCalender.getTime().getMonth() + 1));
        compactCalendarView.invalidate();
        logEventsByMonth(compactCalendarView);
        logEventsByMonth(compactCalendarView);


        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            ArrayList<OrdersModel> ordersModelArrayListSelect = new ArrayList<>();

            @Override
            public void onDayClick(Date dateClicked) {
                int day = dateClicked.getDate();
                int month = dateClicked.getMonth() + 1;
                int year = dateClicked.getYear() + 1900;
                ArrayList<Integer> selectDayArrayList = new ArrayList<>();
                String date = df.format(day) + "-" + df.format(month) + "-" + year;
                Log.i("HienOcChohihi", date + "onDayClick: ");
                tvCalender.setText(date);
                for (int j = 0; j < saveDateStringArrayList.size(); j++) {
                    if (date.equals(saveDateStringArrayList.get(j)) == true) {
                        selectDayArrayList.add(j);
                    }
                }
                if (selectDayArrayList.size() == 0) {
                    ordersModelArrayListSelect.clear();
                } else {
                    ordersModelArrayListSelect.clear();
                    for (int j = 0; j < selectDayArrayList.size(); j++) {
                        ordersModelArrayListSelect.add(ordersModelArrayList.get(selectDayArrayList.get(j)));
                    }

                }
                scheduleAdapter = new ScheduleAdapter(mContext, ordersModelArrayListSelect);
                rcSchedule.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                rcSchedule.setAdapter(scheduleAdapter);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {

                tvMonth.setText("Thang " + (firstDayOfNewMonth.getMonth() + 1));
                tvCalender.setText("Thang " + (firstDayOfNewMonth.getMonth() + 1));
                ordersModelArrayListSelect.clear();
                scheduleAdapter = new ScheduleAdapter(mContext, ordersModelArrayListSelect);
                rcSchedule.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                rcSchedule.setAdapter(scheduleAdapter);

            }
        });


    }


    @Override
    public void onClick(View v) {

    }


    private void addEvents(int day, int month, int year) {
        day = day - 1;
        month = month - 1;
        currentCalender.setTime(new Date());
        currentCalender.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayOfMonth = currentCalender.getTime();
        currentCalender.setTime(firstDayOfMonth);
        currentCalender.set(Calendar.MONTH, month);
        currentCalender.set(Calendar.YEAR, year);
        currentCalender.add(Calendar.DATE, day);
        setToMidnight(currentCalender);
        long timeInMillis = currentCalender.getTimeInMillis();

        List<Event> events = getEvents(timeInMillis, day);
        compactCalendarView.addEvents(events);

    }

    private List<Event> getEvents(long timeInMillis, int day) {
        return Arrays.asList(
                new Event(Color.argb(255, 0, 0, 255), timeInMillis, "Event at " + new Date(timeInMillis)),
                new Event(Color.argb(255, 0, 0, 255), timeInMillis, "Event at " + new Date(timeInMillis)));
    }

    private void setToMidnight(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    private void logEventsByMonth(CompactCalendarView compactCalendarView) {
        currentCalender.setTime(new Date());
        currentCalender.set(Calendar.DAY_OF_MONTH, 1);
        currentCalender.set(Calendar.MONTH, Calendar.AUGUST);
        List<String> dates = new ArrayList<>();
        for (Event e : compactCalendarView.getEventsForMonth(new Date())) {
            dates.add(dateFormatForDisplaying.format(e.getTimeInMillis()));
        }

    }

    private void LoadDataOrder(final ArrayList<OrdersModel> ordersModelArrayList1) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("order")
                .whereEqualTo("photographerId", SharedPreferencesUtils.getString(mContext, Constants.ID_USER) + "")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {


                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                if(documentSnapshot.getData().get("status").equals("confirmed")==true){
                                    OrdersModel ordersModel = new OrdersModel();
                                    ordersModel.setId(documentSnapshot.getId()+"");
                                    ordersModel.setPhotographerId(SharedPreferencesUtils.getString(mContext, Constants.ID_USER) + "");
                                    ordersModel.setTotal(Double.parseDouble(String.valueOf(documentSnapshot.getData().get("total"))));
                                    ordersModel.setUserId(String.valueOf(documentSnapshot.getData().get("userId")));
                                    ordersModel.setAddress(String.valueOf(documentSnapshot.getData().get("address")));
                                    ordersModel.setNote(String.valueOf(documentSnapshot.getData().get("note")));
                                    ordersModel.setStartAt(date(String.valueOf((documentSnapshot.getData().get("startAt")))));
                                    ordersModel.setEndAt(date(String.valueOf((documentSnapshot.getData().get("endAt")))));

                                    ordersModelArrayList1.add(ordersModel);
                                    Timestamp timestamp = ordersModel.getStartAt();
                                    long mm = timestamp.getTime();
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.setTimeInMillis(mm);
                                    String dayy = sdf.format(calendar.getTime());
                                    Log.i("HienOcChohihi", dayy + "onComplete: ");
                                    saveDateStringArrayList.add(dayy);
                                    String[] dayArray = dayy.split("-");
                                    addEvents(Integer.parseInt(dayArray[0]), Integer.parseInt(dayArray[1]), Integer.parseInt(dayArray[2]));
                                }

                            }

                        }
                    }
                });
    }

    public Timestamp date(String s) {
        String[] catChuoi = s.split(",");
        String[] catChuoiTiep = catChuoi[0].split("=");
        Timestamp timestamp = new Timestamp(Long.parseLong(catChuoiTiep[1]) * 1000);
        return timestamp;
    }

}
