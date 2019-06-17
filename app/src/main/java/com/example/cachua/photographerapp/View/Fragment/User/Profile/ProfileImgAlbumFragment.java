package com.example.cachua.photographerapp.View.Fragment.User.Profile;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.DatabaseUtils;
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

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

public class ProfileImgAlbumFragment extends BaseFragment {
    private RecyclerView rcImg;
    private ImageView imvAddImg;
    private ArrayList<ImagesModel> imagesModelArrayList;
    private AlbumUserPhotographerAdapter albumUserPhotographerAdapter;
    String idAlbum;
    String uriImg;
    String linkImg;
    int count;
    int countLoad;
    ProgressDialog progressDialog;
    @Override
    protected int getLayoutResource() {
        return R.layout.img_profile_album_photograher_fragment;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        rcImg= rootView.findViewById(R.id.rcImg);

        imvAddImg= rootView.findViewById(R.id.imvAddImg);


        imvAddImg.setOnClickListener(this);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
 //       Toast.makeText(mContext, "sdfghj", Toast.LENGTH_SHORT).show();
        Bundle bundle= getArguments();
        idAlbum= bundle.getString("IdAlbum");
        Log.i("idAlbum", idAlbum);
        imagesModelArrayList= new ArrayList<>();
        albumUserPhotographerAdapter= new AlbumUserPhotographerAdapter(imagesModelArrayList,mContext);
        rcImg.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        rcImg.setAdapter(albumUserPhotographerAdapter);

        LoadDataAlbum(imagesModelArrayList);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imvAddImg:{
                Toast.makeText(mContext, "Hien", Toast.LENGTH_SHORT).show();
                if (ContextCompat.checkSelfPermission(mContext,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE,

                            }, 1);
                } else {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);

                }
                break;
            }
        }

    }
    private void LoadDataAlbum(final ArrayList<ImagesModel> imagesModelArrayList) {
        imagesModelArrayList.clear();
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        db.collection("images")
                .whereEqualTo("albumId",idAlbum+"")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                ArrayList<String> stringArrayList= new ArrayList<>();
                                stringArrayList= (ArrayList<String>) documentSnapshot.getData().get("tag");
                                ImagesModel imagesModel= new ImagesModel(String.valueOf(documentSnapshot.getData().get("albumId")),String.valueOf(documentSnapshot.getData().get("caption")),String.valueOf(documentSnapshot.getData().get("path")),
                                        stringArrayList,String.valueOf(documentSnapshot.getData().get("userId")));
                                imagesModelArrayList.add(imagesModel);
                            }
                            albumUserPhotographerAdapter.Update(imagesModelArrayList,mContext);
                        }
                    }
                });
    }
    private void AddImg(String link) {
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        ArrayList<String> stringArrayList= new ArrayList<>();
        ImagesModel imagesModel= new ImagesModel(idAlbum,"",link,stringArrayList,SharedPreferencesUtils.getString(mContext,Constants.ID_USER));
        db.collection("images")
                .add(imagesModel)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(final DocumentReference documentReference) {
                        countLoad++;
                        if(countLoad==count){
                            progressDialog.dismiss();
                            LoadDataAlbum(imagesModelArrayList);
                        }
                        Toast.makeText(mContext, "Them album thanh cong", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }
    private void upLoadImg(Uri file) {
        FirebaseStorage storage= FirebaseStorage.getInstance();
        StorageReference storageRef =storage.getReferenceFromUrl("gs://hienngunhucho-ba489.appspot.com/");
        final StorageReference riversRef = storageRef.child(file.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(file);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            }
        });

        //Load uri
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return riversRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    linkImg= downloadUri+"";
                    AddImg(linkImg);

                } else {

                }
            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            if (data.getClipData() != null) {
                ClipData mClipData = data.getClipData();
                String[] projection = {MediaStore.Images.Media.DATA};
                ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                progressDialog = ProgressDialog.show(mContext, "",
                        "Loading ...", false);
                progressDialog.setCancelable(false);
                count=mClipData.getItemCount();
                countLoad=0;
                for (int i = 0; i < mClipData.getItemCount(); i++) {

                    ClipData.Item item = mClipData.getItemAt(i);
                    Uri uri = item.getUri();
                    upLoadImg(uri);
                    mArrayUri.add(uri);
                }
                Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                Log.v("LOG_TAG", "Selected Images" + mClipData.getItemCount());
                LoadDataAlbum(imagesModelArrayList);
            }

       }


        super.onActivityResult(requestCode, resultCode, data);
    }

}
