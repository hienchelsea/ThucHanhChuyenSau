package com.example.cachua.photographerapp.View.Activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Fragment.Login.LoginFragment;

import java.util.Objects;

public class LoginActivity extends BaseActivity {
    private LinearLayout llLogin;

    @Override
    protected int getLayoutResource() {
        return (R.layout.activity_login);
    }

    @Override
    protected void initView() {
        llLogin= findViewById(R.id.llLogin);
    }

    @Override
    protected void initData() {

    }

    public void nextFragment(Fragment fragment, int id, int anim1, int anim2, int anim3, int anim4) {
        String backStateName = fragment.getClass().getName();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(anim1, anim2, anim3, anim4);
        transaction.replace(id, fragment);
        transaction.addToBackStack(backStateName);
        transaction.commit();
    }
    @Override
    public void onBackPressed() {
        try {
            Fragment fragmentBackStack = getSupportFragmentManager().findFragmentById(R.id.llLogin);
            if (fragmentBackStack instanceof LoginFragment
//                    || fragmentBackStack instanceof TipsFragment
                    ) {
                getSupportFragmentManager().popBackStack(fragmentBackStack.getClass().getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            } else {
                super.onBackPressed();
            }
        } catch (Exception e) {
            super.onBackPressed();
            System.exit(0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View v) {

    }
}
