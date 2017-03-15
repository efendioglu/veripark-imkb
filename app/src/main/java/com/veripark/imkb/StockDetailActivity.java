package com.veripark.imkb;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.veripark.imkb.model.GraphData;
import com.veripark.imkb.model.Imkb;
import com.veripark.imkb.model.Period;
import com.veripark.imkb.model.Stock;
import com.veripark.imkb.request.Encrypt;
import com.veripark.imkb.request.GetForexStocksandIndexesInfo;
import com.veripark.imkb.response.EncryptResponse;
import com.veripark.imkb.response.GetForexStocksandIndexesResponse;
import com.veripark.imkb.service.OnResponseListener;
import com.veripark.imkb.service.SoapClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StockDetailActivity extends AppCompatActivity {

    @BindView(R.id.chartView)
    LineChart mChartView;

    @BindView(R.id.symbolTV)
    TextView mSymbolTv;

    @BindView(R.id.differenceTV)
    TextView mDifferenceTv;

    @BindView(R.id.indicator)
    ImageView mIndicator;

    @BindView(R.id.peakPriceTV)
    TextView mPeakPriceTv;

    @BindView(R.id.lowestPriceTV)
    TextView mLowestPriceTv;

    @BindView(R.id.volumeTV)
    TextView mVolumeTv;

    @BindView(R.id.totalTV)
    TextView mTotalTv;

    @BindView(R.id.progressBar)
    ProgressBar mProgressView;

    private Stock mStock;

    @Period.Type
    private String mPeriod = Period.MONTHLY;

    private final static String KEY_STOCK = "stock";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_detail);
        ButterKnife.bind(this);

        mStock = getIntent().getParcelableExtra(KEY_STOCK);

        mChartView.setDescription("");

        mSymbolTv.setText(mStock.getSymbol());
        mDifferenceTv.setText(String.valueOf(mStock.getDifference()));
        if(mStock.getDifference() > 0){
            mIndicator.setImageResource(R.drawable.ic_arrow_up);
        } else{
            mIndicator.setImageResource(R.drawable.ic_arrow_down);
        }

        mPeakPriceTv.setText(String.valueOf(mStock.getDayPeakPrice()));
        mLowestPriceTv.setText(String.valueOf(mStock.getDayLowestPrice()));
        mVolumeTv.setText(String.valueOf(mStock.getVolume()));
        mTotalTv.setText(String.valueOf(mStock.getTotal()));

        mProgressView.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorAccent), android.graphics.PorterDuff.Mode.MULTIPLY);

    }


    @Override
    protected void onResume() {
        super.onResume();
        requestGraphData();
    }


    public static Intent newIntent(Context callerContext, Stock stock){
        Intent intent = new Intent(callerContext, StockDetailActivity.class);
        intent.putExtra(KEY_STOCK, stock);
        return intent;
    }


    private void requestGraphData(){
        showProgress();

        SoapClient.instance().getEncryptedRequestKey(new Encrypt(), new OnResponseListener() {
            @Override
            public <T> void onResponse(T response) {
                EncryptResponse responseData = (EncryptResponse) response;


                SoapClient.instance().getStockList(new GetForexStocksandIndexesInfo(responseData.getRequestKey(), mStock.getSymbol(), mPeriod), new OnResponseListener() {
                    @Override
                    public <T> void onResponse(T response) {

                        hideProgress();

                        GetForexStocksandIndexesResponse responseData = (GetForexStocksandIndexesResponse) response;

                        if(responseData.getRequestResult().isSuccess()){
                            final ArrayList<GraphData> grapDataList = responseData.getGraphDataList();

                            setGraphData(grapDataList);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        hideProgress();

                    }
                });
            }

            @Override
            public void onError(Throwable t) {
                hideProgress();
            }
        });
    }



    private void setGraphData(final ArrayList<GraphData> grapDataList) {
        List<Entry> entryList = new ArrayList<Entry>();
        for(int i = 0; i < grapDataList.size(); i++){
            entryList.add(new Entry(i, grapDataList.get(i).getPrice(), grapDataList.get(i).getDate()));
        }


        AxisValueFormatter formatter = new AxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                //2016-08-29T00:00:00
                String dateStr = grapDataList.get((int)value).getDate();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                try {
                    Date date = sdf.parse(dateStr);
                    return new SimpleDateFormat("dd MMM").format(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return grapDataList.get((int)value).getDate().substring(0, 9);
            }

            // we don't draw numbers, so no decimal digits needed
            @Override
            public int getDecimalDigits() {  return 0; }
        };


        XAxis xAxis = mChartView.getXAxis();

        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);

        LineDataSet lineDataSet = new LineDataSet(entryList, getChartTitle());
        LineData lineData = new LineData(lineDataSet);
        mChartView.setData(lineData);
        mChartView.invalidate();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stock_detail, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_monthly) {
            mPeriod = Period.MONTHLY;
            requestGraphData();
            return true;
        } else if (id == R.id.action_weekly) {
            mPeriod = Period.WEEKLY;
            requestGraphData();
            return true;
        } else if (id == R.id.action_daily) {
            mPeriod = Period.DAILY;
            requestGraphData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private String getChartTitle(){
        if(mPeriod == Period.MONTHLY)
            return getString(R.string.chart_title_monthly);
        else if(mPeriod == Period.WEEKLY)
            return getString(R.string.chart_title_weekly);
        else if(mPeriod == Period.DAILY)
            return getString(R.string.chart_title_daily);

        return "";
    }



    private void showProgress(){
        mProgressView.setVisibility(View.VISIBLE);
        mChartView.setVisibility(View.GONE);
    }



    private void hideProgress(){
        mProgressView.setVisibility(View.GONE);
        mChartView.setVisibility(View.VISIBLE);
    }
}
