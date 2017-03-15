package com.veripark.imkb.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

/**
 * Created by Kemal on 8/30/16.
 */
@Root(name = "StockandIndex")
@Order(elements = {"Symbol", "Price", "Difference", "Volume", "Buying", "Selling", "Hour", "DayPeakPrice", "DayLowestPrice", "Total", "IsIndex"})
public class Stock implements Parcelable{
    @Element(name = "Symbol", required = false)
    private String symbol;

    @Element(name = "Price", required = false)
    private float price;

    @Element(name = "Difference", required = false)
    private float difference;

    @Element(name = "Volume", required = false)
    private float volume;

    @Element(name = "Buying", required = false)
    private float buying;

    @Element(name = "Selling", required = false)
    private float selling;

    @Element(name = "Hour", required = false)
    private String hour;

    @Element(name = "DayPeakPrice", required = false)
    private float dayPeakPrice;

    @Element(name = "DayLowestPrice", required = false)
    private float dayLowestPrice;

    @Element(name = "Total", required = false)
    private float total;

    @Element(name = "IsIndex", required = false)
    private boolean isIndex;

    public Stock() {
    }

    protected Stock(Parcel in) {
        symbol = in.readString();
        price = in.readFloat();
        difference = in.readFloat();
        volume = in.readFloat();
        buying = in.readFloat();
        selling = in.readFloat();
        hour = in.readString();
        dayPeakPrice = in.readFloat();
        dayLowestPrice = in.readFloat();
        total = in.readFloat();
        isIndex = in.readByte() != 0;
    }

    public static final Creator<Stock> CREATOR = new Creator<Stock>() {
        @Override
        public Stock createFromParcel(Parcel in) {
            return new Stock(in);
        }

        @Override
        public Stock[] newArray(int size) {
            return new Stock[size];
        }
    };

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getDifference() {
        return difference;
    }

    public void setDifference(float difference) {
        this.difference = difference;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public float getBuying() {
        return buying;
    }

    public void setBuying(float buying) {
        this.buying = buying;
    }

    public float getSelling() {
        return selling;
    }

    public void setSelling(float selling) {
        this.selling = selling;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public float getDayPeakPrice() {
        return dayPeakPrice;
    }

    public void setDayPeakPrice(float dayPeakPrice) {
        this.dayPeakPrice = dayPeakPrice;
    }

    public float getDayLowestPrice() {
        return dayLowestPrice;
    }

    public void setDayLowestPrice(float dayLowestPrice) {
        this.dayLowestPrice = dayLowestPrice;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public boolean isIndex() {
        return isIndex;
    }

    public void setIndex(boolean index) {
        isIndex = index;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(symbol);
        parcel.writeFloat(price);
        parcel.writeFloat(difference);
        parcel.writeFloat(volume);
        parcel.writeFloat(buying);
        parcel.writeFloat(selling);
        parcel.writeString(hour);
        parcel.writeFloat(dayPeakPrice);
        parcel.writeFloat(dayLowestPrice);
        parcel.writeFloat(total);
        parcel.writeByte((byte) (isIndex ? 1 : 0));
    }

}
