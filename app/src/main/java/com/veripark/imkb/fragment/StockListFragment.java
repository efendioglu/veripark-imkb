package com.veripark.imkb.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.veripark.imkb.R;
import com.veripark.imkb.StockDetailActivity;
import com.veripark.imkb.StockListActivity;
import com.veripark.imkb.adapter.StockListAdapter;
import com.veripark.imkb.model.Stock;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class StockListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.stockListRV)
    RecyclerView mRecyclerView;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private StockListAdapter mAdapter;


    public StockListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stock_list2, container, false);
        ButterKnife.bind(this, view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new StockListAdapter(getContext(), new ArrayList<Stock>());


        mAdapter.setOnItemClickListener(new StockListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Stock stock) {
                startActivity(StockDetailActivity.newIntent(getContext(), stock));
            }
        });

        mRecyclerView.setAdapter(mAdapter);

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(this);

        return view;
    }



    @Override
    public void onRefresh() {
        ((StockListActivity) getActivity()).requestStockList();
    }

    @Override
    public void showProgress() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onResponse(final ArrayList dataList) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try{
                    mAdapter.setStockList(dataList);
                } catch(Exception e){
                }
            }
        }, 300);
    }
}
