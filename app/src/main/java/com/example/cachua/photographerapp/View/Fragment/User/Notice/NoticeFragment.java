package com.example.cachua.photographerapp.View.Fragment.User.Notice;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Adapter.NoticeAdapter;
import com.example.cachua.photographerapp.View.Fragment.BaseFragment;
import com.example.cachua.photographerapp.View.Model.NoticeModel;
import com.example.cachua.photographerapp.View.Model.UseModel;
import com.example.cachua.photographerapp.View.config.Constants;
import com.example.cachua.photographerapp.View.config.SharedPreferencesUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class NoticeFragment extends BaseFragment {
    private RecyclerView rcNotice;
    private NoticeAdapter noticeAdapter;
    private ArrayList<NoticeModel> noticeModelArrayList;

    public static NoticeFragment sInstance;
    public static NoticeFragment newInstance() {
        if (sInstance == null) {
            sInstance = new NoticeFragment();
        }
        return sInstance;
    }

    @Override
    protected int getLayoutResource() {
        return (R.layout.fragment_notice);
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        rcNotice= rootView.findViewById(R.id.rcNotice);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        noticeModelArrayList= new ArrayList<>();

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onResume() {
        super.onResume();
        noticeAdapter= new NoticeAdapter(noticeModelArrayList,mContext);
        rcNotice.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        rcNotice.setAdapter(noticeAdapter);
        LoadDataNotice(noticeModelArrayList);
    }

    private void LoadDataNotice(final ArrayList<NoticeModel> noticeModels) {
        noticeModels.clear();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("notifications")
                .whereEqualTo("userId", SharedPreferencesUtils.getString(mContext, Constants.ID_USER)+"")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                NoticeModel noticeModel= new NoticeModel(String.valueOf(document.getId()),String.valueOf(document.getData().get("collection")),
                                        String.valueOf(document.getData().get("documentId")),String.valueOf(document.getData().get("message")),
                                        String.valueOf(document.getData().get("type")),String.valueOf(document.getData().get("userId")));
                                noticeModels.add(noticeModel);
                                noticeAdapter.Update(noticeModels,mContext);
                            }

                        }
                        else{

                        }
                    }
                });
    }
}
