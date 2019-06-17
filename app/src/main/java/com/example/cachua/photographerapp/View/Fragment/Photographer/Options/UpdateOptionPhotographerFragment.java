package com.example.cachua.photographerapp.View.Fragment.Photographer.Options;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Fragment.BaseFragment;
import com.example.cachua.photographerapp.View.Model.OptionsModel;
import com.example.cachua.photographerapp.View.config.Constants;
import com.example.cachua.photographerapp.View.config.SharedPreferencesUtils;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class UpdateOptionPhotographerFragment extends BaseFragment {
    private Button btnAdd;
    private ImageView imgBack;
    private EditText edtName;
    private EditText edtType;
    private EditText edtShots;
    private EditText edtPrints;
    private EditText edtPriceDay;
    private EditText edtPriceHour;
    private EditText edtNote;
    String id;

    @Override
    protected int getLayoutResource() {
        return R.layout.update_option_fragment;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        btnAdd = rootView.findViewById(R.id.btnAdd);
        imgBack = rootView.findViewById(R.id.imgBack);
        edtName = rootView.findViewById(R.id.edtName);
        edtType = rootView.findViewById(R.id.edtType);
        edtShots = rootView.findViewById(R.id.edtShots);
        edtPrints = rootView.findViewById(R.id.edtPrints);
        edtPriceDay = rootView.findViewById(R.id.edtPriceDay);
        edtPriceHour = rootView.findViewById(R.id.edtPriceHour);
        edtNote = rootView.findViewById(R.id.edtNote);

        btnAdd.setOnClickListener(this);
        imgBack.setOnClickListener(this);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        edtName.setText(bundle.getString("name") + "");
        edtPriceDay.setText(bundle.getFloat("price_per_day") + "");
        edtPriceHour.setText(bundle.getFloat("price_per_hour") + "");
        edtPrints.setText(bundle.getInt("prints") + "");
        edtShots.setText(bundle.getInt("shots") + "");
        edtNote.setText(bundle.getString("description") + "");
        edtType.setText(bundle.getString("type") + "");
        id= bundle.getString("id");


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack: {
                Objects.requireNonNull(getActivity()).onBackPressed();
                break;
            }
            case R.id.btnAdd: {
                UpdateOption();
                Objects.requireNonNull(getActivity()).onBackPressed();

            }

            break;
        }
    }
    public void UpdateOption(){
        FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();
        DocumentReference documentReference= firebaseFirestore.collection("options").document(id+"");
        documentReference.update("description",edtNote.getText().toString()+"");
        documentReference.update("name",edtName.getText().toString()+"");
        documentReference.update("price_per_day",Float.parseFloat(edtPriceDay.getText().toString()));
        documentReference.update("price_per_hour",Float.parseFloat(edtPriceHour.getText().toString()));
        documentReference.update("prints",Integer.parseInt(edtPrints.getText().toString()));
        documentReference.update("shots",Integer.parseInt(edtShots.getText().toString()));
        documentReference.update("type",edtType.getText().toString()+"");
    }


}

