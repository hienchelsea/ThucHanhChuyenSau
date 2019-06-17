package com.example.cachua.photographerapp.View.Fragment.User.UserToPhoto;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Activity.LoginActivity;
import com.example.cachua.photographerapp.View.Adapter.ImvUserToPhotographerAdapter;
import com.example.cachua.photographerapp.View.Fragment.BaseFragment;
import com.example.cachua.photographerapp.View.Model.AlbumModel;
import com.example.cachua.photographerapp.View.Model.ImagesModel;
import com.example.cachua.photographerapp.View.Model.OptionsModel;
import com.example.cachua.photographerapp.View.config.Constants;
import com.example.cachua.photographerapp.View.config.SharedPreferencesUtils;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileUserToPhotographerFragment3 extends BaseFragment {
    private Button btnFinish;
    private CircleImageView cimgAvatar;
    private Dialog dialog;
    private RecyclerView rcImv;
    private TextView tvNamePhoto;
    private TextView tvBirth;
    private TextView tvEmail;
    private TextView tvNumber;
    private TextView tvGioiThieu;
    private ArrayList<String> stringArrayList= new ArrayList<>();
    private ArrayList<String> linkImg= new ArrayList<>();
    private ImvUserToPhotographerAdapter imvUserToPhotographerAdapter;
    private ProgressDialog progressDialog;
    Bundle bundle;
    String idAlbum;
    int countLoad;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_profile_user_to_grapher_3;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        btnFinish= rootView.findViewById(R.id.btnFinish);
        cimgAvatar= rootView.findViewById(R.id.cimgAvatar);
        tvNamePhoto= rootView.findViewById(R.id.tvNamePhoto);
        tvBirth= rootView.findViewById(R.id.tvBirth);
        tvEmail= rootView.findViewById(R.id.tvEmail);
        tvNumber= rootView.findViewById(R.id.tvNumber);
        tvGioiThieu= rootView.findViewById(R.id.tvGioiThieu);
        rcImv= rootView.findViewById(R.id.rcImv);


        btnFinish.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        bundle= getArguments();
        progressDialog= new ProgressDialog(mContext);
        Glide.with(mContext).load(bundle.getString("linkAvatar")).skipMemoryCache(true).into(cimgAvatar);
        tvNamePhoto.setText(SharedPreferencesUtils.getString(mContext, Constants.NAME_USER));
        tvBirth.setText(SharedPreferencesUtils.getString(mContext, Constants.BIRTH_USER));
        tvEmail.setText(SharedPreferencesUtils.getString(mContext, Constants.EMAIL_USER));
        tvNumber.setText(SharedPreferencesUtils.getString(mContext, Constants.PHONE_USER));
        tvGioiThieu.setText(bundle.getString("introduce"));
        stringArrayList= bundle.getStringArrayList("imv");
        imvUserToPhotographerAdapter= new ImvUserToPhotographerAdapter(stringArrayList,mContext);
        rcImv.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        rcImv.setAdapter(imvUserToPhotographerAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnFinish:{
                countLoad=1;
                progressDialog = ProgressDialog.show(mContext, "",
                        "Loading ...", false);
                progressDialog.setCancelable(false);
                upLoadAvatar(Uri.parse(bundle.getString("linkAvatar")));
                AddAlbum(bundle.getString("nameAlbum"),bundle.getString("tag"));
                Log.i("HienNgdddddu", stringArrayList.get(0)+"onClick: ");
                for(int i=0;i<stringArrayList.size();i++){
                    upLoadImg(Uri.parse(stringArrayList.get(i)));
                }

                break;
            }

        }

    }
    public void showDialog(){

        dialog= new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dailog_finish_user_to_grapher);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Window window = dialog.getWindow();
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        Button btnOK= dialog.findViewById(R.id.btnOk);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                dialog.dismiss();

            }
        });


        dialog.show();
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

                return riversRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    linkImg.add(downloadUri+"");

                    countLoad++;
                    if(countLoad==stringArrayList.size()){
                        for(int i=0;i<linkImg.size();i++){
                            Log.i("Hiennnnnnndndnd", linkImg.get(i)+"onSuccess: ");
                                AddImg(linkImg.get(i));
                                if(i==linkImg.size()-1){
                                    OptionsModel optionsModel= new OptionsModel("",bundle.getString("introduceOption"),
                                            bundle.getString("nameOption"),bundle.getFloat("totalDay"),bundle.getFloat("totalHour"),
                                            bundle.getInt("printImv"),bundle.getInt("countImv"),bundle.getString("type"),
                                            SharedPreferencesUtils.getString(mContext,Constants.ID_USER));
                                    AddOption(optionsModel);


                                }
                        }

                    }

                } else {

                }
            }
        });

    }
    private void upLoadAvatar(Uri file) {
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
                    UpdateUser(String.valueOf(downloadUri));

                } else {

                }
            }
        });

    }
    private void AddOption(OptionsModel optionsModel) {
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        db.collection("options")
                .add(optionsModel)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(final DocumentReference documentReference) {
                        progressDialog.dismiss();
                        SharedPreferencesUtils.setBoolean(mContext,Constants.CHECK_FIRST,false);
                        Intent intent= new Intent(mContext,LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }
    private void AddImg(String link) {
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        ArrayList<String> stringArrayList= new ArrayList<>();
        ImagesModel imagesModel= new ImagesModel(idAlbum,"",link,stringArrayList, SharedPreferencesUtils.getString(mContext, Constants.ID_USER));
        db.collection("images")
                .add(imagesModel)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(final DocumentReference documentReference) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }
    private void AddAlbum(String name,String tag) {
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        final AlbumModel albumModel = new AlbumModel("", SharedPreferencesUtils.getString(mContext, Constants.LOCATION_USER),name,tag,SharedPreferencesUtils.getString(mContext,Constants.ID_USER));
        db.collection("album")
                .add(albumModel)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(final DocumentReference documentReference) {
                      //  albumModel.setId(documentReference.getId()+"");
                        idAlbum=documentReference.getId()+"";
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }
    private void UpdateUser(String s){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference washingtonRef = db.collection("users").document(SharedPreferencesUtils.getString(mContext,Constants.ID_USER) + "");
        washingtonRef
                .update("avatar",s+"");
        washingtonRef
                .update("role","Photographer");
    }
}
