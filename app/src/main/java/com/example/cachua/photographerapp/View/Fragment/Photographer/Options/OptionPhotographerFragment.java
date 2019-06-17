package com.example.cachua.photographerapp.View.Fragment.Photographer.Options;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Activity.MainPhotographerActivity;
import com.example.cachua.photographerapp.View.Adapter.OptionPhotographerAdapter;
import com.example.cachua.photographerapp.View.Fragment.BaseFragment;
import com.example.cachua.photographerapp.View.Fragment.Photographer.Coupons.AddCouponsPhotographerFragment;
import com.example.cachua.photographerapp.View.Fragment.Photographer.Profile.ProfilePhotographerFragment;
import com.example.cachua.photographerapp.View.Model.CouponsModel;
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

public class OptionPhotographerFragment extends BaseFragment {
    private ArrayList<OptionsModel> optionsModelArrayList;
    private OptionPhotographerAdapter optionPhotographerAdapter;
    private RecyclerView rcOption;
    private ImageView imvAdd;


    public static OptionPhotographerFragment sInstance;

    public static OptionPhotographerFragment newInstance() {
        if (sInstance == null) {
            sInstance = new OptionPhotographerFragment();
        }
        return sInstance;
    }
    @Override
    protected int getLayoutResource() {
        return R.layout.option_photographer_fragment;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        rcOption= rootView.findViewById(R.id.rcOption);
        imvAdd= rootView.findViewById(R.id.imvAdd);


        imvAdd.setOnClickListener(this);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        optionsModelArrayList= new ArrayList<>();
        SharedPreferencesUtils.setString(mContext, Constants.CHECK_OPTION,"Photographer");
        optionPhotographerAdapter= new OptionPhotographerAdapter(mContext,optionsModelArrayList,"","","","");
        rcOption.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        rcOption.setAdapter(optionPhotographerAdapter);

        LoadDataOptions(optionsModelArrayList);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imvAdd:{
                AddOptionPhotographerFragment addOptionPhotographerFragment= new AddOptionPhotographerFragment();
                ((MainPhotographerActivity) Objects.requireNonNull(mContext)).nextFragment(addOptionPhotographerFragment, R.id.frameContainer, 0, 0, 0, 0);
                break;
            }
        }

    }
    private void LoadDataOptions(final ArrayList<OptionsModel> optionsModelArrayList) {
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        db.collection("options")
                .whereEqualTo("userId",SharedPreferencesUtils.getString(mContext,Constants.ID_USER)+"")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {


                        if (task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                OptionsModel optionsModel= new OptionsModel(documentSnapshot.getId()+"",String.valueOf(documentSnapshot.getData().get("description")),
                                        String.valueOf(documentSnapshot.getData().get("name")),Float.parseFloat(String.valueOf(documentSnapshot.getData().get("price_per_day"))),
                                        Float.parseFloat(String.valueOf(documentSnapshot.getData().get("price_per_hour"))),Integer.parseInt(String.valueOf(documentSnapshot.getData().get("prints"))),
                                        Integer.parseInt(String.valueOf(documentSnapshot.getData().get("shots"))),String.valueOf(documentSnapshot.getData().get("type")),
                                        String.valueOf(documentSnapshot.getData().get("userId")));
                                optionsModelArrayList.add(optionsModel);
                            }
                            optionPhotographerAdapter.Update(optionsModelArrayList,mContext);
                        }
                    }
                });
    }

}
