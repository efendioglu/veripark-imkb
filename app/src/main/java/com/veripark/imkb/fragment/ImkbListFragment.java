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

import com.veripark.imkb.ImkbDetailActivity;
import com.veripark.imkb.R;
import com.veripark.imkb.StockListActivity;
import com.veripark.imkb.adapter.ImkbListAdapter;
import com.veripark.imkb.model.Imkb;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImkbListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.imkbListRV)
    RecyclerView mRecyclerView;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private ImkbListAdapter mAdapter;


    public ImkbListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_imkb_list, container, false);
        ButterKnife.bind(this, view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ImkbListAdapter(getContext(), new ArrayList());

        mAdapter.setOnItemClickListener(new ImkbListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Imkb imkb) {
                startActivity(ImkbDetailActivity.newIntent(getContext(), imkb));
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
                    mAdapter.setImkbList(dataList);
                } catch(Exception e){
                }
            }
        }, 300);
    }
}
