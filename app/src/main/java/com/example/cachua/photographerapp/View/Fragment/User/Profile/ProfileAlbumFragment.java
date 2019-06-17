package com.example.cachua.photographerapp.View.Fragment.User.Profile;

import android.app.ActionBar;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Adapter.AlbumPhotographerAdapter;
import com.example.cachua.photographerapp.View.Fragment.BaseFragment;
import com.example.cachua.photographerapp.View.Model.AlbumModel;
import com.example.cachua.photographerapp.View.config.Constants;
import com.example.cachua.photographerapp.View.config.SharedPreferencesUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ProfileAlbumFragment extends BaseFragment {
    private ArrayList<AlbumModel> albumModelArrayList = new ArrayList<>();
    private AlbumPhotographerAdapter albumPhotographerAdapter;
    private RecyclerView rcAlbumUser;
    private ImageView imvAddAlbum;
    private String idPhotographer;
    private Dialog dialog;
    @Override
    protected int getLayoutResource() {
        return R.layout.frament_profile_album;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        rcAlbumUser=rootView.findViewById(R.id.rcAlbumUser);
        imvAddAlbum=rootView.findViewById(R.id.imvAddAlbum);

        imvAddAlbum.setOnClickListener(this);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Log.i("HienNguVlone", "initData: ");
        Bundle bundle= getArguments();
        idPhotographer= bundle.getString("idPhotographer");
        albumPhotographerAdapter= new AlbumPhotographerAdapter(albumModelArrayList,mContext,"UserToUser");
        rcAlbumUser.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        rcAlbumUser.setAdapter(albumPhotographerAdapter);
        LoadDataAlbum(albumModelArrayList);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imvAddAlbum:{
                ShowDiaLog();

                break;
            }
        }

    }
    private void ShowDiaLog() {
        dialog= new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dailog_add_album);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Window window = dialog.getWindow();
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        Button btnOk= dialog.findViewById(R.id.btnOk);
        final EditText edtName= dialog.findViewById(R.id.edtName);
        final EditText edtDescription= dialog.findViewById(R.id.edtDescription);
        final EditText edtTag= dialog.findViewById(R.id.edtTag);
        Button btnCancel= dialog.findViewById(R.id.btnCancel);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtName.getText().toString().equals("")==false){
                    AddAlbum(edtDescription.getText().toString(),edtName.getText().toString(),edtTag.getText().toString());
                    dialog.dismiss();
                }
                else{
                    Toast.makeText(mContext, "Nhap day du thong tin", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {

                dialog.dismiss();

            }
        });


        dialog.show();
    }

    private void LoadDataAlbum(final ArrayList<AlbumModel> albumModelArrayList) {
        albumModelArrayList.clear();
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        db.collection("album")
                .whereEqualTo("userId", idPhotographer+"")
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
    private void AddAlbum(String description,String name,String tag) {
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        final AlbumModel albumModel = new AlbumModel(description, SharedPreferencesUtils.getString(mContext, Constants.LOCATION_USER),name,tag,SharedPreferencesUtils.getString(mContext,Constants.ID_USER));
        db.collection("album")
                .add(albumModel)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(final DocumentReference documentReference) {
                        Toast.makeText(mContext, "Them album thanh cong", Toast.LENGTH_SHORT).show();
                        albumModel.setId(documentReference.getId()+"");
                        albumModelArrayList.add(albumModel);
                        albumPhotographerAdapter.Update(albumModelArrayList,mContext);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }
}
