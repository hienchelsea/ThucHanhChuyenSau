package com.example.cachua.photographerapp.View.Fragment.User.Home;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;

import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Fragment.BaseFragment;

import java.util.Objects;

public class ProfileDetailPhotographerFragment extends BaseFragment {
    private ImageView imgBack;

    @Override
    protected int getLayoutResource() {
        return (R.layout.fragment_profile_home_photographer_detail);
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        imgBack=rootView.findViewById(R.id.imgBack);

        imgBack.setOnClickListener(this);


    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgBack:{
                Objects.requireNonNull(getActivity()).onBackPressed();
                break;
            }

        }

    }
}
