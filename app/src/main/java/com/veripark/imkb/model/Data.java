package com.veripark.imkb.model;

import java.util.ArrayList;

/**
 * Created by Kemal on 8/31/16.
 */
public class Data {
    private static Data ourInstance = new Data();

    private ArrayList<Stock> stockList;

    private ArrayList<Stock> risingStockList;

    private ArrayList<Stock> fallingStockList;

    private ArrayList<Imkb30> imkb30List;

    private ArrayList<Imkb50> imkb50List;

    private ArrayList<Imkb100> imkb100List;

    private ArrayList<GraphData> graphDataList;

    public static Data getInstance() {
        return ourInstance;
    }

    private Data() {
    }

    public ArrayList<Stock> getStockList() {
        return stockList;
    }

    public void setStockList(ArrayList<Stock> stockList) {
        this.stockList = stockList;
    }

    public ArrayList<Imkb30> getImkb30List() {
        return imkb30List;
    }

    public void setImkb30List(ArrayList<Imkb30> imkb30List) {
        this.imkb30List = imkb30List;
    }

    public ArrayList<Imkb50> getImkb50List() {
        return imkb50List;
    }

    public void setImkb50List(ArrayList<Imkb50> imkb50List) {
        this.imkb50List = imkb50List;
    }

    public ArrayList<Imkb100> getImkb100List() {
        return imkb100List;
    }

    public void setImkb100List(ArrayList<Imkb100> imkb100List) {
        this.imkb100List = imkb100List;
    }

    public ArrayList<GraphData> getGraphDataList() {
        return graphDataList;
    }

    public void setGraphDataList(ArrayList<GraphData> graphDataList) {
        this.graphDataList = graphDataList;
    }

    public ArrayList<Stock> getRisingStockList() {
        return risingStockList;
    }

    public void setRisingStockList(ArrayList<Stock> risingStockList) {
        this.risingStockList = risingStockList;
    }

    public ArrayList<Stock> getFallingStockList() {
        return fallingStockList;
    }

    public void setFallingStockList(ArrayList<Stock> fallingStockList) {
        this.fallingStockList = fallingStockList;
    }
}
