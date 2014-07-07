package com.learning.cxf.client;

import org.apache.hello_world_soap_http.Greeter;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * JaxwsProxyFactoryBean still require the service class and type classes
 * 
 * Same function as JaxWsProxyFactoryBeanClient1, just try client creation with spring IOC
 * @author eyimdai
 */
public class JaxWsProxyFactoryBeanClient2 {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = 
            new ClassPathXmlApplicationContext(new String[] { "client-beans-b.xml" });

        Greeter client = (Greeter) context.getBean("client");

        String response = client.greetMe("Yimin");
        System.out.println("Response: " + response);
        System.exit(0);
    }
}
