package com.veripark.imkb.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Root;

/**
 * Created by Kemal on 8/30/16.
 */
@Root(name = "StockandIndexGraphic")
//@Order(elements = {"Price", "Date"})
public class GraphData {
    @Element(name = "Price", required = false)
    private float price;

    @Element(name = "Date", required = false)
    private String date;

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
