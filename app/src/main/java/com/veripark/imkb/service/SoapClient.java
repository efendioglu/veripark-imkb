package com.veripark.imkb.service;

import com.veripark.imkb.request.Encrypt;
import com.veripark.imkb.request.GetForexStocksandIndexesInfo;
import com.veripark.imkb.response.EncryptResponse;
import com.veripark.imkb.response.GetForexStocksandIndexesResponse;


import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by Kemal on 2/22/16.
 */
public class SoapClient {

    private SoapApi soapApi;

    private static SoapClient soapClient;

    private final String serviceUrl = "http://mobileexam.veripark.com/mobileforeks/";

    public static SoapClient instance(){
        if(soapClient == null)
            soapClient = new SoapClient();

        return soapClient;
    }

    private SoapClient(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .addNetworkInterceptor(new HeaderInterceptor())
                .addInterceptor(logging)
                .build();

        Strategy strategy = new AnnotationStrategy();
        Serializer serializer =  new Persister(strategy);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(serviceUrl)
                .client(client)
                .addConverterFactory(SimpleXmlConverterFactory.create(serializer))
                .build();


        soapApi = retrofit.create(SoapApi.class);
    }

    public SoapApi getSoapApi() {
        return soapApi;
    }

    public void getEncryptedRequestKey(Encrypt request, final OnResponseListener onResponseListener){
        final Call<EncryptResponse> res = soapApi.getRequestKey(new Envelope("", request));

        res.enqueue(new Callback<EncryptResponse>() {
            @Override
            public void onResponse(Call<EncryptResponse> call, Response<EncryptResponse> response) {
                if(onResponseListener != null)
                    onResponseListener.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<EncryptResponse> call, Throwable t) {
                if(onResponseListener != null)
                    onResponseListener.onError(t);
            }
        });
    }

    public void getStockList(GetForexStocksandIndexesInfo request, final OnResponseListener onResponseListener){
        final Call<GetForexStocksandIndexesResponse> res = soapApi.getStockList(new Envelope("", request));

        res.enqueue(new Callback<GetForexStocksandIndexesResponse>() {
            @Override
            public void onResponse(Call<GetForexStocksandIndexesResponse> call, Response<GetForexStocksandIndexesResponse> response) {
                if(onResponseListener != null)
                    onResponseListener.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<GetForexStocksandIndexesResponse> call, Throwable t) {
                if(onResponseListener != null)
                    onResponseListener.onError(t);
            }
        });
    }
}
