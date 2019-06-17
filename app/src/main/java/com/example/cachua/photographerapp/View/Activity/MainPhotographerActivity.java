package com.example.cachua.photographerapp.View.Activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Fragment.Photographer.Coupons.AddCouponsPhotographerFragment;
import com.example.cachua.photographerapp.View.Fragment.Photographer.Coupons.CouponsPhotographerFragment;
import com.example.cachua.photographerapp.View.Fragment.Photographer.List.ListPhotographerFragment;
import com.example.cachua.photographerapp.View.Fragment.Photographer.Messenger.MessengerPhotographerFragment;
import com.example.cachua.photographerapp.View.Fragment.Photographer.Notice.NoticePhotographerFragment;
import com.example.cachua.photographerapp.View.Fragment.Photographer.Options.OptionPhotographerFragment;
import com.example.cachua.photographerapp.View.Fragment.Photographer.Profile.ProfilePhotographerFragment;
import com.example.cachua.photographerapp.View.Fragment.Photographer.Schedule.SchedulePhotographerFragment;
import com.example.cachua.photographerapp.View.Fragment.User.Home.PersonalPagePhotographerFragment;
import com.example.cachua.photographerapp.View.Fragment.User.UserToPhoto.ProfileUserToPhotographerFragment;
import com.example.cachua.photographerapp.View.config.BottomNavigationViewHelper;
import com.example.cachua.photographerapp.View.config.Constants;
import com.example.cachua.photographerapp.View.config.SharedPreferencesUtils;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainPhotographerActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView navigation;
    private ImageView imgMenu;


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main_photographer;
    }

    @Override
    protected void initView() {
        navigation= findViewById(R.id.navigation);
        imgMenu= findViewById(R.id.imgMenu);
        navigation.setOnNavigationItemSelectedListener(this);

        imgMenu.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        BottomNavigationViewHelper.removeShiftMode(navigation);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgMenu:{
                menu();
                break;
            }
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment selectedFragment = null;
        switch (menuItem.getItemId()) {
            case R.id.navigation_Schedule: {
                selectedFragment = SchedulePhotographerFragment.newInstance();
                break;
            }
            case R.id.navigation_List: {
                selectedFragment = ListPhotographerFragment.newInstance();
                break;
            }
            case R.id.navigation_Messenger: {
                selectedFragment = MessengerPhotographerFragment.newInstance();

                break;
            }
            case R.id.navigation_Notice: {
                selectedFragment = NoticePhotographerFragment.newInstance();

                break;
            }
            case R.id.navigation_Profile: {
                selectedFragment = ProfilePhotographerFragment.newInstance();

                break;
            }



        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.tabContainer, selectedFragment);
        transaction.commit();

        return true;
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
            Fragment fragmentBackStack = getSupportFragmentManager().findFragmentById(R.id.frameContainer);
            if (fragmentBackStack instanceof AddCouponsPhotographerFragment
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
    @SuppressLint("RestrictedApi")
    public void menu(){
        MenuBuilder menuBuilder = new MenuBuilder(this);
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu_main_photographer_activity, menuBuilder);
        MenuPopupHelper optionsMenu = new MenuPopupHelper(this, menuBuilder, imgMenu);
        optionsMenu.setForceShowIcon(false);
        optionsMenu.show();
        menuBuilder.setCallback(new MenuBuilder.Callback() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuIntroduce:

                        return true;
                    case R.id.menuKindMoney:

                        return true;
                    case R.id.menuUserToPhotographer:
                        FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();
                        final DocumentReference washingtonRef = firebaseFirestore.collection("users").document(SharedPreferencesUtils.getString(getBaseContext(),Constants.ID_USER) + "");
                        washingtonRef
                                .update("role", "User");
                        SharedPreferencesUtils.setBoolean(MainPhotographerActivity.this, Constants.CHECK_FIRST,false);
                        Intent intent= new Intent(MainPhotographerActivity.this, LoginActivity.class);
                        startActivity(intent);


                        return true;
                    case R.id.menuLogOut:
                        showDialog();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onMenuModeChange(MenuBuilder menu) {

            }
        });
    }
    public void showDialog(){

        final Dialog dialog= new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dailog_delete_note);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Window window = dialog.getWindow();
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        Button btnOK= dialog.findViewById(R.id.btnOk);
        Button btnCancel= dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                SharedPreferencesUtils.setBoolean(MainPhotographerActivity.this, Constants.CHECK_FIRST,false);
                Intent intent= new Intent(MainPhotographerActivity.this, LoginActivity.class);
                startActivity(intent);
                dialog.dismiss();

            }
        });


        dialog.show();
    }
}
