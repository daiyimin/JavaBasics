package com.learning.cxf.jms.transport.client;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.jms.JMSConfigFeature;
import org.apache.cxf.transport.jms.JMSConfiguration;

import com.learning.cxf.jms.transport.Order;
import com.learning.cxf.jms.transport.OrderProcess;

public class Client {

    /**
     * @param args
     */
    public static void main(String[] args) {
        //create jms connection and configuration
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL("tcp://localhost:61616");

        JMSConfiguration jmsConfig = new JMSConfiguration();
        jmsConfig.setTargetDestination("cxf.orderprocess.queue");
        jmsConfig.setConnectionFactory(connectionFactory);

        //create feature and config it with jms configuration
        JMSConfigFeature jmsFeature = new JMSConfigFeature();
        jmsFeature.setJmsConfig(jmsConfig);

        //create client, and add the feature
        JaxWsProxyFactoryBean client = new JaxWsProxyFactoryBean();
        client.setAddress("jms://"); //specify tranport type
        client.getFeatures().add(jmsFeature);
        
        //call service
        OrderProcess orderProcess = client.create(OrderProcess.class);
        Order order = new Order("1");
        String s = orderProcess.processOrder(order);
        System.out.println(s);
        
        order = new Order("2");
        s = orderProcess.processOrder(order);
        System.out.println(s);

        
    }

}
