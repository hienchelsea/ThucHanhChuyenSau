package com.example.cachua.photographerapp.View.Fragment.User.Profile;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Activity.MainUserActivity;
import com.example.cachua.photographerapp.View.Fragment.BaseFragment;
import com.example.cachua.photographerapp.View.Model.UseModel;
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
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class ProfileUserFragment extends BaseFragment {
    private TextView tvResetPassword;
    private CircleImageView cimvAvatar;
    private TextView tvSave;
    private TextView tvBirth;
    private TextView tvEmail;
    private EditText edtName;
    private EditText edtNumber;
    private EditText edtAddress;
    private EditText edtTag;
    private EditText edtIntroduce;
    private ImageView imvBirth;
    ProgressDialog progressDialog;

    private String date;
    private String uriImg;
    private int changeImg=0;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private DecimalFormat df = new DecimalFormat("#00");
    @Override
    protected int getLayoutResource() {
        return (R.layout.fragment_profile_user);
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        tvResetPassword= rootView.findViewById(R.id.tvResetPassword);
        cimvAvatar= rootView.findViewById(R.id.cimvAvatar);
        tvSave= rootView.findViewById(R.id.tvSave);
        tvBirth= rootView.findViewById(R.id.tvBirth);
        tvEmail= rootView.findViewById(R.id.tvEmail);
        edtName= rootView.findViewById(R.id.edtName);
        edtNumber= rootView.findViewById(R.id.edtNumber);
        edtAddress= rootView.findViewById(R.id.edtAddress);
        edtTag= rootView.findViewById(R.id.edtTag);
        edtIntroduce= rootView.findViewById(R.id.edtIntroduce);
        imvBirth= rootView.findViewById(R.id.imvBirth);

        tvResetPassword.setOnClickListener(this);
        tvSave.setOnClickListener(this);
        imvBirth.setOnClickListener(this);
        cimvAvatar.setOnClickListener(this);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        edtName.setText(SharedPreferencesUtils.getString(mContext,Constants.NAME_USER));
        edtNumber.setText(SharedPreferencesUtils.getString(mContext,Constants.PHONE_USER));
        edtAddress.setText(SharedPreferencesUtils.getString(mContext,Constants.LOCATION_USER));
        tvBirth.setText(SharedPreferencesUtils.getString(mContext,Constants.BIRTH_USER));
        tvEmail.setText(SharedPreferencesUtils.getString(mContext,Constants.EMAIL_USER));

        Glide
                .with(mContext)
                .load(SharedPreferencesUtils.getString(mContext,Constants.AVATAR_USER))
                .skipMemoryCache(true)
                .into(cimvAvatar);




    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvResetPassword:{
                ProfilePasswordFragment profilePasswordFragment= new ProfilePasswordFragment();
                ((MainUserActivity)Objects.requireNonNull(mContext)).nextFragment(profilePasswordFragment,R.id.container,0,0,0,0);
                break;
            }
            case R.id.tvSave:{

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                final DocumentReference washingtonRef = db.collection("users").document(SharedPreferencesUtils.getString(mContext,Constants.ID_USER) + "");
                if(edtName.getText().toString().equals(SharedPreferencesUtils.getString(mContext,Constants.NAME_USER))==false){
                    washingtonRef
                            .update("name",edtName.getText().toString()+"");
                    SharedPreferencesUtils.setString(mContext,Constants.NAME_USER,edtName.getText().toString());
                }
                if(edtNumber.getText().toString().equals(SharedPreferencesUtils.getString(mContext,Constants.PHONE_USER))==false){
                    washingtonRef
                            .update("phone",edtNumber.getText().toString()+"");
                    SharedPreferencesUtils.setString(mContext,Constants.PHONE_USER,edtNumber.getText().toString());
                }
                if(edtAddress.getText().toString().equals(SharedPreferencesUtils.getString(mContext,Constants.LOCATION_USER))==false){
                    washingtonRef
                            .update("location",edtAddress.getText().toString()+"");
                    SharedPreferencesUtils.setString(mContext,Constants.LOCATION_USER,edtAddress.getText().toString());
                }
                if(tvBirth.getText().toString().equals(SharedPreferencesUtils.getString(mContext,Constants.BIRTH_USER))==false){
                    washingtonRef
                            .update("birth",tvBirth.getText().toString()+"");
                    SharedPreferencesUtils.setString(mContext,Constants.BIRTH_USER,tvBirth.getText().toString());
                }
                if(changeImg==1){
                    upLoadImg();
                }
                else{
                    Objects.requireNonNull(getActivity()).onBackPressed();
                }




                break;
            }
            case R.id.imvBirth:{
                DatePicker(tvBirth);
                break;
            }
            case R.id.cimvAvatar:{
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
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
                }
                break;
            }
        }

    }
    public void DatePicker(final TextView textView){
        date="";
        Calendar mcurrentTime = Calendar.getInstance();
        int year = mcurrentTime.get(Calendar.YEAR);
        int monthOfYear = mcurrentTime.get(Calendar.MONTH);
        int dayOfMonth = mcurrentTime.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog= new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                date=df.format(dayOfMonth)+"/"+df.format(month)+"/"+year;
                textView.setText(date);
            }
        },year,monthOfYear,dayOfMonth);
        datePickerDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                Uri uri = data.getData();
                uriImg = String.valueOf(uri);
                changeImg=1;
                cimvAvatar.setImageURI(uri);
                Toast.makeText(mContext, "ddddd", Toast.LENGTH_SHORT).show();
//                Picasso.with(mContext).load(uri).into(cimgAvatar);
                Glide
                        .with(mContext)
                        .load(uriImg)
                        .skipMemoryCache(true)
                        .into(cimvAvatar);

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void upLoadImg() {

        progressDialog = ProgressDialog.show(mContext, "",
                "Loading ...", false);
        progressDialog.setCancelable(false);
        FirebaseStorage  storage = FirebaseStorage.getInstance();

        StorageReference storageRef =storage.getReferenceFromUrl("gs://hienngunhucho-ba489.appspot.com/");
        Uri file = Uri.parse(uriImg);
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
                    uriImg= downloadUri+"";
                    SharedPreferencesUtils.setString(mContext,Constants.AVATAR_USER, String.valueOf(uriImg));

                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    final DocumentReference washingtonRef = db.collection("users").document(SharedPreferencesUtils.getString(mContext,Constants.ID_USER) + "");
                    washingtonRef
                            .update("avatar",SharedPreferencesUtils.getString(mContext,Constants.AVATAR_USER));

                    progressDialog.dismiss();

                    Objects.requireNonNull(getActivity()).onBackPressed();



                } else {
                    // Handle failures
                    // ...
                }
            }
        });

    }

}
