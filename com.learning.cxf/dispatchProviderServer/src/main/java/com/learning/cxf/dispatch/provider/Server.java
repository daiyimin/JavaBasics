package com.learning.cxf.dispatch.provider;
import javax.xml.ws.Endpoint;

public class Server {
    public static void main(String...strings ) {
        Endpoint.publish("http://localhost:8090/orderProcessProvider",  
                new OrderProcessDomProvider());  
    }
}
