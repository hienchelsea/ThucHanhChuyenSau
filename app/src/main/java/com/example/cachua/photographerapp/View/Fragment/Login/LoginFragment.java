package com.example.cachua.photographerapp.View.Fragment.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Activity.LoginActivity;
import com.example.cachua.photographerapp.View.Activity.MainPhotographerActivity;
import com.example.cachua.photographerapp.View.Activity.MainUserActivity;
import com.example.cachua.photographerapp.View.Fragment.BaseFragment;
import com.example.cachua.photographerapp.View.config.Constants;
import com.example.cachua.photographerapp.View.config.SharedPreferencesUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class LoginFragment extends BaseFragment {

    public static LoginFragment sInstance;
    public static LoginFragment newInstance() {
        if (sInstance == null) {
            sInstance = new LoginFragment();
        }
        return sInstance;
    }

    private Button btnLogin;
    private EditText edtName;
    private EditText edtPassword;
    private TextView tvRegistration;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    FirebaseFirestore db = FirebaseFirestore.getInstance();



    @Override
    protected int getLayoutResource() {
        return (R.layout.fragment_login);
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        btnLogin= rootView.findViewById(R.id.btnLogin);
        edtPassword= rootView.findViewById(R.id.edtPassword);
        edtName= rootView.findViewById(R.id.edtName);
        tvRegistration= rootView.findViewById(R.id.tvRegistration);

        btnLogin.setOnClickListener(this);
        tvRegistration.setOnClickListener(this);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if(SharedPreferencesUtils.getBoolean(mContext,Constants.CHECK_FIRST)==true){
            if(SharedPreferencesUtils.getString(mContext,Constants.ROLE_USER).equals("User")==true){
                Intent intent= new Intent(mContext, MainUserActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
            else{
                if(SharedPreferencesUtils.getString(mContext,Constants.ROLE_USER).equals("Photographer")==true){
                    Intent intent= new Intent(mContext, MainPhotographerActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:{
                signIn();
                break;
            }
            case R.id.tvRegistration:{
                RegistrationStep1Fragment registrationFragment = new RegistrationStep1Fragment();
                ((LoginActivity) Objects.requireNonNull(getActivity())).nextFragment(registrationFragment, R.id.tabContainer, 0, 0, 0, 0);
                Toast.makeText(mContext, "ddd", Toast.LENGTH_SHORT).show();
                break;
            }
        }

    }
    public void signIn() {
        mAuth = FirebaseAuth.getInstance();
        String email= edtName.getText().toString();
        String pass= edtPassword.getText().toString();
        if(email.equals("")==true||pass.equals("")==true){
            Toast.makeText(mContext, "Loi Email da dang ki", Toast.LENGTH_SHORT).show();
        }
        else{
            progressDialog = ProgressDialog.show(mContext, "",
                    "Loading ...", false);
            progressDialog.setCancelable(false);
            mAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(mContext, "Dang nhap thanh cong", Toast.LENGTH_SHORT).show();
                                userOrPhotographer();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(mContext, "Tai khoan chua duoc dang ki hoac sai tai khoan mat khau", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }

    }
    public void userOrPhotographer(){
        final String[] y = {""};
        db.collection("users")
                .whereEqualTo("email", edtName.getText().toString()+"")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                y[0] = String.valueOf(document.getData().get("role"));
                                SharedPreferencesUtils.setString(mContext,Constants.ID_USER,String.valueOf(document.getId()));
                                SharedPreferencesUtils.setString(mContext,Constants.AVATAR_USER, String.valueOf(document.getData().get("avatar")));
                                SharedPreferencesUtils.setString(mContext,Constants.BIO_USER,String.valueOf(document.getData().get("bio")));
                                SharedPreferencesUtils.setString(mContext,Constants.BIRTH_USER,String.valueOf(document.getData().get("birth")));
                                SharedPreferencesUtils.setString(mContext,Constants.EMAIL_USER,String.valueOf(document.getData().get("email")));
                                SharedPreferencesUtils.setString(mContext,Constants.LOCATION_USER,String.valueOf(document.getData().get("location")));
                                SharedPreferencesUtils.setString(mContext,Constants.NAME_USER,String.valueOf(document.getData().get("name")));
                                SharedPreferencesUtils.setString(mContext,Constants.PHONE_USER,String.valueOf(document.getData().get("phone")));
                                SharedPreferencesUtils.setString(mContext,Constants.RATING_USER,String.valueOf(document.getData().get("rating")));
                                SharedPreferencesUtils.setString(mContext,Constants.ROLE_USER,String.valueOf(document.getData().get("role")));
                                Log.i("HienNGuuuuu", y[0] +"");
                                SharedPreferencesUtils.setBoolean(mContext,Constants.CHECK_FIRST,true);


                            }
                            if(y[0].equals("User")==true){
                                Intent intent= new Intent(mContext, MainUserActivity.class);
                                startActivity(intent);
                            }
                            else{
                                if(y[0].equals("Photographer")==true){
                                    Intent intent= new Intent(mContext, MainPhotographerActivity.class);
                                    startActivity(intent);
                                }
                            }
                            progressDialog.dismiss();
                            getActivity().finish();
                        } else {
                            Log.d("HienNgusdf", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }



}
