package com.example.cachua.photographerapp.View.Fragment.Login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.cachua.photographerapp.View.Activity.MainPhotographerActivity;
import com.example.cachua.photographerapp.View.Activity.MainUserActivity;
import com.example.cachua.photographerapp.View.config.Constants;
import com.example.cachua.photographerapp.View.config.SharedPreferencesUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class UserFile {

    public static void signIn(final String email, String pass, final Context mContext, Activity activity) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        if(email.equals("")==true||pass.equals("")==true){
            SharedPreferencesUtils.setInt(mContext, Constants.CHECK_LOGIN,0);
            Toast.makeText(mContext, "Loi Email da dang ki", Toast.LENGTH_SHORT).show();
        }
        else{
            mAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(mContext, "Dang nhap thanh cong", Toast.LENGTH_SHORT).show();
                                SharedPreferencesUtils.setInt(mContext, Constants.CHECK_LOGIN,1);
                            } else {
                                SharedPreferencesUtils.setInt(mContext, Constants.CHECK_LOGIN,0);
                                Toast.makeText(mContext, "Tai khoan chua duoc dang ki hoac sai tai khoan mat khau", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }

    }
    public static void userOrPhotographer(FirebaseFirestore db, final Context mContext, String email){
        final String[] y = {""};
        db.collection("users")
                .whereEqualTo("email", email+"")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                y[0] = String.valueOf(document.getData().get("role"));
                                Log.i("HienNGuuuuu", y[0] +"");


                            }
                            if(y[0].equals("User")==true){
                                SharedPreferencesUtils.setString(mContext,Constants.CHECK_PERMISSION,"User");
                            }
                            else{
                                if(y[0].equals("Photographer")==true){
                                    SharedPreferencesUtils.setString(mContext,Constants.CHECK_PERMISSION,"Photographer");
                                }
                            }

                        } else {
                            Log.d("HienNgusdf", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }
}
