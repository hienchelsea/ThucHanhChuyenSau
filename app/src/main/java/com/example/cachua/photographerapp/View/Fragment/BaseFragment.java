package com.example.cachua.photographerapp.View.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/* Created by HoangTV on 05/25/17.
* */

public abstract class BaseFragment extends Fragment implements View.OnClickListener{

    protected abstract int getLayoutResource();

    protected abstract void initVariables(Bundle savedInstanceState, View rootView);

    protected abstract void initData(Bundle savedInstanceState);

    protected BroadcastReceiver mReceiver;
    protected Context mContext;



    public BaseFragment() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutResource(), container, false);
        initVariables(savedInstanceState, rootView);
        initData(savedInstanceState);
        initBroadCastReceiver();
        return rootView;
    }

    public void initBroadCastReceiver() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mReceiver != null) {
            getActivity().unregisterReceiver(mReceiver);
            mReceiver = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        initBroadCastReceiver();
    }
}
