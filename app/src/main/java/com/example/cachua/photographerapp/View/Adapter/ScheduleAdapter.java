package com.example.cachua.photographerapp.View.Adapter;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Activity.MainPhotographerActivity;
import com.example.cachua.photographerapp.View.Fragment.Photographer.Schedule.DetailSchedulePhotographerFragment;
import com.example.cachua.photographerapp.View.Model.OrdersModel;
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

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<OrdersModel> ordersModelArrayList;
    private ArrayList<String> stringArrayList= new ArrayList<>();
    private ArrayList<String> stringArrayAvatar= new ArrayList<>();

    public ScheduleAdapter(Context mContext, ArrayList<OrdersModel> ordersModelArrayList) {
        this.mContext = mContext;
        this.ordersModelArrayList = ordersModelArrayList;
    }

    @NonNull
    @Override
    public ScheduleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.layout_adapter_schedule, viewGroup, false);
        ScheduleAdapter.ViewHolder viewHolder = new ScheduleAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleAdapter.ViewHolder viewHolder, final int i) {
        final OrdersModel ordersModel = ordersModelArrayList.get(i);
        viewHolder.tvLocation.setText(ordersModel.getAddress());
        viewHolder.tvTotal.setText(ordersModel.getTotal() + "");
        ArrayList<UseModel> useModelArrayList = new ArrayList<>();
        LoadData(useModelArrayList, ordersModel.getUserId(),viewHolder.tvPhotographer,viewHolder.imgAvatar);

        viewHolder.btnBook.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                Bundle bundle= new Bundle();
                bundle.putString("Location",ordersModel.getAddress());
                bundle.putString("Name",stringArrayList.get(i));
                bundle.putString("Avatar",stringArrayAvatar.get(i));
                bundle.putString("Note",ordersModel.getNote());
                bundle.putString("Total",ordersModel.getTotal()+"");
                bundle.putString("Start",ordersModel.getStartAt().getTime()+"");
                bundle.putString("Finish",ordersModel.getEndAt().getTime()+"");


                DetailSchedulePhotographerFragment detailSchedulePhotographerFragment= new DetailSchedulePhotographerFragment();
                detailSchedulePhotographerFragment.setArguments(bundle);
                ((MainPhotographerActivity) Objects.requireNonNull(mContext)).nextFragment(detailSchedulePhotographerFragment,R.id.frameContainer,0,0,0,0);
            }
        });



    }

    @Override
    public int getItemCount() {
        return ordersModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imgAvatar;
        private TextView tvPhotographer;
        private TextView tvLocation;
        private TextView tvTotal;
        private Button btnBook;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvPhotographer = itemView.findViewById(R.id.tvPhotographer);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvTotal = itemView.findViewById(R.id.tvTotal);
            btnBook = itemView.findViewById(R.id.btnBook);
        }
    }

    private void LoadData(final ArrayList<UseModel> useModelArrayList, String s, final TextView textView, final CircleImageView circleImageView) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("users").document(s + "");
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    UseModel useModel = new UseModel(String.valueOf(documentSnapshot.getData().get("id")), String.valueOf(documentSnapshot.getData().get("bio")),
                            String.valueOf(documentSnapshot.getData().get("birth")), String.valueOf(documentSnapshot.getData().get("email")),
                            String.valueOf(documentSnapshot.getData().get("location")), String.valueOf(documentSnapshot.getData().get("name")),
                            String.valueOf(documentSnapshot.getData().get("phone")), Double.parseDouble(String.valueOf(documentSnapshot.getData().get("rating"))),
                            String.valueOf(documentSnapshot.getData().get("role")), String.valueOf(documentSnapshot.getData().get("avatar"))
                    );
                    useModelArrayList.add(useModel);
                    textView.setText(useModel.getName());
                    stringArrayList.add(useModel.getName());
                    stringArrayAvatar.add(useModel.getAvatar());
                    Glide.with(mContext).load(useModel.getAvatar()).skipMemoryCache(false).into(circleImageView);

                }

            }
        });

    }
}
