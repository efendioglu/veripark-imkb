package com.veripark.imkb;

import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.veripark.imkb.fragment.BaseFragment;
import com.veripark.imkb.fragment.ImkbListFragment;
import com.veripark.imkb.fragment.StockListFragment;
import com.veripark.imkb.model.Data;
import com.veripark.imkb.model.Imkb;
import com.veripark.imkb.model.Stock;
import com.veripark.imkb.request.Encrypt;
import com.veripark.imkb.request.GetForexStocksandIndexesInfo;
import com.veripark.imkb.response.EncryptResponse;
import com.veripark.imkb.response.GetForexStocksandIndexesResponse;
import com.veripark.imkb.service.OnResponseListener;
import com.veripark.imkb.service.SoapClient;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StockListActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private ViewPagerAdapter mViewPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    @BindView(R.id.container)
    ViewPager mViewPager;

    @BindView(R.id.tabs)
    TabLayout mTabLayout;

    private StockListFragment stockListFragment;
    private StockListFragment risingStockListFragment;
    private StockListFragment fallingStockListFragment;

    private ImkbListFragment imkb100ListFragment;
    private ImkbListFragment imkb50ListFragment;
    private ImkbListFragment imkb30ListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_list);
        ButterKnife.bind(this);

        initFragments();

        // Create the adapter that will return a fragment for each of the six
        setViewPagerAdapter();

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mViewPagerAdapter.getItem(position).onResponse(getDataList());
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setViewPagerAdapter() {
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPagerAdapter.addFragment(stockListFragment, getString(R.string.fragment_title_stocks));
        mViewPagerAdapter.addFragment(risingStockListFragment, getString(R.string.fragment_title_rising_stocks));
        mViewPagerAdapter.addFragment(fallingStockListFragment, getString(R.string.fragment_title_falling_stocks));
        mViewPagerAdapter.addFragment(imkb100ListFragment, getString(R.string.fragment_title_imkb100));
        mViewPagerAdapter.addFragment(imkb50ListFragment, getString(R.string.fragment_title_imkb50));
        mViewPagerAdapter.addFragment(imkb30ListFragment, getString(R.string.fragment_title_imkb30));
        mViewPager.setAdapter(mViewPagerAdapter);
    }

    private void initFragments() {
        stockListFragment = new StockListFragment();
        stockListFragment.setType(BaseFragment.STOCKS);

        risingStockListFragment = new StockListFragment();
        risingStockListFragment.setType(BaseFragment.RISING);

        fallingStockListFragment = new StockListFragment();
        fallingStockListFragment.setType(BaseFragment.FALLING);

        imkb100ListFragment = new ImkbListFragment();
        imkb100ListFragment.setType(BaseFragment.IMKB100);

        imkb50ListFragment = new ImkbListFragment();
        imkb50ListFragment.setType(BaseFragment.IMKB50);

        imkb30ListFragment = new ImkbListFragment();
        imkb30ListFragment.setType(BaseFragment.IMKB30);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCurrentFragment().onResponse(getDataList());
    }

    private BaseFragment getCurrentFragment() {
        return mViewPagerAdapter.getItem(mViewPager.getCurrentItem());
    }

    public void requestStockList() {
        getCurrentFragment().showProgress();

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

                        getCurrentFragment().hideProgress();

                        getCurrentFragment().onResponse(getDataList());
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                        getCurrentFragment().hideProgress();
                    }
                });
            }

            @Override
            public void onError(Throwable t) {
                getCurrentFragment().hideProgress();
                t.printStackTrace();
            }
        });
    }

    public ArrayList getDataList() {
        BaseFragment currentFrag = getCurrentFragment();
        ArrayList stockList = new ArrayList<>();

        switch (currentFrag.getType()) {
            case BaseFragment.STOCKS:
                stockList = Data.getInstance().getStockList();
                break;
            case BaseFragment.RISING:
                stockList = Data.getInstance().getRisingStockList();
                break;
            case BaseFragment.FALLING:
                stockList = Data.getInstance().getFallingStockList();
                break;
            case BaseFragment.IMKB100:
                stockList = Data.getInstance().getImkb100List();
                break;
            case BaseFragment.IMKB50:
                stockList = Data.getInstance().getImkb50List();
                break;
            case BaseFragment.IMKB30:
                stockList = Data.getInstance().getImkb30List();
                break;
            default:
                stockList = new ArrayList<>();
        }


        if (stockList == null)
            stockList = new ArrayList<>();

        return stockList;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_stock_list, menu);


        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList searchList = getDataList();
                ArrayList filterList = new ArrayList();

                if (getCurrentFragment() instanceof StockListFragment) {
                    for (int i = 0; i < searchList.size(); i++) {
                        Stock stock = (Stock) searchList.get(i);
                        try {
                            if (stock.getSymbol().toUpperCase().contains(newText.toUpperCase())) {
                                filterList.add(stock);
                                continue;
                            }
                        } catch (Exception e) {
                        }
                    }
                } else {
                    for (int i = 0; i < searchList.size(); i++) {
                        Imkb imkb = (Imkb) searchList.get(i);
                        try {
                            if (imkb.getSymbol().toUpperCase().contains(newText.toUpperCase())) {
                                filterList.add(imkb);
                                continue;
                            }
                        } catch (Exception e) {
                        }
                    }
                }

                getCurrentFragment().onResponse(filterList);

                return true;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }



    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<BaseFragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public BaseFragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(BaseFragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }


}
