package com.example.cachua.photographerapp.View.Fragment.Login;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Activity.LoginActivity;
import com.example.cachua.photographerapp.View.Fragment.BaseFragment;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class RegistrationStep1Fragment extends BaseFragment{
    private Button btnStep2;
    private EditText edtName;
    private EditText edtFirstName;
    private RadioButton rbtnNam;
    private RadioButton rbtnNu;
    private TextView tvBirth;
    private EditText edtNumber;
    private EditText edtAddress;
    private ImageView imvDate;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private DecimalFormat df = new DecimalFormat("#00");
    String date;

    @Override
    protected int getLayoutResource() {
        return (R.layout.registration_fragment);
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        btnStep2= rootView.findViewById(R.id.btnStep2);
        edtName= rootView.findViewById(R.id.edtName);
        edtFirstName= rootView.findViewById(R.id.edtFirstName);
        rbtnNam= rootView.findViewById(R.id.rbtnNam);
        rbtnNu= rootView.findViewById(R.id.rbtnNu);
        tvBirth= rootView.findViewById(R.id.tvBirth);
        edtNumber= rootView.findViewById(R.id.edtNumber);
        edtAddress= rootView.findViewById(R.id.edtAddress);
        imvDate= rootView.findViewById(R.id.imvDate);


        btnStep2.setOnClickListener(this);
        imvDate.setOnClickListener(this);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnStep2:{
               // String email= edtEmail.getText().toString();
                ArrayList<String> stringArrayList= new ArrayList<>();
                stringArrayList.add(edtFirstName.getText().toString()+" "+edtName.getText().toString());
                if(rbtnNam.isChecked()==true){
                    stringArrayList.add("Nam");
                }
                else{
                    if(rbtnNu.isChecked()==true){
                        stringArrayList.add("Nu");
                    }
                }

                stringArrayList.add(tvBirth.getText().toString());
                stringArrayList.add(edtNumber.getText().toString());
                stringArrayList.add(edtAddress.getText().toString());
                int ktra=0;
                for(int i=0;i<stringArrayList.size();i++){
                    if (stringArrayList.get(i).equals("")==true){
                        ktra=1;
                    }
                }
                if(ktra==1){
                    Toast.makeText(mContext, "Vui long dien day du thong tin", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(ktra==0){
                        Bundle bundle= new Bundle();
                      //  bundle.putString("Hienoc","abcccc");
                        bundle.putStringArrayList("step1",stringArrayList);
                        RegistrationStep2Fragment registrationFragment = new RegistrationStep2Fragment();
                        registrationFragment.setArguments(bundle);
                        ((LoginActivity) Objects.requireNonNull(getActivity())).nextFragment(registrationFragment, R.id.tabContainer, 0, 0, 0, 0);
                    }
                }

                break;
            }
            case R.id.imvDate:{
                DatePicker(tvBirth);
                break;
            }
        }

    }
    public void DatePicker(final TextView textView){
        date="";
        Calendar mcurrentTime = Calendar.getInstance();
        int year = mcurrentTime.get(Calendar.YEAR);
        int monthOfYear = mcurrentTime.get(Calendar.MONTH);
        int dayOfMonth = mcurrentTime.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog= new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                date=df.format(dayOfMonth)+"/"+df.format(month)+"/"+year;
                textView.setText(date);
            }
        },year,monthOfYear,dayOfMonth);
        datePickerDialog.show();
    }


}
