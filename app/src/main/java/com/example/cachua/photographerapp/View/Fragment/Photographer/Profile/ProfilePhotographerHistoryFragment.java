package com.example.cachua.photographerapp.View.Fragment.Photographer.Profile;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.example.cachua.photographerapp.R;
import com.example.cachua.photographerapp.View.Adapter.HistoryPhotographerAdapter;
import com.example.cachua.photographerapp.View.Adapter.ProfilePhotographerHistoryAdapter;
import com.example.cachua.photographerapp.View.Fragment.BaseFragment;
import com.example.cachua.photographerapp.View.Model.OrdersModel;

import java.util.ArrayList;

public class ProfilePhotographerHistoryFragment extends BaseFragment {
    private RecyclerView rcHistory;
    private ProfilePhotographerHistoryAdapter profilePhotographerHistoryAdapter;
    private ArrayList<OrdersModel> ordersModelArrayList;


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_profile_photographer_history;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        rcHistory= rootView.findViewById(R.id.rcHistory);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ordersModelArrayList= new ArrayList<>();
//        ordersModelArrayList.add(new OrdersModel(1,"Khuyen mai","22/01/1997","khong biet","abc",3,"Hien Do","Dang cho",200000,"Oc","Ha Noi","Me tri"));
//        ordersModelArrayList.add(new OrdersModel(1,"Khuyen mai","27/12/1997","khong biet","abc",3,"Do Minh","Dang cho",30000,"Oc","Ha Noi","My Dinh"));
//        ordersModelArrayList.add(new OrdersModel(1,"Khuyen mai","08/10/1997","khong biet","abc",3,"Do Duy Manh","Dang cho",50000,"Oc","Ha Noi","Buu chinh vien thong"));
        profilePhotographerHistoryAdapter= new ProfilePhotographerHistoryAdapter(mContext,ordersModelArrayList);
        rcHistory.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        rcHistory.setAdapter(profilePhotographerHistoryAdapter);

    }

    @Override
    public void onClick(View v) {

    }
}
