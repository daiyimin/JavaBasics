package com.learning.cxf.provider;

import java.io.IOException;
import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.dom.DOMSource;
import javax.xml.ws.Provider;
import javax.xml.ws.Service;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceProvider;
import org.w3c.dom.Node;

/**
 * ����ӷ�������������ʾ����Messageģʽ��Provider���������Ϣ��Ϊ����������
 * 
 * @author Administrator
 * 
 */
@WebServiceProvider()
@ServiceMode(value = Service.Mode.MESSAGE)
public class CalcPlusServiceProvider implements Provider<DOMSource> {
	/**
	 * ����������ڶ�����δ���DOMSource��XML��Ϣ���߼������ҹ�����Ӧ��Ϣ
	 */
	@SuppressWarnings("restriction")
	public DOMSource invoke(DOMSource request) {
		try {
			// �ȹ��� һ��SOAPMessage�����ڷ��������SOAP��Ϣ
			MessageFactory factory = MessageFactory.newInstance();
			SOAPMessage soapRequestMsg = factory.createMessage();
			// ע�⣬��Ϊ���ǵĴ����ǰ���Ϣ��Ϊ���崦�����Է������soapPart��������soapBody
			soapRequestMsg.getSOAPPart().setContent(request);

			// ��ӡ���ͻ�������������Ϣ������̨
			System.out.println("�ӿͻ�������������ϢΪ:");
			soapRequestMsg.writeTo(System.out);
			System.out.println();

			// �������Ǵ�������Ϣ�з����������Ҫ����Ϣ
			SOAPBody soapBody = soapRequestMsg.getSOAPBody();

			Node calcSumNode = soapBody.getFirstChild();

			// ���Ҫ���ӷ��������
			Node aNode = calcSumNode.getChildNodes().item(0);
			int a = Integer.parseInt(aNode.getTextContent());
			Node bNode = calcSumNode.getChildNodes().item(1);
			int b = Integer.parseInt(bNode.getTextContent());

			// ����ӷ�
			String sum = String.valueOf(a + b);

			// ��װ�������Ӧ������
			SOAPMessage soapResponseMsg = factory.createMessage();

			// ����<calcSumResponse>Ԫ�أ�����namespaceΪ"http://services.server.cxfstudy.charles.com",ע�����Ԫ����SOAPMessage��<soap:Body>����
			QName calcSumResponseQName = new QName(
					"http://com.learning.cxf.dispatch",
					"calcSumResponse");
			SOAPElement calcSumResponseEle = soapResponseMsg.getSOAPBody()
					.addChildElement(calcSumResponseQName);
			calcSumResponseEle.addChildElement("sum").addTextNode(sum);

			// ��ӡ�������ص��ͻ��˵���Ӧ��Ϣ������̨
			System.out.println("Ҫ���͵��ͻ��˵���ϢΪ:");
			soapResponseMsg.writeTo(System.out);
			System.out.println();

			// ��SOAPMessageתΪDOMSource����
			DOMSource response = new DOMSource(soapResponseMsg.getSOAPPart());
			return response;

		} catch (SOAPException ex) {
			ex.printStackTrace();
			return null;
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}

	}
}