package com.veripark.imkb.service;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;

/**
 * Created by Kemal on 2/23/16.
 */
@Root(name = "soap:Envelope")
@NamespaceList(
        {
                @Namespace(reference = "http://www.w3.org/2003/05/soap-envelope", prefix = "soap"),
                @Namespace(reference = "http://tempuri.org/", prefix = "tem"),
        }
)
public class Envelope {
    @Element(name = "soap:Header", required = false)
    private String header;

    @Element(name = "soap:Body")
    private Object requestBody;

    public Envelope(String header, Object requestBody) {
        this.header = header;
        this.requestBody = requestBody;
    }

    public Object getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(Object requestBody) {
        this.requestBody = requestBody;
    }
}
