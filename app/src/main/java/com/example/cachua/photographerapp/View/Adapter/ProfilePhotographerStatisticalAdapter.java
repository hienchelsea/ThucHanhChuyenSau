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

import java.util.ArrayList;

public class ProfilePhotographerStatisticalAdapter extends RecyclerView.Adapter<ProfilePhotographerStatisticalAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<OrdersModel> ordersModelArrayList;

    public ProfilePhotographerStatisticalAdapter(Context mContext, ArrayList<OrdersModel> ordersModelArrayList) {
        this.mContext = mContext;
        this.ordersModelArrayList = ordersModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater= LayoutInflater.from(mContext);
        View view= layoutInflater.inflate(R.layout.layout_adapter_photographer_list_statistical,viewGroup,false);
        ProfilePhotographerStatisticalAdapter.ViewHolder viewHolder = new ProfilePhotographerStatisticalAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
//        OrdersModel ordersModel= ordersModelArrayList.get(i);
//        viewHolder.tvName.setText(ordersModel.getUser());
//        viewHolder.tvDate.setText(ordersModel.getDate());
//        viewHolder.tvMoney.setText(ordersModel.getTotal()+"");

    }

    @Override
    public int getItemCount() {
        return ordersModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvDate;
        private TextView tvMoney;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tvName);
            tvDate=itemView.findViewById(R.id.tvDate);
            tvMoney=itemView.findViewById(R.id.tvMoney);
        }
    }
}
