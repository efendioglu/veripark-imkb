package com.veripark.imkb.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Root;

/**
 * Created by Kemal on 2/22/16.
 */
@Root
public class GetForexStocksandIndexesInfo {

    @Element(name = "tem:GetForexStocksandIndexesInfo")
    private StockRequest stockRequest;

    public GetForexStocksandIndexesInfo(String requestKey) {
        stockRequest = new StockRequest(requestKey);
    }

    public GetForexStocksandIndexesInfo(String requestKey, String symbol, String period) {
        stockRequest = new StockRequest(requestKey, symbol, period);
    }

    @Root
    static class StockRequest{

        @Element(name = "tem:request")
        private Request request;

        public StockRequest(String requestKey) {
            request = new Request(false, "test", "android", requestKey, "Day");
        }

        public StockRequest(String requestKey, String symbol, String period) {
            request = new Request(requestKey, symbol, period);
        }

        @Root
        //@Order(elements = {"tem:IsIPAD", "tem:DeviceID", "tem:DeviceType", "tem:RequestKey", "tem:Period"})
        static class Request{
            @Element(name = "tem:IsIPAD")
            private boolean isIpad;

            @Element(name = "tem:DeviceID")
            private String deviceId;

            @Element(name = "tem:DeviceType")
            private String deviceType;

            @Element(name = "tem:RequestedSymbol", required = false)
            private String requestedSymbol;

            @Element(name = "tem:RequestKey")
            private String requestKey;

            @Element(name = "tem:Period")
            private String period;

            public Request(boolean isIpad, String deviceId, String deviceType, String requestKey, String period) {
                this.isIpad = isIpad;
                this.deviceId = deviceId;
                this.deviceType = deviceType;
                this.requestKey = requestKey;
                this.period = period;
            }

            public Request(String requestKey, String symbol, String period) {
                this.isIpad = false;
                this.deviceId = "test";
                this.deviceType = "android";
                this.requestKey = requestKey;
                this.requestedSymbol = symbol;
                this.period = period;
            }

            public boolean isIpad() {
                return isIpad;
            }

            public void setIpad(boolean ipad) {
                isIpad = ipad;
            }

            public String getDeviceId() {
                return deviceId;
            }

            public void setDeviceId(String deviceId) {
                this.deviceId = deviceId;
            }

            public String getDeviceType() {
                return deviceType;
            }

            public void setDeviceType(String deviceType) {
                this.deviceType = deviceType;
            }

            public String getRequestKey() {
                return requestKey;
            }

            public void setRequestKey(String requestKey) {
                this.requestKey = requestKey;
            }

            public String getPeriod() {
                return period;
            }

            public void setPeriod(String period) {
                this.period = period;
            }

            public String getRequestedSymbol() {
                return requestedSymbol;
            }

            public void setRequestedSymbol(String requestedSymbol) {
                this.requestedSymbol = requestedSymbol;
            }
        }
    }


}
