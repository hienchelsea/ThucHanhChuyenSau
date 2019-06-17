package com.example.cachua.photographerapp.View.Adapter;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.RecyclerView;
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
import com.example.cachua.photographerapp.View.Activity.LoginActivity;
import com.example.cachua.photographerapp.View.Activity.MainPhotographerActivity;
import com.example.cachua.photographerapp.View.Activity.MainUserActivity;
import com.example.cachua.photographerapp.View.Fragment.Photographer.Options.UpdateOptionPhotographerFragment;
import com.example.cachua.photographerapp.View.Fragment.User.Home.OrdersPhotographerFragment;
import com.example.cachua.photographerapp.View.Model.OptionsModel;
import com.example.cachua.photographerapp.View.config.Constants;
import com.example.cachua.photographerapp.View.config.SharedPreferencesUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

public class OptionPhotographerAdapter extends RecyclerView.Adapter<OptionPhotographerAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<OptionsModel> optionsModelArrayList;
    private String idPhotographer;
    private String avatarLink;
    private String name;
    private String rating;

    public OptionPhotographerAdapter(Context mContext, ArrayList<OptionsModel> optionsModelArrayList,String idPhotographer,String avatarLink,String name,String rating) {
        this.mContext = mContext;
        this.optionsModelArrayList = optionsModelArrayList;
        this.idPhotographer = idPhotographer;
        this.avatarLink = avatarLink;
        this.name = name;
        this.rating = rating;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater= LayoutInflater.from(mContext);
        View view= layoutInflater.inflate(R.layout.layout_adapter_option_photographer,viewGroup,false);
        OptionPhotographerAdapter.ViewHolder viewHolder = new OptionPhotographerAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final OptionsModel optionsModel= optionsModelArrayList.get(i);
        if(SharedPreferencesUtils.getString(mContext, Constants.CHECK_OPTION).equals("User")==true){
            viewHolder.imvMenu.setVisibility(View.GONE);
            viewHolder.btnOder.setVisibility(View.VISIBLE);

        }
        viewHolder.tvName.setText(optionsModel.getName());
        viewHolder.tvType.setText(optionsModel.getType());
        viewHolder.tvDescription.setText(optionsModel.getDescription());
        viewHolder.tvShots.setText(optionsModel.getShots()+"");
        viewHolder.tvPrints.setText(optionsModel.getPrints()+"");
        viewHolder.tvPrice.setText(optionsModel.getPrice_per_hour()+"/gio"+" "+optionsModel.getPrice_per_day()+"/ngay");
        viewHolder.btnOder.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                Bundle bundle= new Bundle();
                bundle.putString("description",optionsModel.getDescription());
                bundle.putString("name",optionsModel.getName());
                bundle.putFloat("price_per_day",optionsModel.getPrice_per_day());
                bundle.putFloat("price_per_hour",optionsModel.getPrice_per_hour());
                bundle.putInt("prints",optionsModel.getPrints());
                bundle.putInt("shots",optionsModel.getShots());
                bundle.putString("type",optionsModel.getType());
                bundle.putString("idPhotographer",optionsModel.getUserId());
                bundle.putString("avatarLink",avatarLink);
                bundle.putString("name",name);
                bundle.putString("rating", rating);
                OrdersPhotographerFragment ordersPhotographerFragment= new OrdersPhotographerFragment();
                ordersPhotographerFragment.setArguments(bundle);
                ((MainUserActivity) Objects.requireNonNull(mContext)).nextFragment(ordersPhotographerFragment, R.id.frameContainer, 0, 0, 0, 0);
            }
        });
        viewHolder.imvMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu(viewHolder.imvMenu,optionsModel);
            }
        });

    }

    @Override
    public int getItemCount() {
        return optionsModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvType;
        private TextView tvShots;
        private TextView tvPrints;
        private TextView tvDescription;
        private TextView tvPrice;
        private ImageView imvMenu;
        private Button btnOder;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName= itemView.findViewById(R.id.tvName);
            tvType= itemView.findViewById(R.id.tvType);
            tvShots= itemView.findViewById(R.id.tvShots);
            tvPrints= itemView.findViewById(R.id.tvPrints);
            tvDescription= itemView.findViewById(R.id.tvDescription);
            tvPrice= itemView.findViewById(R.id.tvPrice);
            imvMenu= itemView.findViewById(R.id.imvMenu);
            btnOder= itemView.findViewById(R.id.btnOder);
        }
    }
    public void Update(ArrayList<OptionsModel> optionsModelArrayList,Context mContext){
        this.mContext = mContext;
        this.optionsModelArrayList = optionsModelArrayList;
        notifyDataSetChanged();
    }
    @SuppressLint("RestrictedApi")
    public void menu(ImageView imageView, final OptionsModel optionsModel){
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
                        showDialogDelete(optionsModel);
                        return true;
                    case R.id.menuUpdate:
                        Bundle bundle= new Bundle();
                        bundle.putString("id",optionsModel.getId());
                        bundle.putString("description",optionsModel.getDescription());
                        bundle.putString("name",optionsModel.getName());
                        bundle.putFloat("price_per_day",optionsModel.getPrice_per_day());
                        bundle.putFloat("price_per_hour",optionsModel.getPrice_per_hour());
                        bundle.putInt("prints",optionsModel.getPrints());
                        bundle.putInt("shots",optionsModel.getShots());
                        bundle.putString("type",optionsModel.getType());


                        UpdateOptionPhotographerFragment ordersPhotographerFragment= new UpdateOptionPhotographerFragment();
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
    public void showDialogDelete(final OptionsModel optionsModel){

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
                deleteOption(optionsModel.getId()+"");
                dialog.dismiss();
                optionsModelArrayList.remove(optionsModel);
                notifyDataSetChanged();
            }
        });


        dialog.show();
    }
    public void deleteOption(String s){
        FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();
        DocumentReference documentReference= firebaseFirestore.collection("options").document(s+"");
        documentReference.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });

    }
}
