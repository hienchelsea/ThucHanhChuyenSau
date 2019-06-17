package com.example.cachua.photographerapp.View.Adapter;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Activity.MainPhotographerActivity;
import com.example.cachua.photographerapp.View.Activity.MainUserActivity;
import com.example.cachua.photographerapp.View.Fragment.User.Home.ImgAlbumPhotographerFragment;
import com.example.cachua.photographerapp.View.Fragment.User.Profile.ProfileImgAlbumFragment;
import com.example.cachua.photographerapp.View.Model.AlbumModel;
import com.example.cachua.photographerapp.View.Model.ImagesModel;
import com.example.cachua.photographerapp.View.config.Constants;
import com.example.cachua.photographerapp.View.config.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.Objects;

public class AlbumPhotographerAdapter extends RecyclerView.Adapter<AlbumPhotographerAdapter.ViewHolder> {
    private ArrayList<AlbumModel> albumModelArrayList;
    private Context mContext;
    private String role;

    public AlbumPhotographerAdapter(ArrayList<AlbumModel> albumModelArrayList, Context mContext,String role) {
        this.albumModelArrayList = albumModelArrayList;
        this.mContext = mContext;
        this.role = role;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater= LayoutInflater.from(mContext);
        View view= layoutInflater.inflate(R.layout.layout_adapter_album_photographer,viewGroup,false);
        AlbumPhotographerAdapter.ViewHolder viewHolder= new AlbumPhotographerAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final AlbumModel albumModel= albumModelArrayList.get(i);

        viewHolder.tvName.setText(albumModel.getName());
        viewHolder.llAlbum.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                    final Bundle bundle= new Bundle();
                    bundle.putString("IdAlbum",albumModel.getId());
                    if(SharedPreferencesUtils.getString(mContext, Constants.ROLE_USER).equals("Photographer")==true){
                        ProfileImgAlbumFragment profileImgAlbumFragment= new ProfileImgAlbumFragment();
                        profileImgAlbumFragment.setArguments(bundle);
                        ((MainPhotographerActivity) Objects.requireNonNull(mContext)).nextFragment(profileImgAlbumFragment, R.id.frameContainer, 0, 0, 0, 0);
                    }
                    else{
                        if(role.equals("UserToGrapher")==true){
                            ImgAlbumPhotographerFragment imgAlbumPhotographerFragment= new ImgAlbumPhotographerFragment();
                            imgAlbumPhotographerFragment.setArguments(bundle);
                            ((MainUserActivity) Objects.requireNonNull(mContext)).nextFragment(imgAlbumPhotographerFragment, R.id.frameContainer, 0, 0, 0, 0);
                        }
                        else{
                            if(role.equals("UserToUser")==true){
                                ProfileImgAlbumFragment profileImgAlbumFragment= new ProfileImgAlbumFragment();
                                profileImgAlbumFragment.setArguments(bundle);
                                ((MainUserActivity) Objects.requireNonNull(mContext)).nextFragment(profileImgAlbumFragment, R.id.frameContainer, 0, 0, 0, 0);

                            }
                        }
                    }


//                ProfileImgAlbumFragment profileImgAlbumFragment= new ProfileImgAlbumFragment();
//                ((MainUserActivity) Objects.requireNonNull(mContext)).nextFragment(profileImgAlbumFragment, R.id.frameContainer, 0, 0, 0, 0);
            }
        });


    }

    @Override
    public int getItemCount() {
        return albumModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout llAlbum;
        private TextView tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            llAlbum= itemView.findViewById(R.id.llAlbum);
            tvName= itemView.findViewById(R.id.tvName);
        }
    }
    public void Update(ArrayList<AlbumModel> albumModelArrayList, Context mContext) {
        this.albumModelArrayList = albumModelArrayList;
        this.mContext = mContext;
        notifyDataSetChanged();
    }
}
