package com.learning.cxf.provider;

import javax.xml.ws.Endpoint;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Server {
    public static void main(String[] args) {
    	CalcPlusServiceProvider provider = new CalcPlusServiceProvider();
    	Endpoint.publish("http://localhost:9000/calcPlus", provider);
    }
}
