package com.example.cachua.photographerapp.View.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Model.FavoritesModel;
import com.example.cachua.photographerapp.View.Model.OrdersModel;
import com.squareup.picasso.Picasso;

import java.sql.Timestamp;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HistoryPhotographerAdapter extends RecyclerView.Adapter<HistoryPhotographerAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<OrdersModel> ordersModelArrayList;
    private ArrayList<FavoritesModel>favoritesModelArrayList;
    private ArrayList<FavoritesModel>favoritesModelArrayListSelect= new ArrayList<>();
    FavoritesModel favoritesModelDelete;
    private ArrayList<String> stringArrayListKt= new ArrayList<>();
    int ktra;

    public HistoryPhotographerAdapter(Context mContext, ArrayList<OrdersModel> ordersModelArrayList,ArrayList<FavoritesModel>favoritesModelArrayList) {
        this.mContext = mContext;
        this.ordersModelArrayList = ordersModelArrayList;
        this.favoritesModelArrayList = favoritesModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater= LayoutInflater.from(mContext);
        View view= layoutInflater.inflate(R.layout.layout_adapter_history,viewGroup,false);
        HistoryPhotographerAdapter.ViewHolder viewHolder = new HistoryPhotographerAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        OrdersModel ordersModel= ordersModelArrayList.get(i);

        viewHolder.tvPhotographer.setText(ordersModel.getPhotographerId());
        Timestamp timestamp= ordersModel.getEndAt();
       // viewHolder.tvTimeBook.setText(timestamp.getDay()+"/"+timestamp.getMonth());
        viewHolder.tvLocation.setText(ordersModel.getAddress());
        viewHolder.tvTotal.setText(ordersModel.getTotal()+"");
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
        private TextView tvIdOder;
        private TextView tvTotal;
        private Button btnBook;
        private CircleImageView imgAvatar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPhotographer= itemView.findViewById(R.id.tvPhotographer);
            tvTimeBook= itemView.findViewById(R.id.tvTimeBook);
            tvLocation= itemView.findViewById(R.id.tvLocation);
            tvIdOder= itemView.findViewById(R.id.tvIdOder);
            tvTotal= itemView.findViewById(R.id.tvTotal);
            imgAvatar= itemView.findViewById(R.id.imgAvatar);
            btnBook= itemView.findViewById(R.id.btnBook);
        }
    }
    public void Update(Context mContext, ArrayList<OrdersModel> ordersModelArrayList) {
        this.mContext = mContext;
        this.ordersModelArrayList = ordersModelArrayList;
        notifyDataSetChanged();
    }

}
