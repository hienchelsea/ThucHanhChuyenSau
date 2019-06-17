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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Activity.MainUserActivity;
import com.example.cachua.photographerapp.View.Fragment.User.Notice.NoticeCouponsFragment;
import com.example.cachua.photographerapp.View.Fragment.User.Notice.NoticeOderFragment;
import com.example.cachua.photographerapp.View.Fragment.User.Notice.NoticeOptionFragment;
import com.example.cachua.photographerapp.View.Model.NoticeModel;
import com.example.cachua.photographerapp.View.Model.UseModel;
import com.example.cachua.photographerapp.View.config.Constants;
import com.example.cachua.photographerapp.View.config.SharedPreferencesUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {
    private ArrayList<NoticeModel> noticeModelArrayList;
    private Context mContext;
    private UseModel useModel;
    private String name;
    private String linkAvatar;

    public NoticeAdapter(ArrayList<NoticeModel> noticeModelArrayList, Context mContext) {
        this.noticeModelArrayList = noticeModelArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater= LayoutInflater.from(mContext);
        View view= layoutInflater.inflate(R.layout.layout_adapter_notice,viewGroup,false);
        NoticeAdapter.ViewHolder viewHolder = new NoticeAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final NoticeModel noticeModel= noticeModelArrayList.get(i);
        viewHolder.tvTittle.setText(noticeModel.getMessage());
        Picasso.with(mContext).load("https://firebasestorage.googleapis.com/v0/b/hienoc-3503c.appspot.com/o/image%3A49081?alt=media&token=be9fa927-78d0-4302-b380-c956f153ab0b").placeholder(R.drawable.icon_user).into(viewHolder.imgAvatar);
        viewHolder.llNotice.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "HienOc", Toast.LENGTH_SHORT).show();
                if(noticeModel.getCollection().equals("orders")==true){
                    NoticeOderFragment noticeOderFragment= new NoticeOderFragment();
                    ((MainUserActivity) Objects.requireNonNull(mContext)).nextFragment(noticeOderFragment,R.id.container,0,0,0,0);
                }
                else{
                    if(noticeModel.getCollection().equals("Options")==true){
                        NoticeOptionFragment noticeOptionFragment= new NoticeOptionFragment();
                        ((MainUserActivity) Objects.requireNonNull(mContext)).nextFragment(noticeOptionFragment,R.id.container,0,0,0,0);
                    }
                    else{
                        if(noticeModel.getCollection().equals("Coupons")==true){
                            NoticeCouponsFragment noticeCouponsFragment= new NoticeCouponsFragment();
                            ((MainUserActivity) Objects.requireNonNull(mContext)).nextFragment(noticeCouponsFragment,R.id.container,0,0,0,0);
                        }
                    }
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return noticeModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTittle;
        TextView tvTime;
        ImageView imgAvatar;
        LinearLayout llNotice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTittle= itemView.findViewById(R.id.tvTittle);
            tvTime= itemView.findViewById(R.id.tvTime);
            imgAvatar= itemView.findViewById(R.id.imgAvatar);
            llNotice= itemView.findViewById(R.id.llNotice);
        }
    }
    public void Update(ArrayList<NoticeModel> noticeModelArrayList, Context mContext) {
        this.noticeModelArrayList = noticeModelArrayList;
        this.mContext = mContext;
        notifyDataSetChanged();
    }
    private void LoadData() {
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        DocumentReference documentReference= db.collection("users").document(SharedPreferencesUtils.getString(mContext,Constants.ID_USER)+"");


               documentReference .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                       if (task.isSuccessful()) {
                           DocumentSnapshot document = task.getResult();
                           if (document.exists()) {
                              name= String.valueOf(document.getData().get("name"));
                              linkAvatar= String.valueOf(document.getData().get("avatar"));
                           } else {

                           }
                       } else {

                       }

                   }
               });


    }
}
