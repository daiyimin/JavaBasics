package com.learning.cxf.dispatch.provider;

import java.io.IOException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.ws.*;
import javax.xml.soap.*;
import static javax.xml.ws.Service.Mode;
import javax.xml.namespace.QName;
import javax.annotation.Resource;
import javax.xml.XMLConstants;

@WebServiceProvider(wsdlLocation = "dp.wsdl",
        targetNamespace = "http://simple.server.cxf.learning.com",
        serviceName = "CourseManager") //specify wsdl path
@ServiceMode(value = Mode.MESSAGE) //specify service mode
public class OrderProcessDomProvider implements Provider<DOMSource> {

    @Resource
    WebServiceContext context; //inject context
    
    public DOMSource invoke(DOMSource request) {
        try {
            
//            System.out.println(context.getMessageContext().get(Message.WSDL_OPERATION)); //get request operation
            
            //get request
            MessageFactory factory = MessageFactory.newInstance();
            SOAPMessage message = factory.createMessage();
            message.getSOAPPart().setContent(request);
            message.writeTo(System.out);
            
            //construct response
            SOAPMessage response = factory.createMessage();
            SOAPElement bodyElement = response.getSOAPBody().addChildElement(new QName("http://simple.server.cxf.learning.com", "addCourseResponse",  XMLConstants.DEFAULT_NS_PREFIX));
            SOAPElement outElement = bodyElement.addChildElement("result");
            outElement.setTextContent("output");
            
            DOMSource domSource = new DOMSource();
            domSource.setNode(response.getSOAPPart());
            return domSource;
            
        } catch (SOAPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}