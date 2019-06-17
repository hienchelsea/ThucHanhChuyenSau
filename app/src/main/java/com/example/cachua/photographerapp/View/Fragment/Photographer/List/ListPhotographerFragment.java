package com.example.cachua.photographerapp.View.Fragment.Photographer.List;

import android.app.ActionBar;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;

import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Adapter.ListWaitAdapter;
import com.example.cachua.photographerapp.View.Fragment.BaseFragment;
import com.example.cachua.photographerapp.View.Fragment.Photographer.Schedule.SchedulePhotographerFragment;
import com.example.cachua.photographerapp.View.Model.OrdersModel;
import com.example.cachua.photographerapp.View.config.Constants;
import com.example.cachua.photographerapp.View.config.SharedPreferencesUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ListPhotographerFragment extends BaseFragment {
    private RecyclerView rcListWait;
    private ArrayList<OrdersModel> ordersModelArrayList;
    private ListWaitAdapter listWaitAdapter;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    public static ListPhotographerFragment sInstance;

    public static ListPhotographerFragment newInstance() {
        if (sInstance == null) {
            sInstance = new ListPhotographerFragment();
        }
        return sInstance;
    }

    @Override
    protected int getLayoutResource() {
            return (R.layout.shedule_photographer_list);
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        rcListWait= rootView.findViewById(R.id.rcListWait);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ordersModelArrayList= new ArrayList<>();
        listWaitAdapter= new ListWaitAdapter(mContext,ordersModelArrayList);
        rcListWait.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        rcListWait.setAdapter(listWaitAdapter);
        LoadDataOrder(ordersModelArrayList);

    }

    @Override
    public void onClick(View v) {

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
                                if(documentSnapshot.getData().get("status").equals("pending")==true){
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
                                    //  saveDateStringArrayList.add(dayy);
                                    String[] dayArray = dayy.split("-");
                                }

                            }

                            listWaitAdapter.Update(mContext,ordersModelArrayList1);
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
