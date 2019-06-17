package com.example.cachua.photographerapp.View.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Model.OrdersModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilePhotographerListWaitAdapter extends RecyclerView.Adapter<ProfilePhotographerListWaitAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<OrdersModel> ordersModelArrayList;

    public ProfilePhotographerListWaitAdapter(Context mContext, ArrayList<OrdersModel> ordersModelArrayList) {
        this.mContext = mContext;
        this.ordersModelArrayList = ordersModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater= LayoutInflater.from(mContext);
        View view= layoutInflater.inflate(R.layout.layout_adapter_photographer_list_wait,viewGroup,false);
        ProfilePhotographerListWaitAdapter.ViewHolder viewHolder = new ProfilePhotographerListWaitAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
//        OrdersModel ordersModel= ordersModelArrayList.get(i);
//        viewHolder.tvPhotographer.setText(ordersModel.getPhotographer());
//        viewHolder.tvTimeBook.setText(ordersModel.getDate());
//        viewHolder.tvLocation.setText(ordersModel.getLocation());
//        viewHolder.tvCount.setText(ordersModel.getPeople()+"");
//        viewHolder.tvIdOder.setText(ordersModel.getId()+"");
//        viewHolder.tvTotal.setText(ordersModel.getTotal()+"");
        Picasso.with(mContext).load("https://firebasestorage.googleapis.com/v0/b/hienoc-3503c.appspot.com/o/image%3A49081?alt=media&token=be9fa927-78d0-4302-b380-c956f153ab0b").placeholder(R.drawable.icon_user).into(viewHolder.imgAvatar);

    }

    @Override
    public int getItemCount() {
        return ordersModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvPhotographer;
        private TextView tvTimeBook;
        private TextView tvLocation;
        private TextView tvCount;
        private TextView tvIdOder;
        private TextView tvTotal;
        private CircleImageView imgAvatar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPhotographer= itemView.findViewById(R.id.tvPhotographer);
            tvTimeBook= itemView.findViewById(R.id.tvTimeBook);
            tvLocation= itemView.findViewById(R.id.tvLocation);
            tvCount= itemView.findViewById(R.id.tvCount);
            tvIdOder= itemView.findViewById(R.id.tvIdOder);
            tvTotal= itemView.findViewById(R.id.tvTotal);
            imgAvatar= itemView.findViewById(R.id.imgAvatar);
        }
    }
}
