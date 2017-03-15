package com.veripark.imkb;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.veripark.imkb.adapter.StockListAdapter;
import com.veripark.imkb.model.Data;
import com.veripark.imkb.model.Stock;
import com.veripark.imkb.request.Encrypt;
import com.veripark.imkb.request.GetForexStocksandIndexesInfo;
import com.veripark.imkb.response.EncryptResponse;
import com.veripark.imkb.response.GetForexStocksandIndexesResponse;
import com.veripark.imkb.service.OnResponseListener;
import com.veripark.imkb.service.SoapClient;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.stocksBtn)
    Button mStocksBtn;

    @BindView(R.id.progressBar)
    ProgressBar mProgressView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mProgressView.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorAccent), android.graphics.PorterDuff.Mode.MULTIPLY);
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestStockList();
    }

    @OnClick(R.id.stocksBtn)
    void onClick(){
        startActivity(new Intent(this, StockListActivity.class));
    }

    public void requestStockList() {
        showProgress();

        SoapClient.instance().getEncryptedRequestKey(new Encrypt(), new OnResponseListener() {
            @Override
            public <T> void onResponse(T response) {

                EncryptResponse responseData = (EncryptResponse) response;
                String key = responseData.getRequestKey();

                SoapClient.instance().getStockList(new GetForexStocksandIndexesInfo(key), new OnResponseListener() {
                    @Override
                    public <T> void onResponse(T response) {
                        GetForexStocksandIndexesResponse responseData = (GetForexStocksandIndexesResponse) response;

                        if (responseData.getRequestResult().isSuccess()) {

                            Data.getInstance().setStockList(responseData.getStockList());

                            //calculate falling and rising stocks
                            ArrayList<Stock> risingList = new ArrayList<Stock>();
                            ArrayList<Stock> fallingList = new ArrayList<Stock>();
                            for (int i = 0; i < responseData.getStockList().size(); i++) {
                                Stock stock = responseData.getStockList().get(i);
                                if (stock.getDifference() < 0) {
                                    fallingList.add(stock);
                                } else if (stock.getDifference() > 0) {
                                    risingList.add(stock);
                                }
                            }

                            Data.getInstance().setRisingStockList(risingList);
                            Data.getInstance().setFallingStockList(fallingList);

                            Data.getInstance().setImkb30List(responseData.getImkb30List());
                            Data.getInstance().setImkb50List(responseData.getImkb50List());
                            Data.getInstance().setImkb100List(responseData.getImkb100List());
                        }

                        hideProgress();

                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                        hideProgress();
                    }
                });
            }

            @Override
            public void onError(Throwable t) {
                hideProgress();
                t.printStackTrace();
            }
        });
    }

    private void showProgress(){
        mProgressView.setVisibility(View.VISIBLE);
        mStocksBtn.setVisibility(View.GONE);
    }

    private void hideProgress(){
        mProgressView.setVisibility(View.GONE);
        mStocksBtn.setVisibility(View.VISIBLE);
    }

}
