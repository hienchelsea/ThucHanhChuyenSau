package com.example.cachua.photographerapp.View.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.cachua.photographerapp.R;

import java.util.ArrayList;

public class ImvUserToPhotographerAdapter extends RecyclerView.Adapter<ImvUserToPhotographerAdapter.ViewHolder> {
    private ArrayList<String> stringArrayList;
    private Context mContext;

    public ImvUserToPhotographerAdapter(ArrayList<String> stringArrayList, Context mContext) {
        this.stringArrayList = stringArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater= LayoutInflater.from(mContext);
        View view= layoutInflater.inflate(R.layout.layout_adapter_imv_user_to_grapher,viewGroup,false);
        ImvUserToPhotographerAdapter.ViewHolder viewHolder= new ImvUserToPhotographerAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String s= stringArrayList.get(i);
        Glide.with(mContext).load(s).skipMemoryCache(true).into(viewHolder.imvImv);
    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imvImv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imvImv= itemView.findViewById(R.id.imvImv);
        }
    }
    public void Update(ArrayList<String> stringArrayList, Context mContext) {
        this.stringArrayList = stringArrayList;
        this.mContext = mContext;
        notifyDataSetChanged();
    }
}
