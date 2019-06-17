package com.example.cachua.photographerapp.View.Fragment.User.Home;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Fragment.BaseFragment;
import com.example.cachua.photographerapp.View.Model.ImagesModel;
import com.squareup.picasso.Picasso;

public class PhotoDetailPhotographerFragment extends BaseFragment {
    private ImageView imvIMG;
    private TextView tvName;
    @Override
    protected int getLayoutResource() {
        return (R.layout.fragment_profile_home_photographer_photo_detail);
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        imvIMG= rootView.findViewById(R.id.imvIMG);
        tvName= rootView.findViewById(R.id.tvName);


    }

    @Override
    protected void initData(Bundle savedInstanceState) {
       // ImagesModel imagesModel=  new ImagesModel(0,"hhu","hhu","huhu","https://firebasestorage.googleapis.com/v0/b/hienoc-3503c.appspot.com/o/image%3A49081?alt=media&token=be9fa927-78d0-4302-b380-c956f153ab0b","hien");
//        Picasso.with(mContext).load(imagesModel.getPath()).placeholder(R.drawable.icon_user).into(imvIMG);
      //  Picasso.with(mContext).load(imagesModel.getPath()).placeholder(R.drawable.icon_user).into(imvIMG);
      //  tvName.setText(imagesModel.getOwner());

    }

    @Override
    public void onClick(View v) {

    }
}
