package com.example.cachua.photographerapp.View.Fragment.Photographer.List;

import android.app.ActionBar;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Fragment.BaseFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailListPhotographerFragment extends BaseFragment {
    private LinearLayout llTimeHour;
    private CircleImageView imgAvatar;
    private TextView tvName;
    private TextView tvAddress;
    private TextView tvNote;
    private TextView tvTotal;
    private TextView tvStartDate;
    private TextView tvDateFinish;
    private TextView tvTimeStart;
    private TextView tvTimeFinish;
    private TextView tvDate;
    private Button btnOk;
    private Button btnCancel;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat sdff = new SimpleDateFormat("HH:mm");
    String location;
    String avatar;
    String id;
    String name;
    String note;
    String total;
    String start;
    String finish;
    @Override
    protected int getLayoutResource() {
        return R.layout.detail_list_photographer_fragment;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        imgAvatar=rootView.findViewById(R.id.imgAvatar);
        tvName=rootView.findViewById(R.id.tvName);
        tvAddress=rootView.findViewById(R.id.tvAddress);
        tvNote=rootView.findViewById(R.id.tvNote);
        tvTotal=rootView.findViewById(R.id.tvTotal);
        tvStartDate=rootView.findViewById(R.id.tvStartDate);
        tvDateFinish=rootView.findViewById(R.id.tvDateFinish);
        tvTimeFinish=rootView.findViewById(R.id.tvTimeFinish);
        tvTimeStart=rootView.findViewById(R.id.tvTimeStart);
        tvDate=rootView.findViewById(R.id.tvDate);
        llTimeHour=rootView.findViewById(R.id.llTimeHour);
        btnCancel=rootView.findViewById(R.id.btnCancel);
        btnOk=rootView.findViewById(R.id.btnOk);

        btnCancel.setOnClickListener(this);
        btnOk.setOnClickListener(this);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        id = bundle.getString("Id");
        location = bundle.getString("Location");
        avatar = bundle.getString("Avatar");
        name = bundle.getString("Name");
        note = bundle.getString("Note");
        total = bundle.getString("Total");
        start = bundle.getString("Start");
        finish = bundle.getString("Finish");
        long timee= Long.parseLong(finish)-Long.parseLong(start);
        Log.i("HienNguVl", timee+"initData: ");

        Calendar calendar= Calendar.getInstance();
        Calendar calendar1= Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(start));
        calendar1.setTimeInMillis(Long.parseLong(finish));
        timee=timee/60/60/1000;
        if(timee==0||timee>23&&timee%24==0){
            llTimeHour.setVisibility(View.GONE);
            tvDate.setText("Chụp theo ngày");
            tvStartDate.setText(sdf.format(calendar.getTime()));
            tvDateFinish.setText(sdf.format(calendar1.getTime()));
        }
        else{
            llTimeHour.setVisibility(View.VISIBLE);
            tvDate.setText("Chụp theo giờ");
            tvTimeStart.setText(sdff.format(calendar.getTime()));
            tvTimeFinish.setText(sdff.format(calendar1.getTime()));
            tvStartDate.setText(sdf.format(calendar.getTime()));
            tvDateFinish.setText(sdf.format(calendar1.getTime()));
        }


        Glide.with(mContext).load(avatar).skipMemoryCache(false).into(imgAvatar);
        tvName.setText(name);
        tvAddress.setText(location);
        tvNote.setText(note);
        tvTotal.setText(total);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnOk:{
                showDialogThank();
                break;
            }
            case R.id.btnCancel:{
                showDialogCancel();
                break;
            }
        }

    }
    public void showDialogCancel() {

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
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                updateOrder(id,"cancel");
                dialog.dismiss();
                Objects.requireNonNull(getActivity()).onBackPressed();
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
    public void showDialogThank() {

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
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                updateOrder(id,"confirmed");
                dialog.dismiss();
                Objects.requireNonNull(getActivity()).onBackPressed();
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
