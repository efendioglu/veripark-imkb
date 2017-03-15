package com.veripark.imkb.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Root;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kemal on 2/22/16.
 */
@Root
public class Encrypt {

    @Element(name = "tem:Encrypt")
    private Request request;

    public Encrypt() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd:MM:yyyy HH:mm");
        request = new Request("RequestIsValid" + sdf.format(new Date()));
    }

    @Root
    static class Request{
        @Element(name = "tem:request")
        private String request;


        public Request(String request) {
            this.request = request;
        }

        public String getRequest() {
            return request;
        }

        public void setRequest(String request) {
            this.request = request;
        }
    }
}
