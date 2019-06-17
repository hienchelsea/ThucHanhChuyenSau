package com.example.cachua.photographerapp.View.Adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Activity.MainUserActivity;
import com.example.cachua.photographerapp.View.Fragment.User.Home.PhotoDetailPhotographerFragment;
import com.example.cachua.photographerapp.View.Model.ImagesModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class AlbumUserPhotographerAdapter extends RecyclerView.Adapter<AlbumUserPhotographerAdapter.ViewHolder> {
    private ArrayList<ImagesModel> imagesModelArrayList;
    private Context mContext;
    int position;

    public AlbumUserPhotographerAdapter(ArrayList<ImagesModel> imagesModelArrayList, Context mContext) {
        this.imagesModelArrayList = imagesModelArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public AlbumUserPhotographerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater= LayoutInflater.from(mContext);
        View view= layoutInflater.inflate(R.layout.layout_adapter_album,viewGroup,false);
        AlbumUserPhotographerAdapter.ViewHolder viewHolder = new AlbumUserPhotographerAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumUserPhotographerAdapter.ViewHolder viewHolder, int i) {
        position=i;
        ImagesModel imagesModel= imagesModelArrayList.get(i);
        Glide
                .with(mContext)
                .load(imagesModel.getPath())
                .placeholder(R.drawable.common_google_signin_btn_text_dark_normal)
                .skipMemoryCache(true)
                .into(viewHolder.imgIMG);
        //Picasso.with(mContext).load(imagesModel.getPath()).placeholder(R.drawable.icon_user).into(viewHolder.imgIMG);


    }

    @Override
    public int getItemCount() {
        return imagesModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgIMG;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIMG=itemView.findViewById(R.id.imgIMG);

            imgIMG.setOnClickListener(this);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.imgIMG:{
                    PhotoDetailPhotographerFragment photoDetailPhotographerFragment= new PhotoDetailPhotographerFragment();
                    ((MainUserActivity) Objects.requireNonNull(mContext)).nextFragment(photoDetailPhotographerFragment, R.id.frameContainer, 0, 0, 0, 0);
                    break;
                }
            }
        }
    }
    public void Update(ArrayList<ImagesModel> imagesModelArrayList, Context mContext) {
        this.imagesModelArrayList = imagesModelArrayList;
        this.mContext = mContext;
        notifyDataSetChanged();
    }

    public interface CallBack{
        void ShowImg(ImagesModel imagesModel);
    }

    private CallBack callBack;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }
}
