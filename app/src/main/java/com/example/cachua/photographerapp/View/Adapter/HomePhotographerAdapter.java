package com.example.cachua.photographerapp.View.Adapter;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.example.cachua.photographerapp.View.Fragment.User.Home.PersonalPagePhotographerFragment;
import com.example.cachua.photographerapp.View.Model.FavoritesModel;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomePhotographerAdapter extends RecyclerView.Adapter<HomePhotographerAdapter.ViewHolder> implements  View.OnClickListener {
    private ArrayList<UseModel>useModelArrayList;
    private ArrayList<FavoritesModel>favoritesModelArrayList;
    private ArrayList<FavoritesModel>favoritesModelArrayListSelect= new ArrayList<>();

    private ArrayList<String> stringArrayListKt= new ArrayList<>();
    private Context mContext;
    FavoritesModel favoritesModelDelete;

    int position;
    int ktra;

    public HomePhotographerAdapter(ArrayList<UseModel> useModelArrayList, Context mContext,ArrayList<FavoritesModel>favoritesModelArrayList) {
        this.useModelArrayList = useModelArrayList;
        this.favoritesModelArrayList = favoritesModelArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater= LayoutInflater.from(mContext);
        View view= layoutInflater.inflate(R.layout.layout_adapter_home_highlight,viewGroup,false);
        HomePhotographerAdapter.ViewHolder viewHolder = new HomePhotographerAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        position=i;
        final UseModel useModel= useModelArrayList.get(i);
        Log.i("HienNguuu", favoritesModelArrayList.size()+"");
        for(int j=0;j<favoritesModelArrayList.size();j++){
            if(favoritesModelArrayList.get(j).getPhotographerId().equals(useModel.getId())==true){
                ktra=1;
                favoritesModelDelete=favoritesModelArrayList.get(j);
                Log.i("HienOcc", favoritesModelDelete.getId()+"onBindViewHolder: ");
            }
        }
        if(ktra==1){
            favoritesModelArrayListSelect.add(favoritesModelDelete);
            viewHolder.imvLove.setImageResource(R.drawable.icon_love_red);
        }
        else{
            if(ktra==0){
                favoritesModelArrayListSelect.add(new FavoritesModel("","",""));
                viewHolder.imvLove.setImageResource(R.drawable.icon_love_black);
            }
            else{

            }

        }
        stringArrayListKt.add(ktra+"");
        ktra=0;
        viewHolder.tvRating.setText(" "+useModel.getRating()+"");
        viewHolder.tvName.setText(useModel.getName());
        Glide
                .with( mContext )
                .load( useModel.getAvatar() )
                .skipMemoryCache( true )
                .into( viewHolder.imgAvatar);

        viewHolder.llHomeAdapter.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                ArrayList<String> stringArrayList = new ArrayList<>();
                stringArrayList.add(useModel.getId());
                stringArrayList.add(useModel.getBio());
                stringArrayList.add(useModel.getBirth());
                stringArrayList.add(useModel.getEmail());
                stringArrayList.add(useModel.getLocation());
                stringArrayList.add(useModel.getName());
                stringArrayList.add(useModel.getPhone());
                stringArrayList.add(String.valueOf(useModel.getRating()));
                stringArrayList.add(useModel.getRole());
                stringArrayList.add(useModel.getAvatar());
                stringArrayList.add(stringArrayListKt.get(i));
                stringArrayList.add(favoritesModelArrayListSelect.get(i).getId());
                Bundle bundle= new Bundle();
                bundle.putStringArrayList("Photographer",stringArrayList);
                Toast.makeText(mContext, i+"", Toast.LENGTH_SHORT).show();

                PersonalPagePhotographerFragment profileHomePhotographerFragment= new PersonalPagePhotographerFragment();
                profileHomePhotographerFragment.setArguments(bundle);
                ((MainUserActivity) Objects.requireNonNull(mContext)).nextFragment(profileHomePhotographerFragment, R.id.frameContainer, 0, 0, 0, 0);
            }
        });
        viewHolder.imvLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int k=i;
                Log.i("kkkkk", k+"onClick: ");
                if(stringArrayListKt.get(i).equals("1")==true){
                    viewHolder.imvLove.setImageResource(R.drawable.icon_love_black);
                    Toast.makeText(mContext, "Bo yeu thich", Toast.LENGTH_SHORT).show();
                    stringArrayListKt.set(i,"0");
                    DeleteLove(k);

                }
                else{
                    if(stringArrayListKt.get(i).equals("0")==true){
                        viewHolder.imvLove.setImageResource(R.drawable.icon_love_red);
                        Toast.makeText(mContext, "Da them vao danh sach yeu thich", Toast.LENGTH_SHORT).show();
                        stringArrayListKt.set(i,"1");
                        FavoritesModel favoritesModel= new FavoritesModel("",useModel.getId(),SharedPreferencesUtils.getString(mContext,Constants.ID_USER));
                        AddLove(favoritesModel,k);
                    }
                }

            }
        });

    }

    private void AddLove(final FavoritesModel favoritesModel, final int i) {
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        db.collection("favorites")
                .add(favoritesModel)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("HienNgu", "DocumentSnapshot added with ID: " + documentReference.getId());
                        favoritesModel.setId(documentReference.getId());
                        favoritesModelArrayListSelect.set(i,favoritesModel);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("HienNgu", "Error adding document", e);
                    }
                });

    }
    private void DeleteLove(final int i){
        final FavoritesModel favoritesModel1= favoritesModelArrayListSelect.get(i);
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        Log.i("HienOcc", favoritesModel1.getId()+"DeleteLove: ");
        db.collection("favorites").document(favoritesModel1.getId()+"")
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        favoritesModelArrayListSelect.remove(i);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }

    @Override
    public int getItemCount() {
        return useModelArrayList.size();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {


    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar;
        private LinearLayout llHomeAdapter;
        private TextView tvName;
        private TextView tvRating;
        private ImageView imvLove;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tvName);
            imgAvatar=itemView.findViewById(R.id.imgAvatar);
            tvRating=itemView.findViewById(R.id.tvRating);
            imvLove=itemView.findViewById(R.id.imvLove);
            llHomeAdapter=itemView.findViewById(R.id.llHomeAdapter);
        }
    }
    public void Update(ArrayList<UseModel> useModelArrayList, Context mContext){
        this.useModelArrayList=useModelArrayList;
        this.mContext=mContext;
        notifyDataSetChanged();

    }


}
