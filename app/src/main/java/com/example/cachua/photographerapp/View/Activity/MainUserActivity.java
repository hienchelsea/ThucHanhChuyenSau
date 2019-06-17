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
import com.example.cachua.photographerapp.View.Fragment.User.Home.HomeFragment;
import com.example.cachua.photographerapp.View.Fragment.User.Home.PersonalPagePhotographerFragment;
import com.example.cachua.photographerapp.View.Fragment.User.Notice.NoticeFragment;
import com.example.cachua.photographerapp.View.Fragment.User.PhotographerFragment;
import com.example.cachua.photographerapp.View.Fragment.User.Profile.ProfileFragment;

import com.example.cachua.photographerapp.View.Fragment.User.UserToPhoto.ProfileUserToPhotographerFragment;
import com.example.cachua.photographerapp.View.Model.OptionsModel;
import com.example.cachua.photographerapp.View.config.BottomNavigationViewHelper;
import com.example.cachua.photographerapp.View.config.Constants;
import com.example.cachua.photographerapp.View.config.SharedPreferencesUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class MainUserActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView navigation;
    private ImageView imgMenu;
    private ArrayList<OptionsModel> optionsModelArrayList= new ArrayList<>();


    @Override
    protected int getLayoutResource() {
        return (R.layout.activity_main);
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
        LoadDataOptions(optionsModelArrayList);



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
            case R.id.navigation_Home: {
                selectedFragment = HomeFragment.newInstance();
                break;
            }
            case R.id.navigation_Messenage: {
                selectedFragment = PhotographerFragment.newInstance();
                break;
            }
            case R.id.navigation_Notice: {
                selectedFragment = NoticeFragment.newInstance();

                break;
            }
            case R.id.navigation_Profile: {
                selectedFragment = ProfileFragment.newInstance();

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
            if (fragmentBackStack instanceof PersonalPagePhotographerFragment
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

    @SuppressLint("RestrictedApi")
    public void menu(){
        MenuBuilder menuBuilder = new MenuBuilder(this);
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu_main_activity, menuBuilder);
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
                        if(optionsModelArrayList.size()==0){
                            Fragment selectedFragment= ProfileUserToPhotographerFragment.newInstance();
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.frameContainer, selectedFragment);
                            transaction.commit();
                        }
                        else{
                            FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();
                            final DocumentReference washingtonRef = firebaseFirestore.collection("users").document(SharedPreferencesUtils.getString(getBaseContext(),Constants.ID_USER) + "");
                            washingtonRef
                                    .update("role", "Photographer");
                            SharedPreferencesUtils.setBoolean(MainUserActivity.this,Constants.CHECK_FIRST,false);
                            Intent intent= new Intent(MainUserActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }


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
                SharedPreferencesUtils.setBoolean(MainUserActivity.this,Constants.CHECK_FIRST,false);
                Intent intent= new Intent(MainUserActivity.this, LoginActivity.class);
                startActivity(intent);
                dialog.dismiss();

            }
        });


        dialog.show();
    }
    private void LoadDataOptions(final ArrayList<OptionsModel> optionsModelArrayList) {
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        db.collection("options")
                .whereEqualTo("userId",SharedPreferencesUtils.getString(getBaseContext(),Constants.ID_USER)+"")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {


                        if (task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                OptionsModel optionsModel= new OptionsModel(documentSnapshot.getId()+"",String.valueOf(documentSnapshot.getData().get("description")),
                                        String.valueOf(documentSnapshot.getData().get("name")),Float.parseFloat(String.valueOf(documentSnapshot.getData().get("price_per_day"))),
                                        Float.parseFloat(String.valueOf(documentSnapshot.getData().get("price_per_hour"))),Integer.parseInt(String.valueOf(documentSnapshot.getData().get("prints"))),
                                        Integer.parseInt(String.valueOf(documentSnapshot.getData().get("shots"))),String.valueOf(documentSnapshot.getData().get("type")),
                                        String.valueOf(documentSnapshot.getData().get("userId")));
                                optionsModelArrayList.add(optionsModel);
                            }
                        }
                    }
                });
    }
}
