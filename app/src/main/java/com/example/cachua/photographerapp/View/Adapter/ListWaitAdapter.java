package com.example.cachua.photographerapp.View.Adapter;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Activity.MainPhotographerActivity;
import com.example.cachua.photographerapp.View.Fragment.Photographer.List.DetailListPhotographerFragment;
import com.example.cachua.photographerapp.View.Fragment.Photographer.Schedule.DetailSchedulePhotographerFragment;
import com.example.cachua.photographerapp.View.Model.OrdersModel;
import com.example.cachua.photographerapp.View.Model.UseModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListWaitAdapter extends RecyclerView.Adapter<ListWaitAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<OrdersModel> ordersModelArrayList;
    private SimpleDateFormat sdf= new SimpleDateFormat("dd-MM-yyyy");
    private ArrayList<String> stringArrayList= new ArrayList<>();
    private ArrayList<String> stringArrayAvatar= new ArrayList<>();


    public ListWaitAdapter(Context mContext, ArrayList<OrdersModel> ordersModelArrayList) {
        this.mContext = mContext;
        this.ordersModelArrayList = ordersModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.layout_adapter_list_wating, viewGroup, false);
        ListWaitAdapter.ViewHolder viewHolder = new ListWaitAdapter.ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final OrdersModel ordersModel= ordersModelArrayList.get(i);
        viewHolder.tvTotal.setText(ordersModel.getTotal()+"");
        viewHolder.tvLocation.setText(ordersModel.getAddress());
        long timeDate= ordersModel.getStartAt().getTime();
        Calendar calendar= Calendar.getInstance();
        calendar.setTimeInMillis(timeDate);
        viewHolder.tvDate.setText(sdf.format(calendar.getTime()));
        ArrayList<UseModel> useModelArrayList = new ArrayList<>();
        LoadData(useModelArrayList, ordersModel.getUserId(),viewHolder.tvPhotographer,viewHolder.imgAvatar);
        viewHolder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogCancel(ordersModel.getId());
            }
        });
        viewHolder.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ordersModelArrayList.remove(ordersModel);
                notifyDataSetChanged();
                showDialogThank(ordersModel.getId());
                updateOrder(ordersModel.getId()+"","confirmed");

            }
        });
        viewHolder.llClick.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                Bundle bundle= new Bundle();
                bundle.putString("Location",ordersModel.getAddress());
                bundle.putString("Id",ordersModel.getId());
                bundle.putString("Name",stringArrayList.get(i));
                bundle.putString("Avatar",stringArrayAvatar.get(i));
                bundle.putString("Note",ordersModel.getNote());
                bundle.putString("Total",ordersModel.getTotal()+"");
                bundle.putString("Start",ordersModel.getStartAt().getTime()+"");
                bundle.putString("Finish",ordersModel.getEndAt().getTime()+"");

                DetailListPhotographerFragment detailSchedulePhotographerFragment= new DetailListPhotographerFragment();
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
        private TextView tvTotal;
        private TextView tvPhotographer;
        private TextView tvLocation;
        private TextView tvDate;
        private CircleImageView imgAvatar;
        private LinearLayout llClick;
        private Button btnCancel;
        private Button btnOk;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTotal= itemView.findViewById(R.id.tvTotal);
            tvPhotographer= itemView.findViewById(R.id.tvPhotographer);
            tvLocation= itemView.findViewById(R.id.tvLocation);
            tvDate= itemView.findViewById(R.id.tvDate);
            imgAvatar= itemView.findViewById(R.id.imgAvatar);
            llClick= itemView.findViewById(R.id.llClick);
            btnCancel= itemView.findViewById(R.id.btnCancel);
            btnOk= itemView.findViewById(R.id.btnOk);
        }
    }
    public void Update(Context mContext, ArrayList<OrdersModel> ordersModelArrayList) {
        this.mContext = mContext;
        this.ordersModelArrayList = ordersModelArrayList;
        notifyDataSetChanged();
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
    public void showDialogCancel(final String s) {

        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dailog_list_photo);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Window window = dialog.getWindow();
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        Button btnOk= dialog.findViewById(R.id.btnOk);
        Button btnCancel= dialog.findViewById(R.id.btnCancel);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                updateOrder(s+"","cancelled");
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    public void showDialogThank(String id) {

        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dailog_list_thank_photo);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Window window = dialog.getWindow();
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        Button btnOk= dialog.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        dialog.show();
    }
    public void updateOrder(String s,String m){
        FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
        DocumentReference documentReference= firebaseFirestore.collection("order").document(s+"");
        documentReference.update("status",m+"")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }
}
