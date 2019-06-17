package com.example.cachua.photographerapp.View.Fragment.User.Profile;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Adapter.ProfileGiftAdapter;
import com.example.cachua.photographerapp.View.Fragment.BaseFragment;

public class ProfileGiftFragment extends BaseFragment {
    private RecyclerView rcGift;
    private ProfileGiftAdapter profileGiftAdapter;
    @Override
    protected int getLayoutResource() {
        return (R.layout.fragment_profile_girt);
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        rcGift= rootView.findViewById(R.id.rcGift);

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
        profileGiftAdapter= new ProfileGiftAdapter(mContext);
        rcGift.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        rcGift.setAdapter(profileGiftAdapter);

    }
}
