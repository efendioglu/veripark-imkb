package com.veripark.imkb.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

/**
 * Created by Kemal on 2/22/16.
 */
@Root(name = "EncryptResponse", strict = false)
@Namespace(reference = "http://tempuri.org/")
public class EncryptResponse {
    @Path("Body/EncryptResponse")
    @Element(name = "EncryptResult")
    private String requestKey;

    public EncryptResponse() {
    }

    public void setRequestKey(String requestKey) {
        this.requestKey = requestKey;
    }

    public String getRequestKey() {
        return requestKey;
    }
}
