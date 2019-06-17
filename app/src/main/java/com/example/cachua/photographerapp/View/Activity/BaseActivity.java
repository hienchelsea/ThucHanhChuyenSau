package com.example.cachua.photographerapp.View.Activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.cachua.photographerapp.R;

import static com.example.cachua.photographerapp.View.config.Constants.MAIN_CONTENT_FADE_IN_DURATION;


public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    public static String PACKAGE_NAME;
    protected int currentApiVersion;

    protected abstract int getLayoutResource();

    protected abstract void initView();

    protected abstract void initData();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        currentApiVersion = Build.VERSION.SDK_INT;
        PACKAGE_NAME = getApplicationContext().getPackageName();
        this.initView();
        this.initData();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        View mainContent = findViewById(R.id.frameContainer);
        if (mainContent != null) {
            mainContent.setAlpha(0);
            mainContent.animate().alpha(1).setDuration(MAIN_CONTENT_FADE_IN_DURATION);
        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
