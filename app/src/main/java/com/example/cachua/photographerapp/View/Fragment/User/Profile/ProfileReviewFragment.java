package com.example.cachua.photographerapp.View.Fragment.User.Profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Adapter.ProfileReviewAdapter;
import com.example.cachua.photographerapp.View.Fragment.BaseFragment;
import com.example.cachua.photographerapp.View.Model.ReviewsModel;
import com.example.cachua.photographerapp.View.config.Constants;
import com.example.cachua.photographerapp.View.config.SharedPreferencesUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ProfileReviewFragment extends BaseFragment {
    private RecyclerView rcReview;
    private ProfileReviewAdapter profileReviewAdapter;
    private ArrayList<ReviewsModel> reviewsModels;
    @Override
    protected int getLayoutResource() {
        return (R.layout.fragment_profile_review);
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
        LoadData(reviewsModels);
    }
    private void LoadData(final ArrayList<ReviewsModel> reviewsModels) {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("review")
                .whereEqualTo("userId", SharedPreferencesUtils.getString(mContext,Constants.ID_USER)+"")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                ReviewsModel reviewsModel= new ReviewsModel(String.valueOf(document.getId()),String.valueOf(document.getData().get("photographerId")),Double.parseDouble(String.valueOf(document.getData().get("rating"))),
                                        String.valueOf(document.getData().get("review")), String.valueOf(document.getData().get("userId")));
                                reviewsModels.add(reviewsModel);
                                profileReviewAdapter.Update(mContext,reviewsModels);

                            }
                            SharedPreferencesUtils.setInt(mContext, Constants.CHECK_DATA,1);
                        }
                        else{
                            Toast.makeText(mContext, "Loi", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
