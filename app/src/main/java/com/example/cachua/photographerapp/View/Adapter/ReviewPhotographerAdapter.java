package com.example.cachua.photographerapp.View.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Model.ReviewsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewPhotographerAdapter extends RecyclerView.Adapter<ReviewPhotographerAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<ReviewsModel> reviewsModelArrayList;

    public ReviewPhotographerAdapter(Context mContext, ArrayList<ReviewsModel> reviewsModelArrayList) {
        this.mContext = mContext;
        this.reviewsModelArrayList = reviewsModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater= LayoutInflater.from(mContext);
        View view= layoutInflater.inflate(R.layout.layout_adapter_review_photographer,viewGroup,false);
        ReviewPhotographerAdapter.ViewHolder viewHolder = new ReviewPhotographerAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        ReviewsModel reviewsModel= reviewsModelArrayList.get(i);
        Log.i("hieeeeeeeeee", reviewsModel.getUserId()+"");
        viewHolder.tvRate.setText(reviewsModel.getRating()+"");
        viewHolder.tvReview.setText(reviewsModel.getReview());


        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .whereEqualTo("id",reviewsModel.getUserId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot:task.getResult()){
                                viewHolder.tvName.setText(String.valueOf(documentSnapshot.getData().get("name")));
                                Glide
                                        .with(mContext)
                                        .load(String.valueOf(documentSnapshot.getData().get("avatar")))
                                        .skipMemoryCache(true)
                                        .into(viewHolder.cimgAvatar);
                            }
                        }

                    }
                });

      //  Picasso.with(mContext).load("https://firebasestorage.googleapis.com/v0/b/hienoc-3503c.appspot.com/o/image%3A49081?alt=media&token=be9fa927-78d0-4302-b380-c956f153ab0b").placeholder(R.drawable.icon_user).into(viewHolder.imgAvatar);

    }

    @Override
    public int getItemCount() {
        return reviewsModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvRate;
        private TextView tvReview;
        private CircleImageView cimgAvatar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName= itemView.findViewById(R.id.tvName);
            tvRate= itemView.findViewById(R.id.tvRate);
            tvReview= itemView.findViewById(R.id.tvReview);
            cimgAvatar= itemView.findViewById(R.id.cimgAvatar);

        }
    }
    public void Update(Context context,ArrayList<ReviewsModel> reviewsModels){
        reviewsModelArrayList= reviewsModels;
        mContext=context;
        notifyDataSetChanged();
    }
}
