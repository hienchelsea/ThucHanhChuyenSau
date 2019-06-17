package com.example.cachua.photographerapp.View.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Activity.MainUserActivity;
import com.example.cachua.photographerapp.View.Fragment.User.Home.OrdersPhotographerFragment;
import com.example.cachua.photographerapp.View.Fragment.User.Home.PersonalPagePhotographerFragment;
import com.example.cachua.photographerapp.View.Model.UseModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class FavoritePhotographerAdapter extends RecyclerView.Adapter<FavoritePhotographerAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<UseModel> useModelArrayList;

    public FavoritePhotographerAdapter(Context mContext, ArrayList<UseModel> useModelArrayList) {
        this.mContext = mContext;
        this.useModelArrayList = useModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater= LayoutInflater.from(mContext);
        View view= layoutInflater.inflate(R.layout.layout_adapter_photographer,viewGroup,false);
        FavoritePhotographerAdapter.ViewHolder viewHolder = new FavoritePhotographerAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        UseModel useModel= useModelArrayList.get(i);


        viewHolder.tvName.setText(useModel.getName());
        viewHolder.tvRating.setText(useModel.getRating()+"");
        // Picasso.with(mContext).load(useModel.getAvatar()).placeholder(R.drawable.icon_user).into(viewHolder.imgAvatar);
        Glide
                .with( mContext )
                .load( useModel.getAvatar() )
                .skipMemoryCache( true )
                .into( viewHolder.imgAvatar);

  //      Picasso.with(mContext).load("https://firebasestorage.googleapis.com/v0/b/hienoc-3503c.appspot.com/o/image%3A49081?alt=media&token=be9fa927-78d0-4302-b380-c956f153ab0b").placeholder(R.drawable.icon_user).into(viewHolder.imgAvatar);
//        viewHolder.btnOrder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                OrdersPhotographerFragment ordersPhotographerFragment= new OrdersPhotographerFragment();
//                ((MainUserActivity) Objects.requireNonNull(mContext)).nextFragment(ordersPhotographerFragment, R.id.frameContainer, 0, 0, 0, 0);
//            }
//        });
//        viewHolder.tvName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PersonalPagePhotographerFragment profileHomePhotographerFragment= new PersonalPagePhotographerFragment();
//                ((MainUserActivity) Objects.requireNonNull(mContext)).nextFragment(profileHomePhotographerFragment, R.id.frameContainer, 0, 0, 0, 0);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return useModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar;
        private TextView tvName;
        private TextView tvRating;
        private Button btnOrder;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar= itemView.findViewById(R.id.imgAvatar);
            btnOrder= itemView.findViewById(R.id.btnOrder);
            tvName= itemView.findViewById(R.id.tvName);
            tvRating= itemView.findViewById(R.id.tvRating);
        }
    }
    public void Update(Context mContext, ArrayList<UseModel> useModelArrayList) {
        this.mContext = mContext;
        this.useModelArrayList = useModelArrayList;
        notifyDataSetChanged();
    }
}
