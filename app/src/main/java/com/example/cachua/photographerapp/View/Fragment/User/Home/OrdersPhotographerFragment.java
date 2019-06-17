package com.example.cachua.photographerapp.View.Fragment.User.Home;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Activity.LoginActivity;
import com.example.cachua.photographerapp.View.Fragment.BaseFragment;
import com.example.cachua.photographerapp.View.Model.AlbumModel;
import com.example.cachua.photographerapp.View.Model.CouponsModel;
import com.example.cachua.photographerapp.View.Model.ImagesModel;
import com.example.cachua.photographerapp.View.Model.OrdersModel;
import com.example.cachua.photographerapp.View.config.Constants;
import com.example.cachua.photographerapp.View.config.SharedPreferencesUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrdersPhotographerFragment extends BaseFragment {
    private Dialog dialog;
    private Button btnOrder;
    private ImageView imvTimeStart;
    private ImageView imvTimeFinish;
    private CircleImageView imgAvatar;
    private ImageView imgBack;
    private RadioButton rbtnHour;
    private RadioButton rbtnDay;
    private LinearLayout llTimeHour;
    private TextView tvTimeFinish;
    private TextView tvTimeStart;
    private TextView tvTotal;
    private TextView tvCoupons;
    private TextView tvRating;
    private TextView tvCheckCoupons;
    private ImageView imvDateStart;
    private ImageView imvDateFinish;
    private TextView tvStartDate;
    private TextView tvDateFinish;
    private TextView tvPrice_per_hour;
    private TextView tvPrice_per_day;
    private EditText edtAddress;
    private EditText edtNote;
    private EditText edtCoupons;
    private TextView  tvName;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private DecimalFormat df = new DecimalFormat("#00");
    String date;
    String time;
    String codeCoupons;
    String descriptionCoupons;
    String nameCoupons;
    Float valueCoupons= Float.valueOf(1);
    float total = 0;
    long timeDate = -1;
    Bundle bundle;
    int checkCoupon=0;
    int status = 0;//0 la start, 1 la finish
    Calendar calendarStart = Calendar.getInstance();
    Calendar calendarFinish = Calendar.getInstance();


    @Override
    protected int getLayoutResource() {
        return (R.layout.fragment_oders_photographer);
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        btnOrder = rootView.findViewById(R.id.btnOrder);
        rbtnHour = rootView.findViewById(R.id.rbtnHour);
        rbtnDay = rootView.findViewById(R.id.rbtnDay);
        llTimeHour = rootView.findViewById(R.id.llTimeHour);
        imvTimeStart = rootView.findViewById(R.id.imvTimeStart);
        imvTimeFinish = rootView.findViewById(R.id.imvTimeFinish);
        imgBack = rootView.findViewById(R.id.imgBack);
        tvTimeFinish = rootView.findViewById(R.id.tvTimeFinish);
        tvTimeStart = rootView.findViewById(R.id.tvTimeStart);
        imvDateStart = rootView.findViewById(R.id.imvDateStart);
        imvDateFinish = rootView.findViewById(R.id.imvDateFinish);
        tvStartDate = rootView.findViewById(R.id.tvStartDate);
        tvDateFinish = rootView.findViewById(R.id.tvDateFinish);
        tvCoupons = rootView.findViewById(R.id.tvCoupons);
        tvCheckCoupons = rootView.findViewById(R.id.tvCheckCoupons);
        edtAddress = rootView.findViewById(R.id.edtAddress);
        edtNote = rootView.findViewById(R.id.edtNote);
        edtCoupons = rootView.findViewById(R.id.edtCoupons);
        tvPrice_per_day = rootView.findViewById(R.id.tvPrice_per_day);
        tvPrice_per_hour = rootView.findViewById(R.id.tvPrice_per_hour);
        tvTotal = rootView.findViewById(R.id.tvTotal);
        imgAvatar = rootView.findViewById(R.id.imgAvatar);
        tvName = rootView.findViewById(R.id.tvName);
        tvRating = rootView.findViewById(R.id.tvRating);


        btnOrder.setOnClickListener(this);
        rbtnDay.setOnClickListener(this);
        rbtnHour.setOnClickListener(this);
        imvTimeStart.setOnClickListener(this);
        imvTimeFinish.setOnClickListener(this);
        imvDateStart.setOnClickListener(this);
        imvDateFinish.setOnClickListener(this);
        tvCheckCoupons.setOnClickListener(this);


    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        bundle = getArguments();
        tvPrice_per_day.setText(bundle.getFloat("price_per_day") + "");
        tvPrice_per_hour.setText(bundle.getFloat("price_per_hour") + "");
        tvName.setText(bundle.getString("name") + "");
        tvRating.setText(bundle.getString("rating") + "");
        Glide.with(mContext).load(bundle.getString("avatarLink")).skipMemoryCache(true).into(imgAvatar);


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:{
                Objects.requireNonNull(getActivity()).onBackPressed();
                break;
            }
            case R.id.tvCheckCoupons:{
                LoadDataCoupon(edtCoupons.getText().toString());
                break;
            }
            case R.id.imvDateStart: {
                status = 0;
                DatePicker(tvStartDate);

                break;
            }
            case R.id.imvDateFinish: {
                status = 1;
                DatePicker(tvDateFinish);

                break;
            }

            case R.id.imvTimeFinish: {
                status = 1;
                TimePicker(tvTimeFinish);
                break;
            }

            case R.id.imvTimeStart: {
                status = 0;
                TimePicker(tvTimeStart);
                break;
            }

            case R.id.rbtnDay: {
                if (rbtnDay.isChecked() == true) {
                    tvTotal.setText("");
                    timeDate = -1;
                    llTimeHour.setVisibility(View.GONE);
                    LoadTotal();
                }
                break;
            }
            case R.id.rbtnHour: {
                if (rbtnHour.isChecked() == true) {
                    tvTotal.setText("");
                    timeDate = -1;
                    llTimeHour.setVisibility(View.VISIBLE);
                    LoadTotal();
                }
                break;
            }
            case R.id.btnOrder: {
                if(timeDate>=0){
                    Map coupon = new HashMap();
                    Map option = new HashMap();
                    if(checkCoupon==1){
                        coupon.put("code", codeCoupons);
                        coupon.put("description", descriptionCoupons);
                        coupon.put("name", nameCoupons);
                        coupon.put("value", valueCoupons);
                    }
                    else{
                        coupon.put("code", "");
                        coupon.put("description", "");
                        coupon.put("name", "");
                        coupon.put("value", "");
                    }


                    option.put("description", bundle.getString("description"));
                    option.put("name", bundle.getString("name"));
                    option.put("price_per_day", bundle.getFloat("price_per_day"));
                    option.put("price_per_hour", bundle.getFloat("price_per_hour"));
                    option.put("prints", bundle.getInt("prints"));
                    option.put("shots", bundle.getInt("shots"));
                    option.put("type", bundle.getString("type"));
                    Timestamp endAt = new Timestamp(calendarFinish.getTimeInMillis());
                    Timestamp startAt = new Timestamp(calendarStart.getTimeInMillis());
                    OrdersModel ordersModel = new OrdersModel("",edtAddress.getText().toString(), coupon, endAt,
                            SharedPreferencesUtils.getString(mContext, Constants.LOCATION_USER), edtNote.getText().toString(), option, bundle.getString("idPhotographer"), startAt,
                            "pending", Double.valueOf(tvTotal.getText().toString()), SharedPreferencesUtils.getString(mContext,Constants.ID_USER));
                      AddOderData(ordersModel);
                    showDialog();
                }
            }

            break;
        }
    }

    private void LoadTotal() {
        Timestamp endAt = new Timestamp(calendarFinish.getTimeInMillis());
        Timestamp startAt = new Timestamp(calendarStart.getTimeInMillis());
        if (rbtnDay.isChecked() == true && tvDateFinish.getText().toString().equals("") == false
                && tvStartDate.getText().toString().equals("") == false) {
            Calendar calendarAbc = Calendar.getInstance();
            calendarStart.set(Calendar.HOUR_OF_DAY, calendarAbc.get(Calendar.HOUR_OF_DAY));
            calendarFinish.set(Calendar.HOUR_OF_DAY, calendarAbc.get(Calendar.HOUR_OF_DAY));
            startAt = new Timestamp(calendarStart.getTimeInMillis());
            endAt = new Timestamp(calendarFinish.getTimeInMillis());
            timeDate = endAt.getTime() - startAt.getTime();
        } else {
            if (rbtnHour.isChecked() == true && tvTimeStart.getText().toString().equals("") == false
                    && tvTimeFinish.getText().toString().equals("") == false
                    && tvDateFinish.getText().toString().equals("") == false
                    && tvStartDate.getText().toString().equals("") == false) {
                startAt = new Timestamp(calendarStart.getTimeInMillis());
                endAt = new Timestamp(calendarFinish.getTimeInMillis());
                timeDate = endAt.getTime() - startAt.getTime();
            } else {
                //Toast.makeText(mContext, "Vui long chon thoi gian", Toast.LENGTH_SHORT).show();
            }

        }
        if (timeDate >= 0) {
            if (rbtnHour.isChecked() == true) {
                int hour = (int) (timeDate / 60 / 60 / 1000);
                if (hour * 60 * 60 * 1000 <= timeDate) {
                    hour = hour + 1;
                }
                Log.i("HienNgu", hour + "onClick: ");
                if(checkCoupon==0){
                    total = hour * bundle.getFloat("price_per_hour");
                }
                else{
                    total = hour * bundle.getFloat("price_per_hour")-hour * bundle.getFloat("price_per_hour")*valueCoupons;
                }

                tvTotal.setText(total+"");

            }

            if (rbtnDay.isChecked() == true) {
                int date = (int) (timeDate / 60 / 60 / 24 / 1000);
                Log.i("HienNgu", date + "onClick: ");
                if (date * 24 * 60 * 60 * 1000 <= timeDate) {
                    date = date + 1;
                }
                if(checkCoupon==0){
                    total = date * bundle.getFloat("price_per_day");
                }
                else{
                    total = date * bundle.getFloat("price_per_day")-date * bundle.getFloat("price_per_day")*valueCoupons;
                }
                tvTotal.setText(total+"");

            }
        }

    }


    private void AddOderData(OrdersModel ordersModel) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("order")
                .add(ordersModel)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(final DocumentReference documentReference) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }


    public void showDialog() {

        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dailog_thanhk_ofter);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Window window = dialog.getWindow();
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        Button btnOk= dialog.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Objects.requireNonNull(getActivity()).onBackPressed();
            }
        });

        dialog.show();
    }

    public void showDialogTime() {

        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dailog_seclect_time);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Window window = dialog.getWindow();
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);

        RadioButton rbtnFullTime = dialog.findViewById(R.id.rbtnFullTime);
        RadioButton rbtnMorning = dialog.findViewById(R.id.rbtnMorning);
        RadioButton rbtnAfternoon = dialog.findViewById(R.id.rbtnAfternoon);
        RadioButton rbtnNight = dialog.findViewById(R.id.rbtnNight);

        rbtnFullTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    tvTime.setText("Ca ngay (7h-22h)");
                dialog.dismiss();
            }
        });
        rbtnMorning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  tvTime.setText("Sang (7h-11h)");
                dialog.dismiss();
            }
        });
        rbtnAfternoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // tvTime.setText("Chieu (13h-17h)");
                dialog.dismiss();
            }
        });
        rbtnNight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  tvTime.setText("Toi (19h-23h)");
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    public void DatePicker(final TextView textView) {
        date = "";
        Calendar mcurrentTime = Calendar.getInstance();
        int year = mcurrentTime.get(Calendar.YEAR);
        int monthOfYear = mcurrentTime.get(Calendar.MONTH);
        int dayOfMonth = mcurrentTime.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                if (status == 1) {
                    calendarFinish.set(Calendar.YEAR, year);
                    calendarFinish.set(Calendar.MONTH, month);
                    calendarFinish.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                } else {
                    calendarStart.set(Calendar.YEAR, year);
                    calendarStart.set(Calendar.MONTH, month);
                    calendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                }
                month = month + 1;
                date = df.format(dayOfMonth) + "/" + df.format(month) + "/" + year;
                textView.setText(date);
                LoadTotal();
            }
        }, year, monthOfYear, dayOfMonth);
        datePickerDialog.show();
    }

    public void TimePicker(final TextView textView) {
        time = "";
        Calendar calendar = Calendar.getInstance();
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (status == 1) {
                    calendarFinish.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendarFinish.set(Calendar.MINUTE, minute);
                } else {
                    calendarStart.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendarStart.set(Calendar.MINUTE, minute);
                }
                date = df.format(hourOfDay) + ":" + df.format(minute);
                textView.setText(date);
                LoadTotal();
            }
        }, hour, minute, true);

        timePickerDialog.show();
    }
    private void LoadDataCoupon(String s) {
        checkCoupon=0;
        Log.i("Hiennnnn", s+"LoadDataCoupon: ");
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        db.collection("coupons")
                .whereEqualTo("code",s+"")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                codeCoupons=String.valueOf(documentSnapshot.getData().get("code"));
                                descriptionCoupons=String.valueOf(documentSnapshot.getData().get("description"));
                                nameCoupons=String.valueOf(documentSnapshot.getData().get("name"));
                                valueCoupons=Float.parseFloat(String.valueOf(documentSnapshot.getData().get("value")));
                                checkCoupon=1;
                                Log.i("Hienuyyuu", String.valueOf(documentSnapshot.getData().get("startAt"))+String.valueOf(documentSnapshot.getData().get("updatedAt")));
                            }
                            if(checkCoupon==0){
                                tvCoupons.setText("Khuyen mai mai khong kha dung");
                                tvTotal.setText(total+"");
                            }
                            else{
                                tvTotal.setText((total-total*valueCoupons)+"");
                                tvCoupons.setText("Khuyen mai giam "+valueCoupons);
                            }
                            tvCoupons.setVisibility(View.VISIBLE);



                        }
                    }
                });

    }
}
