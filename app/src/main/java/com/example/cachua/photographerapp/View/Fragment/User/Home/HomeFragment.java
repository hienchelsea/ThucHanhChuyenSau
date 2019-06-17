package com.example.cachua.photographerapp.View.Fragment.User.Home;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Activity.MainUserActivity;
import com.example.cachua.photographerapp.View.Adapter.HomePhotographerAdapter;
import com.example.cachua.photographerapp.View.Adapter.HomeViewPagerAdapter;
import com.example.cachua.photographerapp.View.Fragment.BaseFragment;
import com.example.cachua.photographerapp.View.Fragment.User.Profile.ProfileFragment;
import com.example.cachua.photographerapp.View.Fragment.User.Profile.ProfileLoveFragment;
import com.example.cachua.photographerapp.View.Model.FavoritesModel;
import com.example.cachua.photographerapp.View.Model.UseModel;
import com.example.cachua.photographerapp.View.config.Constants;
import com.example.cachua.photographerapp.View.config.SharedPreferencesUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends BaseFragment {
    private HomePhotographerAdapter homePhotographerAdapter;
    private RecyclerView rcPhotographer;
  //  private LinearLayout llTime;
   // private LinearLayout llDate;
  //  private LinearLayout llCoupons;
   // private LinearLayout llCount;
    private LinearLayout llSearch;
    private LinearLayout llSearchInformation;
    private ImageView imvSearch;
   // private ImageView imvSearchEdit;
 //   private TextView tvTime;
  //  private TextView tvDate;
  //  private TextView tvCoupons;
 //   private TextView tvCount;
   // private EditText edtName;
    private DecimalFormat df = new DecimalFormat("#00");
    private ArrayList<FavoritesModel> favoritesModelArrayList;
    String date;
    private GestureDetector gestureDetector;
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public static HomeFragment sInstance;

    public static HomeFragment newInstance() {
        if (sInstance == null) {
            sInstance = new HomeFragment();
        }
        return sInstance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("TAG", "CREATE");


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return (R.layout.fragment_home);
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        rcPhotographer = rootView.findViewById(R.id.rcPhotographer);
        imvSearch = rootView.findViewById(R.id.imvSearch);
     //   llDate = rootView.findViewById(R.id.llDate);
       // llCoupons = rootView.findViewById(R.id.llCoupons);
        llSearch = rootView.findViewById(R.id.llSearch);
        llSearchInformation = rootView.findViewById(R.id.llSearchInformation);
    //    llSearchEdit = rootView.findViewById(R.id.llSearchEdit);
   //     tvTime = rootView.findViewById(R.id.tvTime);
//        tvDate = rootView.findViewById(R.id.tvDate);
//        tvCoupons = rootView.findViewById(R.id.tvCoupons);
//        tvCount = rootView.findViewById(R.id.tvCount);
//        edtName = rootView.findViewById(R.id.edtName);
  //      imvSearchEdit = rootView.findViewById(R.id.imvSearchEdit);

        llSearch.setOnClickListener(this);
 //       llDate.setOnClickListener(this);
//        llCoupons.setOnClickListener(this);
        // llCount.setOnClickListener(this);
        imvSearch.setOnClickListener(this);
        //imvSearchEdit.setOnClickListener(this);


    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.llTime:{
//                showDialogTime();
//                break;
//            }
//            case R.id.llDate: {
//                DatePicker(tvDate);
//                break;
//            }
//            case R.id.llCoupons: {
//                showDialogCoupons();
//                break;
//            }
//            case R.id.llCount:{
//                showDialogCount();
//                break;
//            }
            case R.id.llSearch: {
                SearchPhotographerFragment searchPhotographerFragment= new SearchPhotographerFragment();
                ((MainUserActivity) Objects.requireNonNull(mContext)).nextFragment(searchPhotographerFragment, R.id.container, 0, 0, 0, 0);
                break;
            }
//            case R.id.imvSearchEdit: {
//                if (llSearchInformation.getVisibility() == View.VISIBLE) {
//                    llSearchInformation.setVisibility(View.GONE);
//                } else {
//                    if (llSearchInformation.getVisibility() == View.GONE) {
//                        llSearchInformation.setVisibility(View.VISIBLE);
//                    }
//                }
//                break;
//            }
            case R.id.imvSearch: {

                break;
            }

        }

    }


    @Override
    public void onResume() {
        super.onResume();
        DaLuong initDataBase = new DaLuong();
        initDataBase.execute();

    }

    private void LoadData(final ArrayList<UseModel> useModelArrayList) {
        db.collection("users")
                .whereEqualTo("role", "Photographer")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                UseModel useModel = new UseModel(String.valueOf(document.getData().get("id")), String.valueOf(document.getData().get("bio")),
                                        String.valueOf(document.getData().get("birth")), String.valueOf(document.getData().get("email")),
                                        String.valueOf(document.getData().get("location")), String.valueOf(document.getData().get("name")),
                                        String.valueOf(document.getData().get("phone")), Double.parseDouble(String.valueOf(document.getData().get("rating"))),
                                        String.valueOf(document.getData().get("role")), String.valueOf(document.getData().get("avatar"))
                                );
                                useModelArrayList.add(useModel);
                                homePhotographerAdapter.Update(useModelArrayList, mContext);
                            }
                            SharedPreferencesUtils.setInt(mContext, Constants.CHECK_DATA, 1);
                        } else {
                            Toast.makeText(mContext, "Loi", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void LoadDataLove(final ArrayList<FavoritesModel> favoritesModels) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("favorites")
                .whereEqualTo("userId", SharedPreferencesUtils.getString(mContext, Constants.ID_USER) + "")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                FavoritesModel favoritesModel = new FavoritesModel(String.valueOf(document.getId()),
                                        String.valueOf(document.getData().get("photographerId")), String.valueOf(document.getData().get("userId")));
                                favoritesModels.add(favoritesModel);
                                Log.i("HienOcc", favoritesModel.getId() + "onComplete: ");
                            }
                        } else {
                            Toast.makeText(mContext, "Loi", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void showDialogTime() {

        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dailog_seclect_time);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Window window = dialog.getWindow();
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);

        RadioButton rbtnFullTime = dialog.findViewById(R.id.rbtnFullTime);
        RadioButton rbtnMorning = dialog.findViewById(R.id.rbtnMorning);
        RadioButton rbtnAfternoon = dialog.findViewById(R.id.rbtnAfternoon);
        RadioButton rbtnNight = dialog.findViewById(R.id.rbtnNight);

//        rbtnFullTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tvTime.setText("Ca ngay (7h-22h)");
//                dialog.dismiss();
//            }
//        });
//        rbtnMorning.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tvTime.setText("Sang (7h-11h)");
//                dialog.dismiss();
//            }
//        });
//        rbtnAfternoon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tvTime.setText("Chieu (13h-17h)");
//                dialog.dismiss();
//            }
//        });
//        rbtnNight.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tvTime.setText("Toi (19h-23h)");
//                dialog.dismiss();
//            }
//        });


        dialog.show();
    }

    public void showDialogCoupons() {

        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dailog_seclect_coupons);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Window window = dialog.getWindow();
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);

        final RadioButton rbtnCoupons = dialog.findViewById(R.id.rbtnCoupons);
        final RadioButton rbtnKyYeu = dialog.findViewById(R.id.rbtnKyYeu);
        final RadioButton rbtnDaNgoai = dialog.findViewById(R.id.rbtnDaNgoai);
        final RadioButton rbtnConcept = dialog.findViewById(R.id.rbtnConcept);
        final RadioButton rbtnChanDung = dialog.findViewById(R.id.rbtnChanDung);
        final EditText edtCoupons = dialog.findViewById(R.id.edtCoupons);

        edtCoupons.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                rbtnCoupons.setVisibility(View.VISIBLE);
                rbtnCoupons.setText(edtCoupons.getText().toString());
                if (edtCoupons.getText().toString().equals("") == true) {
                    rbtnCoupons.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
//        RadioButton rbtnMorning= dialog.findViewById(R.id.rbtnMorning);
//        RadioButton rbtnAfternoon= dialog.findViewById(R.id.rbtnAfternoon);
//        RadioButton rbtnNight= dialog.findViewById(R.id.rbtnNight);

//        rbtnCoupons.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tvCoupons.setText(edtCoupons.getText().toString());
//                dialog.dismiss();
//            }
//        });
//        rbtnKyYeu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tvCoupons.setText("Chup anh ky yeu");
//                dialog.dismiss();
//            }
//        });
//        rbtnDaNgoai.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tvCoupons.setText("Chup anh da ngoai");
//                dialog.dismiss();
//            }
//        });
//        rbtnConcept.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tvCoupons.setText("Chup anh concept");
//                dialog.dismiss();
//            }
//        });
//        rbtnChanDung.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tvCoupons.setText("Chup anh chan dung");
//                dialog.dismiss();
//            }
//        });


        dialog.show();
    }

    public void showDialogCount() {

        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dailog_seclect_count);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Window window = dialog.getWindow();
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);

        final EditText edtCount = dialog.findViewById(R.id.edtCount);
        final Button btnCount = dialog.findViewById(R.id.btnCount);


        btnCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tvCount.setText(edtCount.getText().toString());
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void DatePicker(final TextView textView) {
        date = "";
        Calendar mcurrentTime = Calendar.getInstance();
        int year = mcurrentTime.get(Calendar.YEAR);
        int monthOfYear = mcurrentTime.get(Calendar.MONTH);
        int dayOfMonth = mcurrentTime.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                date = df.format(dayOfMonth) + "/" + df.format(month) + "/" + year;
                textView.setText(date);
            }
        }, year, monthOfYear, dayOfMonth);
        datePickerDialog.show();
    }

    class MyAbc extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (e2.getY() - e1.getY() > 200) {
                Toast.makeText(mContext, "dddd", Toast.LENGTH_SHORT).show();
            } else {
                if (e1.getY() - e2.getY() > 200) {
                    Toast.makeText(mContext, "dddd", Toast.LENGTH_SHORT).show();
                }
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }

    class DaLuong extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            favoritesModelArrayList = new ArrayList<>();
            LoadDataLove(favoritesModelArrayList);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            ArrayList<UseModel> useModelArrayList = new ArrayList<>();
            homePhotographerAdapter = new HomePhotographerAdapter(useModelArrayList, mContext, favoritesModelArrayList);
            rcPhotographer.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            rcPhotographer.setAdapter(homePhotographerAdapter);
            LoadData(useModelArrayList);

        }
    }

}
