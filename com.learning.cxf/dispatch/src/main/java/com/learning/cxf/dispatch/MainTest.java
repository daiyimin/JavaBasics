package com.learning.cxf.dispatch;

import javax.xml.namespace.QName;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPElement;

public class MainTest {

	public static SOAPMessage buildMessageForAdd(MessageFactory factory)
			throws SOAPException {

		SOAPMessage soapRequest = factory.createMessage();

		// 构造<calcSum>元素，它的namespace为"http://services.server.cxfstudy.charles.com",注意这个元素在SOAPMessage的<soap:Body>部分
		QName calcSumQName = new QName(
				"http://provider.cxf.learning.com/", "calcSum");
		SOAPElement calcSumEle = soapRequest.getSOAPBody().addChildElement(
				calcSumQName);
		// 在<calcSum>元素中添加2个子元素，一个为<a>3</a>,一个为<b>5</b>
		calcSumEle.addChildElement("a").addTextNode("3");
		calcSumEle.addChildElement("b").addTextNode("5");

		return soapRequest;

	}

	public static void main(String[] args) throws Exception {

		String wsdlURLStringForCalcPlus = "http://localhost:9000/calcPlus?wsdl";
		String serviceQName = "http://provider.cxf.learning.com/";
		String serviceProviderStringForCalcPlus = "CalcPlusServiceProviderService";
		String servicePortStringForCalcPlus = "CalcPlusServiceProviderPort";

		// 构造要发送的Soap消息内容并且转为Source类型
		// 从MessageFactory 构造一个要发送的Soap消息
		MessageFactory factory = MessageFactory.newInstance();

		SOAPMessage soapRequest = buildMessageForAdd(factory);
		System.out.println("发送的消息为:");
		soapRequest.writeTo(System.out);
		System.out.println();

		SOAPMessage soapResponse = DispatcherUtil.sendMessage(
				wsdlURLStringForCalcPlus, serviceQName,
				serviceProviderStringForCalcPlus, servicePortStringForCalcPlus,
				soapRequest, factory);
		System.out.println("响应的消息为：");
		soapResponse.writeTo(System.out);

	}
}