package com.example.cachua.photographerapp.View.Fragment.User.UserToPhoto;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Activity.MainUserActivity;
import com.example.cachua.photographerapp.View.Adapter.ImvUserToPhotographerAdapter;
import com.example.cachua.photographerapp.View.Fragment.BaseFragment;
import com.example.cachua.photographerapp.View.Model.AlbumModel;
import com.example.cachua.photographerapp.View.Model.ImagesModel;
import com.example.cachua.photographerapp.View.config.Constants;
import com.example.cachua.photographerapp.View.config.SharedPreferencesUtils;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class ProfileUserToPhotographerFragment extends BaseFragment {

    public static ProfileUserToPhotographerFragment sInstance;
    public static ProfileUserToPhotographerFragment newInstance() {
        if (sInstance == null) {
            sInstance = new ProfileUserToPhotographerFragment();
        }
        return sInstance;
    }


    private Button btnNextStep;
    private ImageView imvAddImv;
    private EditText edtIntroduce;
    private EditText edtNameAlbum;
    private EditText edtTag;
    private RecyclerView rcImv;
    private CircleImageView cimgAvatar;
    private ArrayList<String> stringArrayList;
    private ImvUserToPhotographerAdapter imvUserToPhotographerAdapter;
    String uriImg;
    int ktraAvatar=0;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_profile_user_to_grapher;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        btnNextStep= rootView.findViewById(R.id.btnNextStep);
        cimgAvatar= rootView.findViewById(R.id.cimgAvatar);
        imvAddImv= rootView.findViewById(R.id.imvAddImv);
        edtIntroduce= rootView.findViewById(R.id.edtIntroduce);
        edtNameAlbum= rootView.findViewById(R.id.edtNameAlbum);
        edtTag= rootView.findViewById(R.id.edtTag);
        rcImv= rootView.findViewById(R.id.rcImv);


        btnNextStep.setOnClickListener(this);
        cimgAvatar.setOnClickListener(this);
        imvAddImv.setOnClickListener(this);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        stringArrayList= new ArrayList<>();
        imvUserToPhotographerAdapter= new ImvUserToPhotographerAdapter(stringArrayList,mContext);
        rcImv.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        rcImv.setAdapter(imvUserToPhotographerAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnNextStep:{
                if(ktraAvatar==1&&stringArrayList.size()>0){
                    Bundle bundle= new Bundle();
                    bundle.putString("linkAvatar",uriImg);
                    bundle.putString("introduce",edtIntroduce.getText().toString());
                    bundle.putString("nameAlbum",edtNameAlbum.getText().toString());
                    bundle.putString("tag",edtTag.getText().toString());
                    bundle.putStringArrayList("imv",stringArrayList);
                    ProfileUserToPhotographerFragment2 profileUserToPhotographerFragment= new ProfileUserToPhotographerFragment2();
                    profileUserToPhotographerFragment.setArguments(bundle);
                    ((MainUserActivity) Objects.requireNonNull(mContext)).nextFragment(profileUserToPhotographerFragment,R.id.container,0,0,0,0);
                }

                break;
            }
            case R.id.cimgAvatar:{
                if (ContextCompat.checkSelfPermission(mContext,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE,

                            }, 1);
                } else {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
                }
                break;
            }
            case R.id.imvAddImv:{
                Toast.makeText(mContext, "Hien", Toast.LENGTH_SHORT).show();
                if (ContextCompat.checkSelfPermission(mContext,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE,

                            }, 2);
                } else {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 2);

                }
                break;
            }
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                cimgAvatar.setImageURI(uri);
                uriImg = String.valueOf(uri);
                ktraAvatar=1;
            }
        }
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            if (data.getClipData() != null) {
                ClipData mClipData = data.getClipData();
                String[] projection = {MediaStore.Images.Media.DATA};
                ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                for (int i = 0; i < mClipData.getItemCount(); i++) {

                    ClipData.Item item = mClipData.getItemAt(i);
                    Uri uri = item.getUri();
                    mArrayUri.add(uri);
                    stringArrayList.add(uri+"");
                }
                Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                Log.v("LOG_TAG", "Selected Images" + mClipData.getItemCount());
            }
            imvUserToPhotographerAdapter.Update(stringArrayList,mContext);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
