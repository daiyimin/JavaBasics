package com.learning.cxf.jms.transport;

public class OrderProcessImpl implements OrderProcess {

    @Override
    public String processOrder(Order order) {
        System.out.println("Processing order " + order.getId());
        return order.getId() + " processed";
    }

}
