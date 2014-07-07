
package org.apache.hello_world_async_soap_http;

import javax.xml.ws.Endpoint;

/**
 * This class was generated by Apache CXF 2.7.10
 * 2014-03-30T10:26:18.495+08:00
 * Generated source version: 2.7.10
 * 
 */
 
public class GreeterAsync_SoapPort_Server{

    protected GreeterAsync_SoapPort_Server() throws java.lang.Exception {
        System.out.println("Starting Server");
        Object implementor = new GreeterAsyncImpl();
        String address = "http://localhost:9000/SoapContext/SoapPort";
        Endpoint.publish(address, implementor);
    }
    
    public static void main(String args[]) throws java.lang.Exception { 
        new GreeterAsync_SoapPort_Server();
        System.out.println("Server ready..."); 
        
        Thread.sleep(5 * 60 * 1000); 
        System.out.println("Server exiting");
        System.exit(0);
    }
}
