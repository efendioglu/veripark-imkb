package com.veripark.imkb.service;

import com.veripark.imkb.response.EncryptResponse;
import com.veripark.imkb.response.GetForexStocksandIndexesResponse;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Kemal on 2/22/16.
 */
public interface SoapApi {
    @POST("service.asmx")
    Call<EncryptResponse> getRequestKey(@Body Envelope request);

    @POST("service.asmx")
    Call<GetForexStocksandIndexesResponse> getStockList(@Body Envelope request);
}
