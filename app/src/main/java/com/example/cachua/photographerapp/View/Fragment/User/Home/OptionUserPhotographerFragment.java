package com.example.cachua.photographerapp.View.Fragment.User.Home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Activity.MainUserActivity;
import com.example.cachua.photographerapp.View.Adapter.OptionPhotographerAdapter;
import com.example.cachua.photographerapp.View.Fragment.BaseFragment;
import com.example.cachua.photographerapp.View.Model.OptionsModel;
import com.example.cachua.photographerapp.View.config.Constants;
import com.example.cachua.photographerapp.View.config.SharedPreferencesUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class OptionUserPhotographerFragment extends BaseFragment {
    private ArrayList<OptionsModel> optionsModelArrayList;
    private OptionPhotographerAdapter optionPhotographerAdapter;
    private RecyclerView rcOption;
    private String idPhotographer;
    private String linkAvatar;
    private String name;
    private String rating;
    private ProgressDialog progressDialog;


    private ImageView imvOder;
    @Override
    protected int getLayoutResource() {
        return (R.layout.fragment_option_photographer);
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        rcOption= rootView.findViewById(R.id.rcOption);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle bundle= getArguments();
        idPhotographer= bundle.getString("idPhotographer");
        linkAvatar= bundle.getString("avatarLink");
        name= bundle.getString("name");
        rating= bundle.getString("rating");
        Log.i("idPhotographer", idPhotographer+"");
        optionsModelArrayList= new ArrayList<>();
        SharedPreferencesUtils.setString(mContext, Constants.CHECK_OPTION,"User");


        optionPhotographerAdapter= new OptionPhotographerAdapter(mContext,optionsModelArrayList,idPhotographer,linkAvatar,name,rating);
        rcOption.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        rcOption.setAdapter(optionPhotographerAdapter);
        LoadDataOption(optionsModelArrayList);


    }

    private void LoadDataOption(final ArrayList<OptionsModel> optionsModels) {
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        db.collection("options")
                .whereEqualTo("userId",idPhotographer+"")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        progressDialog= ProgressDialog.show(mContext,"","Loading...",false);
                        progressDialog.setCancelable(false);

                        if (task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                OptionsModel optionsModel= new OptionsModel(String.valueOf(documentSnapshot.getData().get("id")),
                                        String.valueOf(documentSnapshot.getData().get("description")),String.valueOf(documentSnapshot.getData().get("name")),Float.parseFloat(String.valueOf(documentSnapshot.getData().get("price_per_day"))),
                                        Float.parseFloat(String.valueOf(documentSnapshot.getData().get("price_per_hour"))),Integer.parseInt(String.valueOf(documentSnapshot.getData().get("prints"))),Integer.parseInt(String.valueOf(documentSnapshot.getData().get("shots"))),
                                        String.valueOf(documentSnapshot.getData().get("type")),String.valueOf(documentSnapshot.getData().get("userId")));
                                optionsModels.add(optionsModel);
                            }
                            optionPhotographerAdapter.Update(optionsModels,mContext);
                            progressDialog.dismiss();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.imvOder:{
//                OrdersPhotographerFragment ordersPhotographerFragment= new OrdersPhotographerFragment();
//                ((MainUserActivity) Objects.requireNonNull(mContext)).nextFragment(ordersPhotographerFragment, R.id.frameContainer, 0, 0, 0, 0);
//                break;
//            }
        }

    }
}
