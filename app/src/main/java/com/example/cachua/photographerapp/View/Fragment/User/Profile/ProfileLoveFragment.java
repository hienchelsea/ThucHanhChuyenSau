package com.example.cachua.photographerapp.View.Fragment.User.Profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Adapter.FavoritePhotographerAdapter;
import com.example.cachua.photographerapp.View.Fragment.BaseFragment;
import com.example.cachua.photographerapp.View.Model.FavoritesModel;
import com.example.cachua.photographerapp.View.Model.UseModel;
import com.example.cachua.photographerapp.View.config.Constants;
import com.example.cachua.photographerapp.View.config.SharedPreferencesUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ProfileLoveFragment extends BaseFragment {
    private ArrayList<UseModel> useModelArrayList;
    private ArrayList<FavoritesModel> favoritesModelArrayList;
    private FavoritePhotographerAdapter favoritePhotographerAdapter;
    private RecyclerView rcHomeHighlight;
    @Override
    protected int getLayoutResource() {
        return (R.layout.fragment_profile_love);
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        rcHomeHighlight= rootView.findViewById(R.id.rcHomeHighlight);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        Log.i("NguNgoc", "123456");
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onResume() {
        super.onResume();
        useModelArrayList= new ArrayList<>();
        favoritesModelArrayList= new ArrayList<>();
        favoritePhotographerAdapter = new FavoritePhotographerAdapter(mContext,useModelArrayList);
        rcHomeHighlight.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        rcHomeHighlight.setAdapter(favoritePhotographerAdapter);
        LoadDataLove(favoritesModelArrayList,useModelArrayList);
    }
    private void LoadDataLove(final ArrayList<FavoritesModel> favoritesModels,final ArrayList<UseModel> useModels) {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("favorites")
                .whereEqualTo("userId", SharedPreferencesUtils.getString(mContext,Constants.ID_USER)+"")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.i("Hiennn", "onComplete: ");
                                FavoritesModel favoritesModel= new FavoritesModel(String.valueOf(document.getId()),String.valueOf(document.getData().get("photographerId")),(String.valueOf(document.getData().get("userId"))));
                                favoritesModels.add(favoritesModel);
                                LoadDataUser(useModels,favoritesModel.getPhotographerId());
                            }
                            SharedPreferencesUtils.setInt(mContext, Constants.CHECK_DATA,1);
                            Log.i("Hiennn", useModels.size()+"");
                        }
                        else{
                            Toast.makeText(mContext, "Loi", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void LoadDataUser(final ArrayList<UseModel> useModels,String s) {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .whereEqualTo("id", s+"")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.i("Hiennn", "onComplete: ");

                                UseModel useModel= new UseModel(String.valueOf(document.getData().get("id")),String.valueOf(document.getData().get("bio")),
                                        String.valueOf(document.getData().get("birth")),String.valueOf(document.getData().get("email")),
                                        String.valueOf(document.getData().get("location")),String.valueOf(document.getData().get("name")),
                                        String.valueOf(document.getData().get("phone")),Double.parseDouble(String.valueOf(document.getData().get("rating"))),
                                        String.valueOf(document.getData().get("role")),String.valueOf(document.getData().get("avatar"))
                                );
                                useModels.add(useModel);
                                favoritePhotographerAdapter.Update(mContext,useModels);

                            }

                        }
                        else{

                        }
                    }
                });
    }

}
