package com.learning.cxf.restful;

import org.apache.cxf.interceptor.LoggingInInterceptor;  
import org.apache.cxf.interceptor.LoggingOutInterceptor;  
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;  

/**
 * http://localhost:9000/ws/jaxrs/getList/1
 * 
 * @author eyimdai
 */
public class MyServer {  
    public static void main(String[] args) throws Exception {  
        JAXRSServerFactoryBean factoryBean = new JAXRSServerFactoryBean();  
        factoryBean.getInInterceptors().add(new LoggingInInterceptor());  
        factoryBean.getOutInterceptors().add(new LoggingOutInterceptor());  
        factoryBean.setResourceClasses(Rest_HelloWorldImpl.class);  
        factoryBean.setAddress("http://localhost:9000/ws/jaxrs");  
        factoryBean.create();  
    }  
}  