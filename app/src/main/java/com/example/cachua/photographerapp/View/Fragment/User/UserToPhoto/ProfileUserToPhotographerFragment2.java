package com.example.cachua.photographerapp.View.Fragment.User.UserToPhoto;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Activity.MainUserActivity;
import com.example.cachua.photographerapp.View.Fragment.BaseFragment;

import java.util.Objects;

public class ProfileUserToPhotographerFragment2 extends BaseFragment {
    private Button btnNextStep;
    private EditText edtNameOption;
    private EditText edtType;
    private EditText edtCountImg;
    private EditText edtTotalDay;
    private EditText edtTotalHour;
    private EditText edtIntroduce;
    private EditText edtPrintImg;
    Bundle bundle;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_profile_user_to_grapher_2;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        btnNextStep= rootView.findViewById(R.id.btnNextStep);
        edtNameOption= rootView.findViewById(R.id.edtNameOption);
        edtType= rootView.findViewById(R.id.edtType);
        edtCountImg= rootView.findViewById(R.id.edtCountImg);
        edtTotalDay= rootView.findViewById(R.id.edtTotalDay);
        edtTotalHour= rootView.findViewById(R.id.edtTotalHour);
        edtIntroduce= rootView.findViewById(R.id.edtIntroduce);
        edtPrintImg= rootView.findViewById(R.id.edtPrintImg);

        btnNextStep.setOnClickListener(this);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        bundle= getArguments();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnNextStep:{
                if(edtNameOption.getText().toString().equals("")==false){
                    bundle.putString("nameOption",edtNameOption.getText().toString());
                    bundle.putString("type",edtType.getText().toString());
                    bundle.putInt("countImv", Integer.parseInt(edtCountImg.getText().toString()));
                    bundle.putInt("printImv", Integer.parseInt(edtPrintImg.getText().toString()));
                    bundle.putFloat("totalDay", Float.parseFloat(edtTotalDay.getText().toString()));
                    bundle.putFloat("totalHour", Float.parseFloat(edtTotalHour.getText().toString()));
                    bundle.putString("introduceOption",edtIntroduce.getText().toString());
                    ProfileUserToPhotographerFragment3 profileUserToPhotographerFragment= new ProfileUserToPhotographerFragment3();
                    profileUserToPhotographerFragment.setArguments(bundle);
                    ((MainUserActivity) Objects.requireNonNull(mContext)).nextFragment(profileUserToPhotographerFragment,R.id.container,0,0,0,0);
                }


                break;
            }
        }

    }
}
