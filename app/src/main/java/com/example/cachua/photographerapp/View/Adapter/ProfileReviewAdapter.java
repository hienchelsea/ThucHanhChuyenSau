package com.example.cachua.photographerapp.View.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Model.ReviewsModel;
import com.example.cachua.photographerapp.View.config.Constants;
import com.example.cachua.photographerapp.View.config.SharedPreferencesUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileReviewAdapter extends RecyclerView.Adapter<ProfileReviewAdapter.ViewHolder>{
    private Context mContext;
    private ArrayList<ReviewsModel> reviewsModelArrayList;

    public ProfileReviewAdapter(Context mContext, ArrayList<ReviewsModel> reviewsModelArrayList) {
        this.mContext = mContext;
        this.reviewsModelArrayList = reviewsModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater= LayoutInflater.from(mContext);
        View view= layoutInflater.inflate(R.layout.layout_adapter_review,viewGroup,false);
        ProfileReviewAdapter.ViewHolder viewHolder = new ProfileReviewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        ReviewsModel reviewsModel= reviewsModelArrayList.get(i);
        viewHolder.tvRate.setText(reviewsModel.getRating()+"");
        viewHolder.tvReview.setText(reviewsModel.getReview()+"");

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .whereEqualTo("id",reviewsModel.getPhotographerId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot:task.getResult()){
                                viewHolder.tvName.setText(SharedPreferencesUtils.getString(mContext, Constants.NAME_USER)+" da danh gia "+ documentSnapshot.getData().get("name"));
                                Glide
                                        .with(mContext)
                                        .load(String.valueOf(documentSnapshot.getData().get("avatar")))
                                        .skipMemoryCache(true)
                                        .into(viewHolder.cimgAvatar);
                            }
                        }

                    }
                });



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
