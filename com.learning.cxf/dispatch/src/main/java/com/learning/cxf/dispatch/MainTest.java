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

		// ����<calcSum>Ԫ�أ�����namespaceΪ"http://services.server.cxfstudy.charles.com",ע�����Ԫ����SOAPMessage��<soap:Body>����
		QName calcSumQName = new QName(
				"http://provider.cxf.learning.com/", "calcSum");
		SOAPElement calcSumEle = soapRequest.getSOAPBody().addChildElement(
				calcSumQName);
		// ��<calcSum>Ԫ�������2����Ԫ�أ�һ��Ϊ<a>3</a>,һ��Ϊ<b>5</b>
		calcSumEle.addChildElement("a").addTextNode("3");
		calcSumEle.addChildElement("b").addTextNode("5");

		return soapRequest;

	}

	public static void main(String[] args) throws Exception {

		String wsdlURLStringForCalcPlus = "http://localhost:9000/calcPlus?wsdl";
		String serviceQName = "http://provider.cxf.learning.com/";
		String serviceProviderStringForCalcPlus = "CalcPlusServiceProviderService";
		String servicePortStringForCalcPlus = "CalcPlusServiceProviderPort";

		// ����Ҫ���͵�Soap��Ϣ���ݲ���תΪSource����
		// ��MessageFactory ����һ��Ҫ���͵�Soap��Ϣ
		MessageFactory factory = MessageFactory.newInstance();

		SOAPMessage soapRequest = buildMessageForAdd(factory);
		System.out.println("���͵���ϢΪ:");
		soapRequest.writeTo(System.out);
		System.out.println();

		SOAPMessage soapResponse = DispatcherUtil.sendMessage(
				wsdlURLStringForCalcPlus, serviceQName,
				serviceProviderStringForCalcPlus, servicePortStringForCalcPlus,
				soapRequest, factory);
		System.out.println("��Ӧ����ϢΪ��");
		soapResponse.writeTo(System.out);

	}
}