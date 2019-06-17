package com.example.cachua.photographerapp.View.Fragment.User.Profile;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Fragment.BaseFragment;

import java.util.Objects;

public class ProfilePasswordFragment extends BaseFragment {
    private ImageView imgBack;
    @Override
    protected int getLayoutResource() {
        return (R.layout.fragment_profile_password);
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        imgBack= rootView.findViewById(R.id.imgBack);


        imgBack.setOnClickListener(this);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

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
