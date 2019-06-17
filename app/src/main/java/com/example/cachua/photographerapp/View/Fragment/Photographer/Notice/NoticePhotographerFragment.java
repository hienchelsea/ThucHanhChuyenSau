package com.example.cachua.photographerapp.View.Fragment.Photographer.Notice;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Adapter.NoticeAdapter;
import com.example.cachua.photographerapp.View.Fragment.BaseFragment;
import com.example.cachua.photographerapp.View.Fragment.Photographer.Options.OptionPhotographerFragment;
import com.example.cachua.photographerapp.View.Model.NoticeModel;

import java.util.ArrayList;

public class NoticePhotographerFragment extends BaseFragment {
    private RecyclerView rcNotice;
    private NoticeAdapter noticeAdapter;
    private ArrayList<NoticeModel> noticeModelArrayList;


    public static NoticePhotographerFragment sInstance;

    public static NoticePhotographerFragment newInstance() {
        if (sInstance == null) {
            sInstance = new NoticePhotographerFragment();
        }
        return sInstance;
    }
    @Override
    protected int getLayoutResource() {
        return R.layout.notice_photographer_fragment;
    }

    protected void initVariables(Bundle savedInstanceState, View rootView) {
        rcNotice= rootView.findViewById(R.id.rcNotice);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        noticeModelArrayList= new ArrayList<>();
//        noticeModelArrayList.add(new NoticeModel(1,"Hien Do","abc","1 gio truoc","Khuyen mai"));
//        noticeModelArrayList.add(new NoticeModel(2,"Minh Hoang","abc","2 gio truoc","Chup anh"));
//        noticeModelArrayList.add(new NoticeModel(3,"Leu Leu","abc","3 gio truoc","Khuyen mai"));
        noticeAdapter= new NoticeAdapter(noticeModelArrayList,mContext);
        rcNotice.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        rcNotice.setAdapter(noticeAdapter);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
