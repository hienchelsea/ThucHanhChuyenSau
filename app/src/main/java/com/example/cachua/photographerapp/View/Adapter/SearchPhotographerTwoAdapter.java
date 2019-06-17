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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Activity.MainUserActivity;

import com.example.cachua.photographerapp.View.Fragment.User.Home.ImgAlbumPhotographerFragment;
import com.example.cachua.photographerapp.View.Fragment.User.Home.PersonalPagePhotographerFragment;
import com.example.cachua.photographerapp.View.Fragment.User.Home.PresonalpageFragment;
import com.example.cachua.photographerapp.View.Fragment.User.Profile.ProfileImgAlbumFragment;
import com.example.cachua.photographerapp.View.Model.FavoritesModel;
import com.example.cachua.photographerapp.View.Model.UseModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

public class SearchPhotographerTwoAdapter extends RecyclerView.Adapter<SearchPhotographerTwoAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<UseModel> useModelArrayList;
    private ArrayList<FavoritesModel>favoritesModelArrayList;
    private ArrayList<FavoritesModel>favoritesModelArrayListSelect= new ArrayList<>();
    FavoritesModel favoritesModelDelete;
    private ArrayList<String> stringArrayListKt= new ArrayList<>();
    int ktra;

    public SearchPhotographerTwoAdapter(Context mContext, ArrayList<UseModel> useModelArrayList,ArrayList<FavoritesModel>favoritesModelArrayList) {
        this.useModelArrayList = useModelArrayList;
        this.favoritesModelArrayList = favoritesModelArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public SearchPhotographerTwoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater= LayoutInflater.from(mContext);
        View view= layoutInflater.inflate(R.layout.layout_adapter_search,viewGroup,false);
        SearchPhotographerTwoAdapter.ViewHolder viewHolder= new SearchPhotographerTwoAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchPhotographerTwoAdapter.ViewHolder viewHolder, final int i) {
        ktra=0;
        final UseModel useModel= useModelArrayList.get(i);
        for(int j=0;j<favoritesModelArrayList.size();j++){
            if(favoritesModelArrayList.get(j).getPhotographerId().equals(useModel.getId())==true){
                ktra=1;
                favoritesModelDelete=favoritesModelArrayList.get(j);
                Log.i("HienOcc", favoritesModelDelete.getId()+"onBindViewHolder: ");
            }
        }
        if(ktra==1){
            favoritesModelArrayListSelect.add(favoritesModelDelete);
        }
        else{
            if(ktra==0){
                favoritesModelArrayListSelect.add(new FavoritesModel("","",""));
            }
            else{

            }

        }
        stringArrayListKt.add(ktra+"");
        viewHolder.tvName.setText(useModel.getName());
        viewHolder.tvRating.setText(useModel.getRating()+"");
        Glide
                .with( mContext )
                .load( useModel.getAvatar() )
                .skipMemoryCache( true )
                .into( viewHolder.imgAvatar);


        viewHolder.llSearch.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                ArrayList<String> stringArrayList = new ArrayList<>();
                stringArrayList.add(useModel.getId());
                stringArrayList.add(useModel.getBio());
                stringArrayList.add(useModel.getBirth());
                stringArrayList.add(useModel.getEmail());
                stringArrayList.add(useModel.getLocation());
                stringArrayList.add(useModel.getName());
                stringArrayList.add(useModel.getPhone());
                stringArrayList.add(String.valueOf(useModel.getRating()));
                stringArrayList.add(useModel.getRole());
                stringArrayList.add(useModel.getAvatar());
                stringArrayList.add(stringArrayListKt.get(i));
                stringArrayList.add(favoritesModelArrayListSelect.get(i).getId());
                Bundle bundle= new Bundle();
                bundle.putStringArrayList("Photographer",stringArrayList);
                Toast.makeText(mContext, "HienNGu", Toast.LENGTH_SHORT).show();

                PresonalpageFragment profileHomePhotographerFragment= new PresonalpageFragment();
                profileHomePhotographerFragment.setArguments(bundle);
                ((MainUserActivity) Objects.requireNonNull(mContext)).nextFragment(profileHomePhotographerFragment, R.id.container, 0, 0, 0, 0);

            }
        });

    }

    @Override
    public int getItemCount() {
        return useModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar;
        private TextView tvName;
        private TextView tvRating;
        private LinearLayout llSearch;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar= itemView.findViewById(R.id.imgAvatar);
            tvName= itemView.findViewById(R.id.tvName);
            tvRating= itemView.findViewById(R.id.tvRating);
            llSearch= itemView.findViewById(R.id.llSearch);
        }
    }
    public void Update(Context mContext, ArrayList<UseModel> useModelArrayList) {
        this.mContext = mContext;
        this.useModelArrayList = useModelArrayList;
        notifyDataSetChanged();
    }
}
