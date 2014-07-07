package com.learning.cxf.jms.transport;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.transport.jms.JMSConfigFeature;
import org.apache.cxf.transport.jms.JMSConfiguration;

public class Server {
    public static void main(String... args) {
        //basic setting on service
        JaxWsServerFactoryBean jwfb = new JaxWsServerFactoryBean();
        jwfb.setServiceClass(OrderProcess.class);
        jwfb.setServiceBean(new OrderProcessImpl());
        jwfb.setAddress("jms://"); //specify jms transport

        //create jms connection factory
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL("tcp://localhost:61616");
        
        //set target destination queue
        JMSConfiguration jmsConfig = new JMSConfiguration();
        jmsConfig.setTargetDestination("cxf.orderprocess.queue");
        jmsConfig.setConnectionFactory(connectionFactory);
        
        //add feature
        JMSConfigFeature jmsFeature = new JMSConfigFeature();
        jmsFeature.setJmsConfig(jmsConfig);
        jwfb.getFeatures().add(jmsFeature);
        
        //create
        jwfb.create();
    }
}
