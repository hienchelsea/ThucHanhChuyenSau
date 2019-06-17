package com.example.cachua.photographerapp.View.Fragment.Photographer.Profile;

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

import com.bumptech.glide.Glide;
import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Activity.LoginActivity;
import com.example.cachua.photographerapp.View.Activity.MainPhotographerActivity;
import com.example.cachua.photographerapp.View.Activity.MainUserActivity;
import com.example.cachua.photographerapp.View.Fragment.BaseFragment;
import com.example.cachua.photographerapp.View.Fragment.Photographer.Coupons.CouponsPhotographerFragment;
import com.example.cachua.photographerapp.View.Fragment.Photographer.Options.AddOptionPhotographerFragment;
import com.example.cachua.photographerapp.View.Fragment.Photographer.Options.OptionPhotographerFragment;
import com.example.cachua.photographerapp.View.Fragment.Photographer.Schedule.SchedulePhotographerFragment;
import com.example.cachua.photographerapp.View.Fragment.User.Profile.ProfileAlbumFragment;
import com.example.cachua.photographerapp.View.Fragment.User.Profile.ProfileHistoryFragment;
import com.example.cachua.photographerapp.View.Fragment.User.Profile.ProfileMoneyFragment;
import com.example.cachua.photographerapp.View.config.Constants;
import com.example.cachua.photographerapp.View.config.SharedPreferencesUtils;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilePhotographerFragment extends BaseFragment {
//    private LinearLayout llProfile;
//    private LinearLayout llReview;
//    private LinearLayout llLogOut;
//    private LinearLayout llMoney;
//    private LinearLayout llHistory;
//    private LinearLayout llListWait;
//    private LinearLayout llPhotographerToUser;
//    private LinearLayout llStatistical;
    private ImageView imvOption;
    private ImageView imvAlbum;
    private ImageView imvCoupons;
    private TextView tvName;
    private TextView tvNumber;
    private TextView tvEmail;
    private TextView tvFullName;
    private CircleImageView cimvAvatar;
    private Dialog dialog;


    public static ProfilePhotographerFragment sInstance;

    public static ProfilePhotographerFragment newInstance() {
        if (sInstance == null) {
            sInstance = new ProfilePhotographerFragment();
        }
        return sInstance;
    }
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_profile_photographer;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
//        llProfile= rootView.findViewById(R.id.llProfile);
//        llReview= rootView.findViewById(R.id.llReview);
//        llLogOut= rootView.findViewById(R.id.llLogOut);
//        llMoney= rootView.findViewById(R.id.llMoney);
//        llHistory= rootView.findViewById(R.id.llHistory);
//        llListWait= rootView.findViewById(R.id.llListWait);
//        llPhotographerToUser= rootView.findViewById(R.id.llPhotographerToUser);
//        llStatistical= rootView.findViewById(R.id.llStatistical);
//        llStatistical= rootView.findViewById(R.id.llStatistical);

        tvFullName= rootView.findViewById(R.id.tvFullName);
        tvEmail= rootView.findViewById(R.id.tvEmail);
        tvNumber= rootView.findViewById(R.id.tvNumber);
        tvName= rootView.findViewById(R.id.tvName);
        cimvAvatar= rootView.findViewById(R.id.cimvAvatar);
        imvOption= rootView.findViewById(R.id.imvOption);
        imvCoupons= rootView.findViewById(R.id.imvCoupons);
        imvAlbum= rootView.findViewById(R.id.imvAlbum);
//
//
//        llProfile.setOnClickListener(this);
//        llReview.setOnClickListener(this);
//        llLogOut.setOnClickListener(this);
//        llMoney.setOnClickListener(this);
//        llHistory.setOnClickListener(this);
//        llListWait.setOnClickListener(this);
//        llPhotographerToUser.setOnClickListener(this);
//        llStatistical.setOnClickListener(this);
        imvOption.setOnClickListener(this);
        imvCoupons.setOnClickListener(this);
        imvAlbum.setOnClickListener(this);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    public void onResume() {
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
            case R.id.imvOption: {
                OptionPhotographerFragment optionPhotographerFragment = new OptionPhotographerFragment();
                ((MainPhotographerActivity) Objects.requireNonNull(mContext)).nextFragment(optionPhotographerFragment, R.id.frameContainer, 0, 0, 0, 0);
                break;
            }
            case R.id.imvCoupons: {
                CouponsPhotographerFragment couponsPhotographerFragment = new CouponsPhotographerFragment();
                ((MainPhotographerActivity) Objects.requireNonNull(mContext)).nextFragment(couponsPhotographerFragment, R.id.frameContainer, 0, 0, 0, 0);
                break;
            }
            case R.id.imvAlbum:{
                Bundle bundle= new Bundle();
                bundle.putString("idPhotographer",SharedPreferencesUtils.getString(mContext,Constants.ID_USER));
                ProfileAlbumFragment profileAlbumFragment= new ProfileAlbumFragment();
                profileAlbumFragment.setArguments(bundle);
                ((MainPhotographerActivity)Objects.requireNonNull(mContext)).nextFragment(profileAlbumFragment,R.id.frameContainer,0,0,0,0);
                break;
            }
//            case R.id.llProfile:{
//                ProfilePhotographerInformationFragment profilePhotographerInformationFragment= new ProfilePhotographerInformationFragment();
//                ((MainPhotographerActivity) Objects.requireNonNull(mContext)).nextFragment(profilePhotographerInformationFragment, R.id.frameContainer, 0, 0, 0, 0);
//                break;
//            }
//            case R.id.llReview:{
//                ProfilePhotographerReviewFragment profilePhotographerReviewFragment= new ProfilePhotographerReviewFragment();
//                ((MainPhotographerActivity) Objects.requireNonNull(mContext)).nextFragment(profilePhotographerReviewFragment, R.id.frameContainer, 0, 0, 0, 0);
//                break;
//            }
//            case R.id.llMoney:{
//                ProfileMoneyFragment profileMoneyFragment= new ProfileMoneyFragment();
//                ((MainPhotographerActivity) Objects.requireNonNull(mContext)).nextFragment(profileMoneyFragment, R.id.frameContainer, 0, 0, 0, 0);
//                break;
//            }
//            case R.id.llHistory:{
//                ProfilePhotographerHistoryFragment profilePhotographerHistoryFragment= new ProfilePhotographerHistoryFragment();
//                ((MainPhotographerActivity)Objects.requireNonNull(mContext)).nextFragment(profilePhotographerHistoryFragment,R.id.container,0,0,0,0);
//                break;
//            }
//            case R.id.llListWait:{
//                ProfilePhotographerListWaitFragment profilePhotographerListWaitFragment= new ProfilePhotographerListWaitFragment();
//                ((MainPhotographerActivity)Objects.requireNonNull(mContext)).nextFragment(profilePhotographerListWaitFragment,R.id.container,0,0,0,0);
//                break;
//            }
//            case R.id.llStatistical:{
//                ProfilePhotographerStatisticalFragment profilePhotographerStatisticalFragment= new ProfilePhotographerStatisticalFragment();
//                ((MainPhotographerActivity)Objects.requireNonNull(mContext)).nextFragment(profilePhotographerStatisticalFragment,R.id.container,0,0,0,0);
//                break;
//            }
//            case R.id.llLogOut:{
//                showDialogLogOut();
//                break;
//            }
//            case R.id.llPhotographerToUser:{
//                showDialog();
//                break;
//            }
        }

    }
    public void showDialogLogOut(){

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
    public void showDialog(){

        dialog= new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dailog_photo_to_user);
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
