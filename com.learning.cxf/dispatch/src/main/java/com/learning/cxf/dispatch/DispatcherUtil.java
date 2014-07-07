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
 * ���������ڷ��ͺͽ�����Ϣ
 * 
 * @author Administrator
 * 
 */
public class DispatcherUtil {

	/**
	 * ��ָ����SOAP��Ϣ���͵�ָ����endpoint�ϣ����Ҹ������ص�SOAP��Ϣ
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
		// ��SOAPMessageתΪSource����
		DOMSource requestMsg = new DOMSource(soapRequest.getSOAPPart());

		URL wsdlURL = new URL(wsdlURLString);

		// ����һ��Service����
		QName serviceProvider = new QName(serviceQName,
				serviceProviderServiceName);
		QName portName = new QName(serviceQName, serviceProviderPortName);
		Service service = Service.create(wsdlURL, serviceProvider);

		// ����Service����������(Dispatch) Source���͵�SOAPMessage��ָ����Port��
		Dispatch<DOMSource> domMsg = service.createDispatch(portName,
				DOMSource.class, Mode.MESSAGE);
		// �����Ӧ��Ϣ
		DOMSource respMsg = domMsg.invoke(requestMsg);
		SOAPMessage soapResponse = factory.createMessage();
		soapResponse.getSOAPPart().setContent(respMsg);

		return soapResponse;

	}
}