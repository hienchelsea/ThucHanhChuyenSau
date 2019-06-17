package com.example.cachua.photographerapp.View.Fragment.Photographer.Messenger;

import android.os.Bundle;
import android.view.View;

import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Fragment.BaseFragment;
import com.example.cachua.photographerapp.View.Fragment.Photographer.List.ListPhotographerFragment;

public class MessengerPhotographerFragment extends BaseFragment {

    public static MessengerPhotographerFragment sInstance;

    public static MessengerPhotographerFragment newInstance() {
        if (sInstance == null) {
            sInstance = new MessengerPhotographerFragment();
        }
        return sInstance;
    }
    @Override
    protected int getLayoutResource() {
        return R.layout.messenger_photographer_fragment;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {

    }
}
