package com.example.cachua.photographerapp.View.Fragment.Photographer.Coupons;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.example.cachua.photographerapp.View.Model.OrdersModel;
import com.example.cachua.photographerapp.View.config.Constants;
import com.example.cachua.photographerapp.View.config.SharedPreferencesUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class AddCouponsPhotographerFragment extends BaseFragment {

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
    String date;
    @Override
    protected int getLayoutResource() {
         return R.layout.add_coupons_photographer_fragment;
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

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imvTimeStart:{

                DatePicker(tvStartDate,timeStart);
                break;
            }
            case R.id.imvTimeFinish:{
                DatePicker(tvEndDate,timeEnd);
                break;
            }
            case R.id.btnAdd:{
                if(edtName.getText().toString().equals("")==false){

                    CouponsModel couponsModel= new CouponsModel("",edtCode.getText().toString(),
                            edtDescription.getText().toString(), new Timestamp(timeEnd),edtName.getText().toString(),
                            new Timestamp(timeEnd),new Timestamp(timeEnd), SharedPreferencesUtils.getString(mContext, Constants.ID_USER),
                            (float) 0.2);

                    AddCoupons(couponsModel);
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
    public void DatePicker(final TextView textView,long mm){
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

                month=month+1;
                date=df.format(dayOfMonth)+"/"+df.format(month)+"/"+year;
                textView.setText(date);
            }
        },year,monthOfYear,dayOfMonth);

        mm= mcurrentTime.getTimeInMillis();
        datePickerDialog.show();
    }
    private void AddCoupons(CouponsModel couponsModel) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("coupons")
                .add(couponsModel)
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

}
