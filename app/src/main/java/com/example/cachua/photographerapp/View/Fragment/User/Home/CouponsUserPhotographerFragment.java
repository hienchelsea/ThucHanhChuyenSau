package com.example.cachua.photographerapp.View.Fragment.User.Home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Adapter.CouponsPhotographerAdapter;
import com.example.cachua.photographerapp.View.Fragment.BaseFragment;
import com.example.cachua.photographerapp.View.Model.CouponsModel;
import com.example.cachua.photographerapp.View.Model.OptionsModel;
import com.example.cachua.photographerapp.View.config.Constants;
import com.example.cachua.photographerapp.View.config.SharedPreferencesUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Timestamp;
import java.util.ArrayList;

public class CouponsUserPhotographerFragment extends BaseFragment {
    private ArrayList<CouponsModel> couponsModelArrayList;
    private CouponsPhotographerAdapter couponsPhotographerAdapter;
    private RecyclerView rcCoupons;
    private String idPhotographer;
    @Override
    protected int getLayoutResource() {
        return R.layout.coupons_user_photographer_fragment;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        rcCoupons= rootView.findViewById(R.id.rcCoupons);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle bundle= getArguments();
        idPhotographer= bundle.getString("idPhotographer");
        Log.i("idPhotographer", idPhotographer+"");
        couponsModelArrayList= new ArrayList<>();
        SharedPreferencesUtils.setString(mContext, Constants.CHECK_COUPONS,"User");

        couponsPhotographerAdapter= new CouponsPhotographerAdapter(mContext,couponsModelArrayList);
        rcCoupons.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        rcCoupons.setAdapter(couponsPhotographerAdapter);
        LoadDataCoupons(couponsModelArrayList);

    }

    @Override
    public void onClick(View v) {

    }
    private void LoadDataCoupons(final ArrayList<CouponsModel> couponsModels) {
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        db.collection("coupons")
                .whereEqualTo("userId",idPhotographer+"")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {


                        if (task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                CouponsModel couponsModel= new CouponsModel(String.valueOf(documentSnapshot.getData().get("id")),
                                        String.valueOf(documentSnapshot.getData().get("code")),String.valueOf(documentSnapshot.getData().get("description")),date(String.valueOf((documentSnapshot.getData().get("endAt")))),
                                        String.valueOf(documentSnapshot.getData().get("name")),date(String.valueOf((documentSnapshot.getData().get("startAt")))),date(String.valueOf((documentSnapshot.getData().get("updatedAt")))),
                                        String.valueOf(documentSnapshot.getData().get("userId")),Float.parseFloat(String.valueOf(documentSnapshot.getData().get("value"))));
                                Log.i("Hienuyyuu", String.valueOf(documentSnapshot.getData().get("startAt"))+String.valueOf(documentSnapshot.getData().get("updatedAt")));
                                couponsModels.add(couponsModel);
                            }
                            couponsPhotographerAdapter.Update(mContext,couponsModels);
                        }
                    }
                });
    }
    public Timestamp date(String s) {
        String[] catChuoi = s.split(",");
        String[] catChuoiTiep = catChuoi[0].split("=");
        Timestamp timestamp = new Timestamp(Long.parseLong(catChuoiTiep[1]) * 1000);
        return timestamp;
    }
}
