package com.veripark.imkb.response;

import com.veripark.imkb.model.GraphData;
import com.veripark.imkb.model.Imkb;
import com.veripark.imkb.model.Imkb100;
import com.veripark.imkb.model.Imkb30;
import com.veripark.imkb.model.Imkb50;
import com.veripark.imkb.model.RequestResult;
import com.veripark.imkb.model.Stock;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

/**
 * Created by Kemal on 2/22/16.
 */
@Root(strict = false)
public class GetForexStocksandIndexesResponse {
    @Path("Body/GetForexStocksandIndexesInfoResponse/GetForexStocksandIndexesInfoResult")
    @ElementList(name = "StocknIndexesResponseList")
    private ArrayList<Stock> stockList;

    @Path("Body/GetForexStocksandIndexesInfoResponse/GetForexStocksandIndexesInfoResult")
    @ElementList(name = "IMKB100List", required = false)
    private ArrayList<Imkb100> imkb100List;

    @Path("Body/GetForexStocksandIndexesInfoResponse/GetForexStocksandIndexesInfoResult")
    @ElementList(name = "IMKB50List", required = false)
    private ArrayList<Imkb50> imkb50List;

    @Path("Body/GetForexStocksandIndexesInfoResponse/GetForexStocksandIndexesInfoResult")
    @ElementList(name = "IMKB30List", required = false)
    private ArrayList<Imkb30> imkb30List;

    @Path("Body/GetForexStocksandIndexesInfoResponse/GetForexStocksandIndexesInfoResult")
    @ElementList(name = "StocknIndexesGraphicInfos", required = false)
    private ArrayList<GraphData> graphDataList;

    @Path("Body/GetForexStocksandIndexesInfoResponse/GetForexStocksandIndexesInfoResult")
    @Element(name = "RequestResult")
    private RequestResult requestResult;

    public GetForexStocksandIndexesResponse() {
    }


    public ArrayList<Stock> getStockList() {
        return stockList;
    }

    public void setStockList(ArrayList<Stock> stockList) {
        this.stockList = stockList;
    }

    public ArrayList<Imkb100> getImkb100List() {
        return imkb100List;
    }

    public void setImkb100List(ArrayList<Imkb100> imkb100List) {
        this.imkb100List = imkb100List;
    }

    public ArrayList<Imkb50> getImkb50List() {
        return imkb50List;
    }

    public void setImkb50List(ArrayList<Imkb50> imkb50List) {
        this.imkb50List = imkb50List;
    }

    public ArrayList<Imkb30> getImkb30List() {
        return imkb30List;
    }

    public void setImkb30List(ArrayList<Imkb30> imkb30List) {
        this.imkb30List = imkb30List;
    }

    public ArrayList<GraphData> getGraphDataList() {
        return graphDataList;
    }

    public void setGraphDataList(ArrayList<GraphData> graphDataList) {
        this.graphDataList = graphDataList;
    }

    public RequestResult getRequestResult() {
        return requestResult;
    }

    public void setRequestResult(RequestResult requestResult) {
        this.requestResult = requestResult;
    }
}
