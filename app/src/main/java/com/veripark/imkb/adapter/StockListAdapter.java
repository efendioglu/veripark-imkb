package com.veripark.imkb.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.veripark.imkb.R;
import com.veripark.imkb.model.Stock;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Kemal on 1/13/16.
 */
public class StockListAdapter extends RecyclerView.Adapter<StockListAdapter.ViewHolder> {

    public interface OnItemClickListener{
        void onItemClick(Stock stock);
    }

    private OnItemClickListener onItemClickListener;

    private List<Stock> stockList = new ArrayList<>();;

    private Context mContext;


    public StockListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public StockListAdapter(Context mContext, ArrayList<Stock> stockList) {
        this.stockList = stockList;
        this.mContext = mContext;
    }


    public List<Stock> getStockList() {
        return stockList;
    }

    public void setStockList(ArrayList<Stock> stockList) {
        this.stockList = stockList;
        notifyDataSetChanged();
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public StockListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.itemlist_stock, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(StockListAdapter.ViewHolder holder, int position) {
        if(!(stockList.get(position) instanceof Stock))
            return;

        Stock stock = stockList.get(position);

        holder.symbol.setText(stock.getSymbol());
        holder.price.setText(String.valueOf(stock.getPrice()));
        holder.difference.setText(String.valueOf(stock.getDifference()));
        holder.volume.setText(String.valueOf(stock.getVolume()));
        holder.buying.setText(String.valueOf(stock.getBuying()));
        holder.selling.setText(String.valueOf(stock.getSelling()));

        SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
        try {
            Date date = sdf.parse(stock.getHour());
            holder.hour.setText(new SimpleDateFormat("HH:mm:ss").format(date));
        } catch (Exception e) {
            holder.hour.setText("00:00:00");
            e.printStackTrace();
        }

        if(stock.getDifference() > 0){
            holder.indicator.setImageResource(R.drawable.ic_arrow_up);
        } else{
            holder.indicator.setImageResource(R.drawable.ic_arrow_down);
        }
    }


    @Override
    public int getItemCount() {
        return stockList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.symbolTV) TextView symbol;
        @BindView(R.id.priceTV) TextView price;
        @BindView(R.id.differenceTV) TextView difference;
        @BindView(R.id.volumeTV) TextView volume;
        @BindView(R.id.buyingTV) TextView buying;
        @BindView(R.id.sellingTV) TextView selling;
        @BindView(R.id.hourTV) TextView hour;
        @BindView(R.id.indicator) ImageView indicator;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.root)
        void onClick(){
            if(onItemClickListener != null)
                onItemClickListener.onItemClick(stockList.get(getAdapterPosition()));
        }
    }
}
