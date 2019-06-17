package com.example.cachua.photographerapp.View.Fragment.User.Home;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Activity.MainUserActivity;
import com.example.cachua.photographerapp.View.Adapter.ReviewPhotographerAdapter;
import com.example.cachua.photographerapp.View.Fragment.BaseFragment;
import com.example.cachua.photographerapp.View.Model.FavoritesModel;
import com.example.cachua.photographerapp.View.Model.ReviewsModel;
import com.example.cachua.photographerapp.View.Model.UseModel;
import com.example.cachua.photographerapp.View.config.Constants;
import com.example.cachua.photographerapp.View.config.SharedPreferencesUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class PresonalpageFragment extends BaseFragment {
    private ImageView imgBack;
    private ImageView imgProfile;
    private ImageView imvOption;
    private ImageView imvCoupons;
    private ImageView imvAlbum;
    private ImageView imvLove;
    private CircleImageView cimvAvatar;
    private TextView tvName;
    private TextView tvRating;
    private TextView tvEmail;
    private TextView tvPhone;
    //private ImageView imgAvatar;

    private ImageView imvOder;
    private RecyclerView rcReview;
    private ArrayList<ReviewsModel> reviewsModelArrayList;
    private ReviewPhotographerAdapter reviewPhotographerAdapter;
    private UseModel useModel;
    private String nameUser;
    ArrayList<String> stringArrayList=new ArrayList<>();
    @Override
    protected int getLayoutResource() {
        return (R.layout.fragment_profile_home_photographet);
    }


    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        imgBack=rootView.findViewById(R.id.imgBack);
        imvOption=rootView.findViewById(R.id.imvOption);
        imvCoupons=rootView.findViewById(R.id.imvCoupons);
        imvAlbum=rootView.findViewById(R.id.imvAlbum);
        cimvAvatar=rootView.findViewById(R.id.cimvAvatar);
        tvName=rootView.findViewById(R.id.tvName);
        tvRating=rootView.findViewById(R.id.tvRating);
        tvEmail=rootView.findViewById(R.id.tvEmail);
        tvPhone=rootView.findViewById(R.id.tvPhone);
        imvLove=rootView.findViewById(R.id.imvLove);

        // imgAvatar=rootView.findViewById(R.id.imgAvatar);

        //  imvOder=rootView.findViewById(R.id.imvOder);
        rcReview=rootView.findViewById(R.id.rcReview);


        imgBack.setOnClickListener(this);
        imvOption.setOnClickListener(this);
        imvCoupons.setOnClickListener(this);
        imvAlbum.setOnClickListener(this);
        imvLove.setOnClickListener(this);
//        imvOder.setOnClickListener(this);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        Bundle bundle= getArguments();
        stringArrayList= bundle.getStringArrayList("Photographer");
        useModel= new UseModel(stringArrayList.get(0),stringArrayList.get(1),stringArrayList.get(2),
                stringArrayList.get(3),stringArrayList.get(4),stringArrayList.get(5),stringArrayList.get(6),
                Double.parseDouble(stringArrayList.get(7)),stringArrayList.get(8),stringArrayList.get(9));
        tvName.setText(useModel.getName());
        tvEmail.setText(useModel.getEmail());
        tvPhone.setText(useModel.getPhone());
        tvRating.setText(useModel.getRating()+"");
        if(stringArrayList.get(10).equals("1")){
            imvLove.setImageResource(R.drawable.icon_love_red);
        }
        else{
            imvLove.setImageResource(R.drawable.icon_love_black);
        }
        Glide
                .with(mContext)
                .load(useModel.getAvatar())
                .skipMemoryCache(true)
                .into(cimvAvatar);
    }



    @Override
    public void onResume() {
        super.onResume();
        reviewsModelArrayList= new ArrayList<>();
        reviewPhotographerAdapter= new ReviewPhotographerAdapter(mContext,reviewsModelArrayList);
        rcReview.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        rcReview.setAdapter(reviewPhotographerAdapter);
        LoadData(reviewsModelArrayList);

    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imvLove:{
                if(stringArrayList.get(10).equals("1")==true){
                    imvLove.setImageResource(R.drawable.icon_love_black);
                    Toast.makeText(mContext, "Bo yeu thich", Toast.LENGTH_SHORT).show();
                    stringArrayList.set(10,"0");
                    DeleteLove(stringArrayList.get(11));

                }
                else{
                    if(stringArrayList.get(10).equals("0")==true){
                        imvLove.setImageResource(R.drawable.icon_love_red);
                        Toast.makeText(mContext, "Da them vao danh sach yeu thich", Toast.LENGTH_SHORT).show();
                        FavoritesModel favoritesModel= new FavoritesModel("",useModel.getId(), SharedPreferencesUtils.getString(mContext, Constants.ID_USER));
                        stringArrayList.set(10,"1");
                        AddLove(favoritesModel);
                    }
                }
//                if(imvLove.getBackground().equals(R.drawable.icon_love_black)==true){
//                    imvLove.setImageResource(R.drawable.icon_love_red);
//                }
//                else{
//                    imvLove.setImageResource(R.drawable.icon_love_black);
//                }
                break;
            }
            case R.id.imgBack:{
                Objects.requireNonNull(getActivity()).onBackPressed();
                break;
            }

            case R.id.imvAlbum:{
                Bundle bundle= new Bundle();
                bundle.putString("idPhotographer",useModel.getId());
                AlbumPhotographerFragment albumPhotographerFragment = new AlbumPhotographerFragment();
                albumPhotographerFragment.setArguments(bundle);
                ((MainUserActivity) Objects.requireNonNull(mContext)).nextFragment(albumPhotographerFragment, R.id.frameContainer, 0, 0, 0, 0);
                break;
            }
            case R.id.imvOption:{
                Bundle bundle= new Bundle();
                bundle.putString("idPhotographer",useModel.getId());
                bundle.putString("avatarLink",useModel.getAvatar());
                bundle.putString("name",useModel.getName());
                bundle.putString("rating", String.valueOf(useModel.getRating()));
                OptionUserPhotographerFragment optionUserPhotographerFragment = new OptionUserPhotographerFragment();
                optionUserPhotographerFragment.setArguments(bundle);
                ((MainUserActivity) Objects.requireNonNull(mContext)).nextFragment(optionUserPhotographerFragment, R.id.frameContainer, 0, 0, 0, 0);
                break;
            }
            case R.id.imvCoupons:{
                Bundle bundle= new Bundle();
                bundle.putString("idPhotographer",useModel.getId());
                CouponsUserPhotographerFragment couponsUserPhotographerFragment = new CouponsUserPhotographerFragment();
                couponsUserPhotographerFragment.setArguments(bundle);
                ((MainUserActivity) Objects.requireNonNull(mContext)).nextFragment(couponsUserPhotographerFragment, R.id.frameContainer, 0, 0, 0, 0);
                break;
            }
//            case R.id.imvOder:{
//                OrdersPhotographerFragment ordersPhotographerFragment= new OrdersPhotographerFragment();
//                ((MainUserActivity) Objects.requireNonNull(mContext)).nextFragment(ordersPhotographerFragment, R.id.frameContainer, 0, 0, 0, 0);
//                break;
//            }

        }

    }
    private void LoadData(final ArrayList<ReviewsModel> reviewsModels) {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("review")
                .whereEqualTo("photographerId", useModel.getId()+"")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ReviewsModel reviewsModel= new ReviewsModel(String.valueOf(document.getId()),String.valueOf(document.getData().get("photographerId")),Double.parseDouble(String.valueOf(document.getData().get("rating"))),
                                        String.valueOf(document.getData().get("review")), String.valueOf(document.getData().get("userId")));
                                reviewsModels.add(reviewsModel);
                                reviewPhotographerAdapter.Update(mContext,reviewsModels);
                            }
                            SharedPreferencesUtils.setInt(mContext, Constants.CHECK_DATA,1);
                        }
                        else{
                            Toast.makeText(mContext, "Loi", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void AddLove(final FavoritesModel favoritesModel) {
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        db.collection("favorites")
                .add(favoritesModel)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        stringArrayList.set(11,documentReference.getId()+"");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("HienNgu", "Error adding document", e);
                    }
                });

    }
    private void DeleteLove(String s){
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        Log.i("HienOcc", s+"");
        db.collection("favorites").document(s+"")
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }
}

