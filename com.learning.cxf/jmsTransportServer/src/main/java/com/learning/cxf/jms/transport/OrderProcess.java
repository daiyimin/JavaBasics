package com.learning.cxf.jms.transport;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(targetNamespace="http://localhost:8080/cxf/jms")
public interface OrderProcess {

    @WebMethod(operationName="processOrder")
    public String processOrder( @WebParam(name="order") Order order);
    
}