package com.example.cachua.photographerapp.View.Fragment.Photographer.Coupons;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Fragment.BaseFragment;
import com.example.cachua.photographerapp.View.Model.CouponsModel;
import com.example.cachua.photographerapp.View.config.Constants;
import com.example.cachua.photographerapp.View.config.SharedPreferencesUtils;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class UpdateCouponsPhotographerFragment extends BaseFragment {
    private ImageView imvTimeStart;
    private ImageView imvTimeFinish;
    private ImageView imgBack;
    private TextView tvStartDate;
    private TextView tvEndDate;
    private EditText edtName;
    private EditText edtValue;
    private EditText edtCode;
    private EditText edtDescription;
    private Button btnAdd;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private DecimalFormat df = new DecimalFormat("#00");
    long timeStart,timeEnd;
    long mm;
    String date;
    String id;
    @Override
    protected int getLayoutResource() {
            return R.layout.update_coupons_photographer_fragment;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        imvTimeStart=rootView.findViewById(R.id.imvTimeStart);
        imvTimeFinish=rootView.findViewById(R.id.imvTimeFinish);
        imgBack=rootView.findViewById(R.id.imgBack);
        tvStartDate=rootView.findViewById(R.id.tvStartDate);
        tvEndDate=rootView.findViewById(R.id.tvEndDate);
        btnAdd=rootView.findViewById(R.id.btnAdd);
        edtName=rootView.findViewById(R.id.edtName);
        edtValue=rootView.findViewById(R.id.edtValue);
        edtCode=rootView.findViewById(R.id.edtCode);
        edtDescription=rootView.findViewById(R.id.edtDescription);


        imvTimeStart.setOnClickListener(this);
        imvTimeFinish.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        btnAdd.setOnClickListener(this);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle bundle= getArguments();

        edtName.setText(bundle.getString("name"));
        edtValue.setText(bundle.getFloat("value")+"");
        edtCode.setText(bundle.getString("code"));
        edtDescription.setText(bundle.getString("description"));
        Calendar calendar= Calendar.getInstance();
        Calendar calendar1= Calendar.getInstance();
        calendar.setTimeInMillis(bundle.getLong("startAt"));
        calendar1.setTimeInMillis(bundle.getLong("updatedAt"));
        timeStart=bundle.getLong("startAt");
        timeEnd=bundle.getLong("updatedAt");
        id= bundle.getString("id");
        tvEndDate.setText(sdf.format(calendar.getTime()));
        tvStartDate.setText(sdf.format(calendar1.getTime()));


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imvTimeStart:{
                DatePicker(tvStartDate,1);
                break;
            }
            case R.id.imvTimeFinish:{
                DatePicker(tvEndDate,2);
                break;
            }
            case R.id.btnAdd:{
                if(edtName.getText().toString().equals("")==false){
                    UpdateCoupon();
                    Objects.requireNonNull(getActivity()).onBackPressed();
                }
                break;
            }
            case R.id.imgBack:{
                Objects.requireNonNull(getActivity()).onBackPressed();
                break;
            }
        }
    }
    public void DatePicker(final TextView textView, final int n){
        date="";
        final Calendar mcurrentTime = Calendar.getInstance();
        int year = mcurrentTime.get(Calendar.YEAR);
        int monthOfYear = mcurrentTime.get(Calendar.MONTH);
        int dayOfMonth = mcurrentTime.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog= new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mcurrentTime.set(Calendar.YEAR,year);
                mcurrentTime.set(Calendar.MONTH,month);
                mcurrentTime.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                mm=mcurrentTime.getTimeInMillis();
                month=month+1;
                date=df.format(dayOfMonth)+"/"+df.format(month)+"/"+year;
                textView.setText(date);
                if(n==1){
                    timeStart=mm;
                }
                else{
                    timeEnd=mm;
                }
            }
        },year,monthOfYear,dayOfMonth);

        //mm= mcurrentTime.getTimeInMillis();
        Log.i("HienNgu", mm+"DatePicker: ");
        datePickerDialog.show();
    }
    public void UpdateCoupon(){
        FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();
        DocumentReference documentReference= firebaseFirestore.collection("coupons").document(id+"");
        documentReference.update("code",edtCode.getText().toString()+"");
        documentReference.update("description",edtDescription.getText().toString()+"");
        documentReference.update("name",String.valueOf(edtName.getText().toString()));
        documentReference.update("value",Float.parseFloat(edtValue.getText().toString()));

        documentReference.update("endAt",new Timestamp(timeEnd));
        documentReference.update("startAt",new Timestamp(timeStart));
    }
}
