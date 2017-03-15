package com.veripark.imkb.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Kemal on 8/30/16.
 */
@Root(name = "RequestResult")
public class RequestResult {
    @Element(name = "Success", required = false)
    private boolean isSuccess;

    @Element(name = "Message", required = false)
    private String message;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
