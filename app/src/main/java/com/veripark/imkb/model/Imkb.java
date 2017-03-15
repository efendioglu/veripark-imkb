package com.veripark.imkb.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Root;

/**
 * Created by Kemal on 8/30/16.
 */
@Root
@Order(elements = {"Symbol", "Name", "Gain", "Fund"})
public class Imkb implements Parcelable{
    @Element(name = "Symbol", required = false)
    private String symbol;

    @Element(name = "Name", required = false)
    private String name;

    @Element(name = "Gain", required = false)
    private float gain;

    @Element(name = "Fund", required = false)
    private float fund;

    public Imkb() {
    }

    protected Imkb(Parcel in) {
        symbol = in.readString();
        name = in.readString();
        gain = in.readFloat();
        fund = in.readFloat();
    }

    public static final Creator<Imkb> CREATOR = new Creator<Imkb>() {
        @Override
        public Imkb createFromParcel(Parcel in) {
            return new Imkb(in);
        }

        @Override
        public Imkb[] newArray(int size) {
            return new Imkb[size];
        }
    };

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getGain() {
        return gain;
    }

    public void setGain(float gain) {
        this.gain = gain;
    }

    public float getFund() {
        return fund;
    }

    public void setFund(float fund) {
        this.fund = fund;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(symbol);
        parcel.writeString(name);
        parcel.writeFloat(gain);
        parcel.writeFloat(fund);
    }
}
