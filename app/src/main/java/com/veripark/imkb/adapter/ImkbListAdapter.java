package com.veripark.imkb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.veripark.imkb.R;
import com.veripark.imkb.model.Imkb;
import com.veripark.imkb.model.Stock;

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
public class ImkbListAdapter extends RecyclerView.Adapter<ImkbListAdapter.ViewHolder> {

    public interface OnItemClickListener{
        void onItemClick(Imkb imkb);
    }

    private OnItemClickListener onItemClickListener;

    private List imkbList = new ArrayList<>();

    private Context mContext;


    public ImkbListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public ImkbListAdapter(Context mContext, ArrayList imkbList) {
        this.imkbList = imkbList;
        this.mContext = mContext;
    }


    public List getImkbList() {
        return imkbList;
    }

    public void setImkbList(List imkbList) {
        this.imkbList = imkbList;
        notifyDataSetChanged();
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public ImkbListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.itemlist_imkb, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImkbListAdapter.ViewHolder holder, int position) {
        if(!(imkbList.get(position) instanceof Imkb))
            return;

        Imkb imkb = (Imkb) imkbList.get(position);

        holder.symbol.setText(imkb.getSymbol());
        holder.gain.setText(String.valueOf(imkb.getGain()));
        holder.fund.setText(String.valueOf(imkb.getFund()));
    }


    @Override
    public int getItemCount() {
        return imkbList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.symbolTV) TextView symbol;
        @BindView(R.id.gainTV) TextView gain;
        @BindView(R.id.fundTV) TextView fund;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.root)
        void onClick(){
            if(onItemClickListener != null)
                onItemClickListener.onItemClick((Imkb) imkbList.get(getAdapterPosition()));
        }
    }
}
