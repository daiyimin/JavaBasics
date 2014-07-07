package com.learning.cxf.client;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.hello_world_soap_http.Greeter;

/**
 * JaxwsProxyFactoryBean still require the service class and type classes
 * 
 * @author eyimdai
 */
public class JaxWsProxyFactoryBeanClient1 {
    public static void main(String[] args) {
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(Greeter.class);

        factory.setAddress("http://localhost:8080/com.learning.cxf.wf.server/services/GreeterImpl");
        Greeter service = (Greeter) factory.create();

        service.sayHi();

    }
}
