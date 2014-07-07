package com.learning.cxf.dispatch;

import java.io.IOException;

import org.junit.Test;
import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;

public class TestSoapMessage {
    private String ns = "http://www.example.org/MyService/";
     
    @Test
    public void testMessage(){
        //1��������Ϣ����
        try {
            MessageFactory factory=MessageFactory.newInstance();
            //2��������Ϣ��������SOAPMessage
             
            SOAPMessage messge=factory.createMessage();
            //3,����SOAPPart
            SOAPPart part=messge.getSOAPPart() ;
            //4����ȡ�ŷ�
            SOAPEnvelope envelop=part.getEnvelope() ;
            //5����ȡ��Ϣ���ⲿ��
            SOAPBody body=envelop.getBody();
             
            //6��������Ϣ
            QName qname=new QName(ns,"add","ns");
            SOAPElement ele=body.addBodyElement(qname);
            ele.addChildElement("number1").setValue("1");
            ele.addChildElement("number2").setValue("2");
             
            messge.writeTo(System.out);
        } catch (SOAPException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
