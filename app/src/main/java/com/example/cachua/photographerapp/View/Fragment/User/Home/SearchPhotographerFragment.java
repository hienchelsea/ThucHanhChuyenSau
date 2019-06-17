package com.example.cachua.photographerapp.View.Fragment.User.Home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Adapter.FavoritePhotographerAdapter;
import com.example.cachua.photographerapp.View.Adapter.SearchPhotographerTwoAdapter;
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

public class SearchPhotographerFragment extends BaseFragment {
    private ArrayList<UseModel> useModelArrayList;
    private SearchPhotographerTwoAdapter searchPhotographerTwoAdapter;
    private RecyclerView rcSearch;
    private EditText edtName;
    private LinearLayout llSearch;
    private ArrayList<FavoritesModel>favoritesModelArrayList;
    String name;

    public static SearchPhotographerFragment sInstance;
    public static SearchPhotographerFragment newInstance(){
        if (sInstance == null) {
            sInstance = new SearchPhotographerFragment();
        }
        return sInstance;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_search_photographer;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        rcSearch= rootView.findViewById(R.id.rcSearch);
        edtName= rootView.findViewById(R.id.edtName);
        llSearch= rootView.findViewById(R.id.llSearch);

        llSearch.setOnClickListener(this);


    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.llSearch:{
                if(edtName.getText().toString().equals("")==true){
                    Toast.makeText(mContext, "Vui nhap ten can tim", Toast.LENGTH_SHORT).show();
                }
                else{
                    LoadDataSearch(useModelArrayList,edtName.getText().toString());
                }
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        favoritesModelArrayList= new ArrayList<>();
        LoadDataLove(favoritesModelArrayList);
        useModelArrayList= new ArrayList<>();
        searchPhotographerTwoAdapter = new SearchPhotographerTwoAdapter(mContext,useModelArrayList,favoritesModelArrayList);
        rcSearch.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        rcSearch.setAdapter(searchPhotographerTwoAdapter);
    }

    private void LoadDataSearch(final ArrayList<UseModel> useModelArrayList, final String s) {
        useModelArrayList.clear();
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        db.collection("users")
                .whereEqualTo("role", "Photographer")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(String.valueOf(document.getData().get("name")).equals(s)==true){
                                    UseModel useModel= new UseModel(String.valueOf(document.getData().get("id")),String.valueOf(document.getData().get("bio")),
                                            String.valueOf(document.getData().get("birth")),String.valueOf(document.getData().get("email")),
                                            String.valueOf(document.getData().get("location")),String.valueOf(document.getData().get("name")),
                                            String.valueOf(document.getData().get("phone")),Double.parseDouble(String.valueOf(document.getData().get("rating"))),
                                            String.valueOf(document.getData().get("role")),String.valueOf(document.getData().get("avatar"))
                                    );
                                    useModelArrayList.add(useModel);
                                }
                                searchPhotographerTwoAdapter.Update(mContext,useModelArrayList);
                            }
                            SharedPreferencesUtils.setInt(mContext, Constants.CHECK_DATA,1);
                        }
                        else{
                            Toast.makeText(mContext, "Loi", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void LoadDataLove(final ArrayList<FavoritesModel> favoritesModels) {
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        db.collection("favorites")
                .whereEqualTo("userId", SharedPreferencesUtils.getString(mContext,Constants.ID_USER)+"")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                FavoritesModel favoritesModel= new FavoritesModel(String.valueOf(document.getId()),
                                        String.valueOf(document.getData().get("photographerId")),String.valueOf(document.getData().get("userId")));
                                favoritesModels.add(favoritesModel);
                                Log.i("HienOcc", favoritesModel.getId()+"onComplete: ");
                            }
                        }
                        else{
                            Toast.makeText(mContext, "Loi", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
