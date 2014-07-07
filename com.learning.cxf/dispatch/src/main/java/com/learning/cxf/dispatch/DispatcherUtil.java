package com.learning.cxf.dispatch;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.dom.DOMSource;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.Service.Mode;

/**
 * 工具类用于发送和接收消息
 * 
 * @author Administrator
 * 
 */
public class DispatcherUtil {

	/**
	 * 把指定的SOAP消息发送到指定的endpoint上，并且给出返回的SOAP消息
	 * 
	 * @param wsdlURLString
	 * @param serviceQName
	 * @param serviceProviderServiceName
	 * @param serviceProviderPortName
	 * @param soapRequest
	 * @param factory
	 * @return
	 * @throws MalformedURLException
	 * @throws SOAPException
	 */
	@SuppressWarnings("restriction")
	public static SOAPMessage sendMessage(String wsdlURLString,
			String serviceQName, String serviceProviderServiceName,
			String serviceProviderPortName, SOAPMessage soapRequest,
			MessageFactory factory) throws MalformedURLException, SOAPException {
		// 把SOAPMessage转为Source类型
		DOMSource requestMsg = new DOMSource(soapRequest.getSOAPPart());

		URL wsdlURL = new URL(wsdlURLString);

		// 构造一个Service对象
		QName serviceProvider = new QName(serviceQName,
				serviceProviderServiceName);
		QName portName = new QName(serviceQName, serviceProviderPortName);
		Service service = Service.create(wsdlURL, serviceProvider);

		// 利用Service对象来发送(Dispatch) Source类型的SOAPMessage到指定的Port上
		Dispatch<DOMSource> domMsg = service.createDispatch(portName,
				DOMSource.class, Mode.MESSAGE);
		// 获得响应消息
		DOMSource respMsg = domMsg.invoke(requestMsg);
		SOAPMessage soapResponse = factory.createMessage();
		soapResponse.getSOAPPart().setContent(respMsg);

		return soapResponse;

	}
}