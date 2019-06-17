package com.example.cachua.photographerapp.View.Fragment.Photographer.Schedule;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Fragment.BaseFragment;
import com.example.cachua.photographerapp.View.Model.OrdersModel;
import com.example.cachua.photographerapp.View.config.Constants;
import com.example.cachua.photographerapp.View.config.SharedPreferencesUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailSchedulePhotographerFragment extends BaseFragment {
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
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat sdff = new SimpleDateFormat("HH:mm");
    String location;
    String avatar;
    String name;
    String note;
    String total;
    String start;
    String finish;

    @Override
    protected int getLayoutResource() {
        return R.layout.detail_schedule_photographer_fragment;
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


    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
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

    }
}
