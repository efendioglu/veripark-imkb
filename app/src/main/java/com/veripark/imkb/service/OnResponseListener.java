package com.veripark.imkb.service;

/**
 * Created by Kemal on 2/22/16.
 */
public interface OnResponseListener {
    <T> void onResponse(T response);

    void onError(Throwable t);
}
