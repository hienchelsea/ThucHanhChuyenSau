package com.example.cachua.photographerapp.View.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 21/4/2018.
 */

public class HomeViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList = new ArrayList<>();
    private List<String> mFragmentTitleList = new ArrayList<>();


    public HomeViewPagerAdapter(FragmentManager fm, List<Fragment> mFragmentListt, List<String> mFragmentTitleListt) {
        super(fm);
        mFragmentList.clear();
        mFragmentTitleList.clear();
        this.mFragmentList.addAll(mFragmentListt);
        this.mFragmentTitleList.addAll(mFragmentTitleListt);

        Log.i("HomeViewPagerAdapter", "HomeViewPagerAdapter: "+mFragmentList.size());
    }

    @Override
    public Fragment getItem(int position) {
        Log.e("TAG", "GET ITEM " + position);

        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}