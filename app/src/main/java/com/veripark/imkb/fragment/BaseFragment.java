package com.veripark.imkb.fragment;


import android.support.v4.app.Fragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment {

    //Hisse Ve Endeksler
    public final static int STOCKS = 1;

    //Yükselenler
    public final static int RISING = 2;

    //Düşenler
    public final static int FALLING = 3;

    //Hacme Göre IMKB100
    public final static int IMKB100 = 4;

    //Hacme Göre IMKB50
    public final static int IMKB50 = 5;

    //Hacme Göre IMKB30
    public final static int IMKB30 = 6;

    private int type;

    public BaseFragment() {
        // Required empty public constructor
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public abstract void showProgress();
    public abstract void hideProgress();
    public abstract void onResponse(ArrayList dataList);

}
