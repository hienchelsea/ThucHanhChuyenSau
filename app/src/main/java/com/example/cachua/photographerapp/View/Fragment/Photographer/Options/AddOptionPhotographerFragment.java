package com.example.cachua.photographerapp.View.Fragment.Photographer.Options;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Activity.LoginActivity;
import com.example.cachua.photographerapp.View.Fragment.BaseFragment;
import com.example.cachua.photographerapp.View.Model.OptionsModel;
import com.example.cachua.photographerapp.View.config.Constants;
import com.example.cachua.photographerapp.View.config.SharedPreferencesUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class AddOptionPhotographerFragment extends BaseFragment {
    private Button btnAdd;
    private ImageView imgBack;
    private EditText edtName;
    private EditText edtType;
    private EditText edtShots;
    private EditText edtPrints;
    private EditText edtPriceDay;
    private EditText edtPriceHour;
    private EditText edtNote;

    @Override
    protected int getLayoutResource() {
        return R.layout.add_option_photographer_fragment;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        btnAdd= rootView.findViewById(R.id.btnAdd);
        imgBack= rootView.findViewById(R.id.imgBack);
        edtName= rootView.findViewById(R.id.edtName);
        edtType= rootView.findViewById(R.id.edtType);
        edtShots= rootView.findViewById(R.id.edtShots);
        edtPrints= rootView.findViewById(R.id.edtPrints);
        edtPriceDay= rootView.findViewById(R.id.edtPriceDay);
        edtPriceHour= rootView.findViewById(R.id.edtPriceHour);
        edtNote= rootView.findViewById(R.id.edtNote);

        btnAdd.setOnClickListener(this);
        imgBack.setOnClickListener(this);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgBack:{
                Objects.requireNonNull(getActivity()).onBackPressed();
                break;
            }
            case R.id.btnAdd:{
                if(edtName.getText().toString().equals("")==false){
                    OptionsModel optionsModel= new OptionsModel("",edtNote.getText().toString(),edtName.getText().toString(),
                            Float.parseFloat(edtPriceDay.getText().toString()),Float.parseFloat(edtPriceHour.getText().toString()),
                            Integer.parseInt(edtPrints.getText().toString()),Integer.parseInt(edtShots.getText().toString()),
                            edtType.getText().toString(),SharedPreferencesUtils.getString(mContext,Constants.ID_USER));
                    AddOption(optionsModel);

                    Objects.requireNonNull(getActivity()).onBackPressed();

                }

                break;
            }
        }

    }
    private void AddOption(OptionsModel optionsModel) {
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        db.collection("options")
                .add(optionsModel)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(final DocumentReference documentReference) {


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }
}
