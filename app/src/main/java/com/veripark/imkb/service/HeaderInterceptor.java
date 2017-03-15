package com.veripark.imkb.service;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by santalu on 12/15/15.
 */
public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request().newBuilder()
                .header("Content-Type", "application/soap+xml; charset=UTF-8")
                .build();
        return chain.proceed(request);
    }
}
