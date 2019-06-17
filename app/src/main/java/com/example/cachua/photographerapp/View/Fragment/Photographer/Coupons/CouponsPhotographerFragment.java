package com.example.cachua.photographerapp.View.Fragment.Photographer.Coupons;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Activity.MainPhotographerActivity;
import com.example.cachua.photographerapp.View.Activity.MainUserActivity;
import com.example.cachua.photographerapp.View.Adapter.CouponsPhotographerAdapter;
import com.example.cachua.photographerapp.View.Fragment.BaseFragment;
import com.example.cachua.photographerapp.View.Fragment.Photographer.Notice.NoticePhotographerFragment;
import com.example.cachua.photographerapp.View.Fragment.User.Home.OrdersPhotographerFragment;
import com.example.cachua.photographerapp.View.Model.CouponsModel;
import com.example.cachua.photographerapp.View.config.Constants;
import com.example.cachua.photographerapp.View.config.SharedPreferencesUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class CouponsPhotographerFragment extends BaseFragment {
    private ArrayList<CouponsModel> couponsModelArrayList;
    private CouponsPhotographerAdapter couponsPhotographerAdapter;
    private RecyclerView rcCoupons;
    private ImageView imvAdd;




    public static CouponsPhotographerFragment sInstance;

    public static CouponsPhotographerFragment newInstance() {
        if (sInstance == null) {
            sInstance = new CouponsPhotographerFragment();
        }
        return sInstance;
    }
    @Override
    protected int getLayoutResource() {
        return R.layout.coupons_photographer_fragment;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        rcCoupons= rootView.findViewById(R.id.rcCoupons);
        imvAdd= rootView.findViewById(R.id.imvAdd);

        imvAdd.setOnClickListener(this);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        SharedPreferencesUtils.setString(mContext, Constants.CHECK_COUPONS,"Photographer");
//        couponsModelArrayList.add(new CouponsModel(1,"Khuyen mai cho 2 nguoi tro len","22/01/1997","27/12/1997","Khuyen mai quanh nam","HienOc",20));
//        couponsModelArrayList.add(new CouponsModel(1,"Khuyen mai cho 2 nguoi tro len","22/01/1997","27/12/1997","Khuyen mai quanh nam","HienOc",20));
//        couponsModelArrayList.add(new CouponsModel(1,"Khuyen mai cho 2 nguoi tro len","22/01/1997","27/12/1997","Khuyen mai quanh nam","HienOc",20));
//        couponsModelArrayList.add(new CouponsModel(1,"Khuyen mai cho 2 nguoi tro len","22/01/1997","27/12/1997","Khuyen mai quanh nam","HienOc",20));


    }

    @Override
    public void onResume() {
        super.onResume();
        couponsModelArrayList= new ArrayList<>();
        couponsPhotographerAdapter= new CouponsPhotographerAdapter(mContext,couponsModelArrayList);
        rcCoupons.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        rcCoupons.setAdapter(couponsPhotographerAdapter);
        LoadDataCoupons(couponsModelArrayList);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imvAdd:{
                AddCouponsPhotographerFragment addCouponsPhotographerFragment= new AddCouponsPhotographerFragment();
                ((MainPhotographerActivity) Objects.requireNonNull(mContext)).nextFragment(addCouponsPhotographerFragment, R.id.frameContainer, 0, 0, 0, 0);
                break;
            }
        }

    }
    private void LoadDataCoupons(final ArrayList<CouponsModel> couponsModels) {
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        db.collection("coupons")
                .whereEqualTo("userId",SharedPreferencesUtils.getString(mContext,Constants.ID_USER)+"")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {


                        if (task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                CouponsModel couponsModel= new CouponsModel(String.valueOf(documentSnapshot.getId()+""),
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
