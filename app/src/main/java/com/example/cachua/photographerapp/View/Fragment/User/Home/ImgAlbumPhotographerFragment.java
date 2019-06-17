package com.example.cachua.photographerapp.View.Fragment.User.Home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Adapter.AlbumUserPhotographerAdapter;
import com.example.cachua.photographerapp.View.Fragment.BaseFragment;
import com.example.cachua.photographerapp.View.Model.AlbumModel;
import com.example.cachua.photographerapp.View.Model.ImagesModel;
import com.example.cachua.photographerapp.View.config.Constants;
import com.example.cachua.photographerapp.View.config.SharedPreferencesUtils;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

public class ImgAlbumPhotographerFragment extends BaseFragment {
    private RecyclerView rcImg;
    private ArrayList<ImagesModel> imagesModelArrayList;
    private AlbumUserPhotographerAdapter albumUserPhotographerAdapter;
    String idAlbum;

    @Override
    protected int getLayoutResource() {
        return R.layout.img_album_photograher_fragment;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        rcImg= rootView.findViewById(R.id.rcImg);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle bundle= getArguments();
        idAlbum= bundle.getString("IdAlbum");
        Log.i("idAlbum", idAlbum);
        imagesModelArrayList= new ArrayList<>();
        albumUserPhotographerAdapter= new AlbumUserPhotographerAdapter(imagesModelArrayList,mContext);
        rcImg.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        rcImg.setAdapter(albumUserPhotographerAdapter);

        LoadDataImg(imagesModelArrayList);

    }

    @Override
    public void onClick(View v) {


    }
    private void LoadDataImg(final ArrayList<ImagesModel> imagesModelArrayList) {
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        db.collection("images")
                .whereEqualTo("albumId",idAlbum+"")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                ImagesModel imagesModel= new ImagesModel(String.valueOf(documentSnapshot.getData().get("id")),
                                        String.valueOf(documentSnapshot.getData().get("albumId")),String.valueOf(documentSnapshot.getData().get("caption")),String.valueOf(documentSnapshot.getData().get("path")),
                                        (ArrayList<String>) documentSnapshot.getData().get("tag"),String.valueOf(documentSnapshot.getData().get("userId")));
                                imagesModelArrayList.add(imagesModel);
                            }
                            albumUserPhotographerAdapter.Update(imagesModelArrayList,mContext);
                        }
                    }
                });
    }

}
