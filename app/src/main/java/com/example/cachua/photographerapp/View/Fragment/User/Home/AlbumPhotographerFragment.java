package com.example.cachua.photographerapp.View.Fragment.User.Home;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Adapter.AlbumPhotographerAdapter;
import com.example.cachua.photographerapp.View.Adapter.AlbumUserPhotographerAdapter;
import com.example.cachua.photographerapp.View.Fragment.BaseFragment;
import com.example.cachua.photographerapp.View.Model.AlbumModel;
import com.example.cachua.photographerapp.View.Model.CouponsModel;
import com.example.cachua.photographerapp.View.Model.ImagesModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class AlbumPhotographerFragment extends BaseFragment {
    private ImageView imgBack;
    private ImageView ImvIMG;
    private LinearLayout llToolbar;
 //   private ArrayList<ImagesModel> imagesModelArrayList;
    private ArrayList<AlbumModel> albumModelArrayList;
    private AlbumPhotographerAdapter albumPhotographerAdapter;
    private RecyclerView rcAlbumUser;
    private String idPhotographer;
    @Override
    protected int getLayoutResource() {
        return (R.layout.fragment_profile_home_photographer_album);
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        imgBack=rootView.findViewById(R.id.imgBack);
        ImvIMG=rootView.findViewById(R.id.ImvIMG);
        llToolbar=rootView.findViewById(R.id.llToolbar);
        rcAlbumUser=rootView.findViewById(R.id.rcAlbumUser);

        imgBack.setOnClickListener(this);
        ImvIMG.setOnClickListener(this);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle bundle= getArguments();
        idPhotographer= bundle.getString("idPhotographer");
        albumModelArrayList= new ArrayList<>();
        albumPhotographerAdapter= new AlbumPhotographerAdapter(albumModelArrayList,mContext,"UserToGrapher");
        rcAlbumUser.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        rcAlbumUser.setAdapter(albumPhotographerAdapter);
        LoadDataAlbum(albumModelArrayList);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgBack:{
                Objects.requireNonNull(getActivity()).onBackPressed();
                break;
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void LoadDataAlbum(final ArrayList<AlbumModel> albumModelArrayList) {
        albumModelArrayList.clear();
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        db.collection("album")
                .whereEqualTo("userId",idPhotographer+"")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {


                        if (task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                AlbumModel albumModel= new AlbumModel(String.valueOf(documentSnapshot.getId()),
                                        String.valueOf(documentSnapshot.getData().get("description")),String.valueOf(documentSnapshot.getData().get("locationId")),String.valueOf(documentSnapshot.getData().get("name")),
                                        String.valueOf(documentSnapshot.getData().get("tag")),String.valueOf(documentSnapshot.getData().get("userId")));
                                albumModelArrayList.add(albumModel);
                            }
                            albumPhotographerAdapter.Update(albumModelArrayList,mContext);
                        }
                    }
                });
    }
}
