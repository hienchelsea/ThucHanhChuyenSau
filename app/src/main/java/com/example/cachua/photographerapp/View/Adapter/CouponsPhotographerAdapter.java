package com.example.cachua.photographerapp.View.Adapter;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Activity.MainPhotographerActivity;
import com.example.cachua.photographerapp.View.Fragment.Photographer.Coupons.UpdateCouponsPhotographerFragment;
import com.example.cachua.photographerapp.View.Fragment.Photographer.Options.UpdateOptionPhotographerFragment;
import com.example.cachua.photographerapp.View.Model.CouponsModel;
import com.example.cachua.photographerapp.View.Model.OptionsModel;
import com.example.cachua.photographerapp.View.config.Constants;
import com.example.cachua.photographerapp.View.config.SharedPreferencesUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class CouponsPhotographerAdapter extends RecyclerView.Adapter<CouponsPhotographerAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<CouponsModel> couponsModelArrayList;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public CouponsPhotographerAdapter(Context mContext, ArrayList<CouponsModel> couponsModelArrayList) {
        this.mContext = mContext;
        this.couponsModelArrayList = couponsModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.layout_adapter_coupons_photographer, viewGroup, false);
        CouponsPhotographerAdapter.ViewHolder viewHolder = new CouponsPhotographerAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final CouponsModel couponsModel = couponsModelArrayList.get(i);
        if (SharedPreferencesUtils.getString(mContext, Constants.CHECK_COUPONS).equals("User") == true) {
            viewHolder.imvMenu.setVisibility(View.GONE);
        }
        viewHolder.tvName.setText(couponsModel.getName());
        viewHolder.tvDescription.setText(couponsModel.getDescription());
        viewHolder.tvValue.setText(couponsModel.getValue() + "%");
        Timestamp timestamp= couponsModel.getStartAt();
        Timestamp timestamp1= couponsModel.getEndAt();
        long mm= timestamp.getTime();
        long mm1= timestamp1.getTime();
        Calendar calendar= Calendar.getInstance();
        Calendar calendar1= Calendar.getInstance();
        calendar.setTimeInMillis(mm);
        calendar1.setTimeInMillis(mm1);


        viewHolder.tvStartDate.setText(sdf.format(calendar.getTime())+"");
        viewHolder.tvEndDate.setText(sdf.format(calendar1.getTime())+"");

        viewHolder.imvMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu(viewHolder.imvMenu,couponsModel);
            }
        });


    }

    @Override
    public int getItemCount() {
        return couponsModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvValue;
        private TextView tvDescription;
        private TextView tvStartDate;
        private TextView tvEndDate;
        private ImageView imvMenu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvValue = itemView.findViewById(R.id.tvValue);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvStartDate = itemView.findViewById(R.id.tvStartDate);
            tvEndDate = itemView.findViewById(R.id.tvEndDate);
            imvMenu = itemView.findViewById(R.id.imvMenu);
        }
    }

    public void Update(Context mContext, ArrayList<CouponsModel> couponsModelArrayList) {
        this.mContext = mContext;
        this.couponsModelArrayList = couponsModelArrayList;
        notifyDataSetChanged();

    }
    public void showDialogDelete(final CouponsModel couponsModel){

        final Dialog dialog= new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dailog_delete_option);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Window window = dialog.getWindow();
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        Button btnOK= dialog.findViewById(R.id.btnOk);
        Button btnCancel= dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                deleteCoupons(couponsModel.getId()+"");
                dialog.dismiss();
                couponsModelArrayList.remove(couponsModel);
                notifyDataSetChanged();
            }
        });


        dialog.show();
    }
    @SuppressLint("RestrictedApi")
    public void menu(ImageView imageView, final CouponsModel couponsModel){
        MenuBuilder menuBuilder = new MenuBuilder(mContext);
        MenuInflater inflater = new MenuInflater(mContext);
        inflater.inflate(R.menu.menu_option_photographer, menuBuilder);
        MenuPopupHelper optionsMenu = new MenuPopupHelper(mContext, menuBuilder, imageView);
        optionsMenu.setForceShowIcon(false);
        optionsMenu.show();
        menuBuilder.setCallback(new MenuBuilder.Callback() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuDelete:
                        showDialogDelete(couponsModel);
                        return true;
                    case R.id.menuUpdate:
                        Bundle bundle= new Bundle();


                        bundle.putString("id",couponsModel.getId());
                        bundle.putString("code",couponsModel.getCode());
                        bundle.putString("description",couponsModel.getDescription());
                        bundle.putString("name",couponsModel.getName());
                        bundle.putString("userId",couponsModel.getId());
                        bundle.putFloat("value",couponsModel.getValue());

                        Timestamp timestamp= couponsModel.getStartAt();
                        Timestamp timestamp1= couponsModel.getEndAt();
                        long mm= timestamp.getTime();
                        long mm1= timestamp1.getTime();

                        bundle.putLong("startAt",mm);
                        bundle.putLong("updatedAt",mm1);



                        UpdateCouponsPhotographerFragment ordersPhotographerFragment= new UpdateCouponsPhotographerFragment();
                        ordersPhotographerFragment.setArguments(bundle);
                        ((MainPhotographerActivity) Objects.requireNonNull(mContext)).nextFragment(ordersPhotographerFragment, R.id.frameContainer, 0, 0, 0, 0);

                        return true;

                    default:
                        return false;
                }
            }

            @Override
            public void onMenuModeChange(MenuBuilder menu) {

            }
        });
    }
    public void deleteCoupons(String s){
        FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();
        DocumentReference documentReference= firebaseFirestore.collection("coupons").document(s+"");
        documentReference.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });

    }

//    public String date(String s) {
//        Calendar calendar = Calendar.getInstance();
//
//        String[] catChuoi = s.split(",");
//        String[] catChuoiTiep = catChuoi[0].split("=");
//        Timestamp timestamp = new Timestamp(Long.parseLong(catChuoiTiep[1]) * 1000);
//        calendar.setTimeInMillis(Long.parseLong(catChuoiTiep[1]) * 1000);
//        return sdf.format(calendar);
//    }
}
