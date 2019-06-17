package com.example.cachua.photographerapp.View.Fragment.User.Profile;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Activity.LoginActivity;
import com.example.cachua.photographerapp.View.Activity.MainUserActivity;
import com.example.cachua.photographerapp.View.Fragment.BaseFragment;
import com.example.cachua.photographerapp.View.Fragment.User.Home.AlbumPhotographerFragment;
import com.example.cachua.photographerapp.View.config.Constants;
import com.example.cachua.photographerapp.View.config.SharedPreferencesUtils;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends BaseFragment {
    private TextView tvEditProfile;
    private TextView tvCoupons;
    private TextView tvName;
    private TextView tvNumber;
    private TextView tvEmail;
    private TextView tvFullName;
    private ImageView imvHistory;
    private ImageView imvLove;
    private ImageView tvReview;
    private ImageView imvAlbum;
    private CircleImageView cimvAvatar;
    private Dialog dialog;

    public static ProfileFragment sInstance;
    public static ProfileFragment newInstance() {
        if (sInstance == null) {
            sInstance = new ProfileFragment();
        }
        return sInstance;
    }

    @Override
    protected int getLayoutResource() {
        return (R.layout.fragment_profile);
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {

        tvEditProfile= rootView.findViewById(R.id.tvEditProfile);
        tvCoupons= rootView.findViewById(R.id.tvCoupons);
        imvHistory= rootView.findViewById(R.id.imvHistory);
        imvLove= rootView.findViewById(R.id.imvLove);
        tvReview= rootView.findViewById(R.id.tvReview);
        tvFullName= rootView.findViewById(R.id.tvFullName);
        tvEmail= rootView.findViewById(R.id.tvEmail);
        tvNumber= rootView.findViewById(R.id.tvNumber);
        tvName= rootView.findViewById(R.id.tvName);
        imvAlbum= rootView.findViewById(R.id.imvAlbum);
        cimvAvatar= rootView.findViewById(R.id.cimvAvatar);




        tvEditProfile.setOnClickListener(this);
        tvCoupons.setOnClickListener(this);
        imvHistory.setOnClickListener(this);
        imvLove.setOnClickListener(this);
        tvReview.setOnClickListener(this);
        imvAlbum.setOnClickListener(this);


    }

    @Override
    protected void initData(Bundle savedInstanceState) {


    }
    

    @Override
    public void onResume() {
        Toast.makeText(mContext, "DDDDD", Toast.LENGTH_SHORT).show();
        super.onResume();
        tvName.setText(SharedPreferencesUtils.getString(mContext, Constants.NAME_USER));
        tvFullName.setText(SharedPreferencesUtils.getString(mContext, Constants.NAME_USER));
        tvEmail.setText(SharedPreferencesUtils.getString(mContext, Constants.EMAIL_USER));
        tvNumber.setText(SharedPreferencesUtils.getString(mContext, Constants.PHONE_USER));
       // Picasso.with(mContext).load(SharedPreferencesUtils.getString(mContext,Constants.AVATAR_USER)).into(cimvAvatar);
        Glide
                .with( mContext )
                .load( SharedPreferencesUtils.getString(mContext,Constants.AVATAR_USER) )
                .skipMemoryCache( true )
                .into(cimvAvatar );
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvEditProfile:{
                ProfileUserFragment profileUserFragment= new ProfileUserFragment();
                ((MainUserActivity)Objects.requireNonNull(mContext)).nextFragment(profileUserFragment,R.id.container,0,0,0,0);
                break;
            }
            case R.id.tvCoupons: {
                ProfileGiftFragment ProfileGiftFragment = new ProfileGiftFragment();
                ((MainUserActivity) Objects.requireNonNull(mContext)).nextFragment(ProfileGiftFragment, R.id.container, 0, 0, 0, 0);
                break;
            }
            case R.id.imvHistory: {
                ProfileHistoryFragment profileHistoryFragment = new ProfileHistoryFragment();
                ((MainUserActivity) Objects.requireNonNull(mContext)).nextFragment(profileHistoryFragment, R.id.container, 0, 0, 0, 0);
                break;
            }
            case R.id.imvLove:{
                ProfileLoveFragment profileLoveFragment= new ProfileLoveFragment();
                ((MainUserActivity)Objects.requireNonNull(mContext)).nextFragment(profileLoveFragment,R.id.container,0,0,0,0);
                break;
            }
            case R.id.imvAlbum: {
               Bundle bundle= new Bundle();
                bundle.putString("idPhotographer",SharedPreferencesUtils.getString(mContext,Constants.ID_USER));
//                ProfileAlbumFragment profileAlbumFragment = new ProfileAlbumFragment();
//                profileAlbumFragment.setArguments(bundle);
//                ((MainUserActivity) Objects.requireNonNull(mContext)).nextFragment(profileAlbumFragment, R.id.container, 0, 0, 0, 0);
                ProfileAlbumFragment albumPhotographerFragment = new ProfileAlbumFragment();
                albumPhotographerFragment.setArguments(bundle);
                ((MainUserActivity) Objects.requireNonNull(mContext)).nextFragment(albumPhotographerFragment, R.id.frameContainer, 0, 0, 0, 0);
                break;
            }
            case R.id.tvReview: {
                ProfileReviewFragment profileReviewFragment = new ProfileReviewFragment();
                ((MainUserActivity) Objects.requireNonNull(mContext)).nextFragment(profileReviewFragment, R.id.container, 0, 0, 0, 0);
                break;
            }
        }

    }
    public void showDialog(){

        dialog= new Dialog(mContext);
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
                Intent intent= new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                dialog.dismiss();
                getActivity().finish();

            }
        });


        dialog.show();
    }
}
