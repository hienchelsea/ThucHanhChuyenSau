package com.example.cachua.photographerapp.View.Fragment.Login;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class RegistrationStep2Fragment extends BaseFragment {
    private Button btnRegistration;
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtPassword2;
    private CircleImageView imgAvatar;
    private TextView tvAvatar;
    private CheckBox CheckBox;
    ProgressDialog progressDialog;
    ArrayList<String> stringArrayList = new ArrayList<>();
    String uriImg;
    String linkImg;
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage;


    @Override
    protected int getLayoutResource() {
        return (R.layout.registration_step_2_fragment);
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        btnRegistration = rootView.findViewById(R.id.btnRegistration);
        edtEmail = rootView.findViewById(R.id.edtEmail);
        edtPassword = rootView.findViewById(R.id.edtPassword);
        edtPassword2 = rootView.findViewById(R.id.edtPassword2);
        imgAvatar = rootView.findViewById(R.id.imgAvatar);
        tvAvatar = rootView.findViewById(R.id.tvAvatar);
        CheckBox = rootView.findViewById(R.id.CheckBox);


        btnRegistration.setOnClickListener(this);
        imgAvatar.setOnClickListener(this);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        storage = FirebaseStorage.getInstance();
        Bundle bundle = getArguments();
        stringArrayList = bundle.getStringArrayList("step1");

        Toast.makeText(mContext, stringArrayList.get(0) + "", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegistration: {
                if (edtPassword.getText().toString().equals(edtPassword2.getText().toString()) == true) {
   //                 upLoadImg();
                    createUser();

                }
                else{
                    Toast.makeText(mContext, "Mat khau khong khop nhau", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.imgAvatar: {
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

    private void createIDUser() {
        final UseModel useModel = new UseModel("", stringArrayList.get(1), stringArrayList.get(2), edtEmail.getText().toString(), stringArrayList.get(4), stringArrayList.get(0), stringArrayList.get(3), 0.0,"User",linkImg);

        db.collection("users")
                .add(useModel)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(final DocumentReference documentReference) {

                        final DocumentReference washingtonRef = db.collection("users").document(documentReference.getId() + "");
                        washingtonRef
                                .update("id", documentReference.getId() + "")
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        SharedPreferencesUtils.setString(mContext, Constants.ID_USER,String.valueOf(documentReference.getId()));

                                        Intent intent = new Intent(mContext, MainUserActivity.class);
                                        startActivity(intent);
                                        progressDialog.dismiss();
                                        getActivity().finish();

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {


                                    }
                                });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();

                    }
                });

    }

    private void createUser() {
        mAuth = FirebaseAuth.getInstance();
        String email = edtEmail.getText().toString();
        String pass = edtPassword.getText().toString();
        if (email.equals("") == true || pass.equals("") == true) {
            Toast.makeText(mContext, "Loi Email da dang ki", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog = ProgressDialog.show(mContext, "",
                    "Loading ...", false);
            progressDialog.setCancelable(false);
            mAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                upLoadImg();
                                SharedPreferencesUtils.setString(mContext,Constants.BIO_USER,String.valueOf(stringArrayList.get(1)));
                                SharedPreferencesUtils.setString(mContext,Constants.BIRTH_USER,String.valueOf(stringArrayList.get(2)));
                                SharedPreferencesUtils.setString(mContext,Constants.EMAIL_USER,String.valueOf(edtEmail.getText().toString()));
                                SharedPreferencesUtils.setString(mContext,Constants.LOCATION_USER,String.valueOf(stringArrayList.get(4)));
                                SharedPreferencesUtils.setString(mContext,Constants.NAME_USER,String.valueOf(stringArrayList.get(0)));
                                SharedPreferencesUtils.setString(mContext,Constants.PHONE_USER,String.valueOf(stringArrayList.get(3)));
                                SharedPreferencesUtils.setString(mContext,Constants.RATING_USER,String.valueOf("0"));
                                SharedPreferencesUtils.setString(mContext,Constants.ROLE_USER,String.valueOf("User"));
                                SharedPreferencesUtils.setBoolean(mContext,Constants.CHECK_FIRST,true);
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(mContext, "Loi ", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }

    }

    private void upLoadImg() {

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
                    linkImg= downloadUri+"";
                    SharedPreferencesUtils.setString(mContext,Constants.AVATAR_USER, String.valueOf(linkImg));

                    createIDUser();

                } else {
                    // Handle failures
                    // ...
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                imgAvatar.setImageURI(uri);
                uriImg = String.valueOf(uri);

                tvAvatar.setVisibility(View.GONE);

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
