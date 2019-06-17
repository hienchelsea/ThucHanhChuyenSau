package com.example.cachua.photographerapp.View.Fragment.Photographer.Profile;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Adapter.ProfileReviewAdapter;
import com.example.cachua.photographerapp.View.Fragment.BaseFragment;
import com.example.cachua.photographerapp.View.Model.ReviewsModel;

import java.util.ArrayList;

public class ProfilePhotographerReviewFragment extends BaseFragment {
    private RecyclerView rcReview;
    private ProfileReviewAdapter profileReviewAdapter;
    private ArrayList<ReviewsModel> reviewsModels;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_profile_photographer_review;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        rcReview= rootView.findViewById(R.id.rcReview);

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
        reviewsModels= new ArrayList<>();
//        reviewsModels.add(new ReviewsModel(1,"Hien Oc","Leu Leu","Chup anh dep vl",8.5));
//        reviewsModels.add(new ReviewsModel(2,"Hien Oc","Thang dien","Chup ngu oi la ngu",10));
//        reviewsModels.add(new ReviewsModel(3,"Hien Oc","ahuhu","chup depppppp",10));
        profileReviewAdapter= new ProfileReviewAdapter(mContext,reviewsModels);
        rcReview.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        rcReview.setAdapter(profileReviewAdapter);
    }
}
