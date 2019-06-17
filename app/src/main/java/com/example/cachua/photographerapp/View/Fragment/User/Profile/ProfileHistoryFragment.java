package com.example.cachua.photographerapp.View.Fragment.User.Profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Adapter.HistoryPhotographerAdapter;
import com.example.cachua.photographerapp.View.Fragment.BaseFragment;
import com.example.cachua.photographerapp.View.Model.FavoritesModel;
import com.example.cachua.photographerapp.View.Model.ImagesModel;
import com.example.cachua.photographerapp.View.Model.OrdersModel;
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

public class ProfileHistoryFragment extends BaseFragment {
    private RecyclerView rcHistory;
    private HistoryPhotographerAdapter historyPhotographerAdapter;
    private ArrayList<OrdersModel> ordersModelArrayList;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private ArrayList<FavoritesModel> favoritesModelArrayList;

    @Override
    protected int getLayoutResource() {
        return (R.layout.fragment_profile_history);
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        rcHistory = rootView.findViewById(R.id.rcHistory);

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
        favoritesModelArrayList = new ArrayList<>();
        LoadDataLove(favoritesModelArrayList);
        ordersModelArrayList = new ArrayList<>();
        historyPhotographerAdapter = new HistoryPhotographerAdapter(mContext, ordersModelArrayList, favoritesModelArrayList);
        rcHistory.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        rcHistory.setAdapter(historyPhotographerAdapter);
        LoadDataHistory(ordersModelArrayList);
    }

    private void LoadDataHistory(final ArrayList<OrdersModel> ordersModelArrayList) {
        //  imagesModelArrayList.clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("order")
                .whereEqualTo("userId", SharedPreferencesUtils.getString(mContext, Constants.ID_USER) + "")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                if (documentSnapshot.getData().get("status").equals("success")) {
                                    OrdersModel ordersModel = new OrdersModel();
                                    ordersModel.setId(documentSnapshot.getId()+"");
                                    ordersModel.setAddress(String.valueOf(documentSnapshot.getData().get("address")));
                                    ordersModel.setPhotographerId(String.valueOf(documentSnapshot.getData().get("photographerId")));
                                    ordersModel.setUserId(String.valueOf(documentSnapshot.getData().get("userId")));
                                    ordersModel.setTotal(Double.parseDouble(String.valueOf(documentSnapshot.getData().get("total"))));
                                    String s = String.valueOf(documentSnapshot.getData().get("endAt"));
                                    Calendar calendar = Calendar.getInstance();
                                    String[] a = s.split(",");
                                    String[] b = a[0].split("=");
                                    Log.i("Hiennnnnguu", b[1]);
                                    Timestamp timestamp = new Timestamp(Long.parseLong(b[1]) * 1000);
                                    Log.i("Hiennnnnguu", timestamp.toString());
                                    Log.i("Hiennnnnguu", calendar.getTimeInMillis() + "");


                                    ordersModel.setStartAt(new Timestamp(Long.parseLong(b[1])));
//                                    ordersModel.setEndAt(new Timestamp(Long.parseLong(String.valueOf(documentSnapshot.getData().get("endAt")))));
                                    ordersModelArrayList.add(ordersModel);
                                }

                            }
                            historyPhotographerAdapter.Update(mContext, ordersModelArrayList);
                        }
                    }
                });
    }

    private void LoadDataLove(final ArrayList<FavoritesModel> favoritesModels) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("favorites")
                .whereEqualTo("userId", SharedPreferencesUtils.getString(mContext, Constants.ID_USER) + "")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                FavoritesModel favoritesModel = new FavoritesModel(String.valueOf(document.getId()),
                                        String.valueOf(document.getData().get("photographerId")), String.valueOf(document.getData().get("userId")));
                                favoritesModels.add(favoritesModel);
                                Log.i("HienOcc", favoritesModel.getId() + "onComplete: ");
                            }
                        } else {
                            Toast.makeText(mContext, "Loi", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
