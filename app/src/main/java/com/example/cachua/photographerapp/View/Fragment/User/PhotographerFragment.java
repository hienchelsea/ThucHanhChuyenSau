package com.example.cachua.photographerapp.View.Fragment.User;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Adapter.FavoritePhotographerAdapter;
import com.example.cachua.photographerapp.View.Fragment.BaseFragment;

import java.util.ArrayList;

public class PhotographerFragment extends BaseFragment {


    private RecyclerView rcHomeHighlight;
    private FavoritePhotographerAdapter favoritePhotographerAdapter;

    public static PhotographerFragment sInstance;
    public static PhotographerFragment newInstance() {
        if (sInstance == null) {
            sInstance = new PhotographerFragment();
        }
        return sInstance;
    }

    @Override
    protected int getLayoutResource() {
     return (R.layout.fragment_photographer);
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        rcHomeHighlight= rootView.findViewById(R.id.rcHomeHighlight);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {


    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onResume() {
        super.onResume();

//        favoritePhotographerAdapter = new FavoritePhotographerAdapter(mContext);
//        rcHomeHighlight.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
//        rcHomeHighlight.setAdapter(favoritePhotographerAdapter);
    }
}
