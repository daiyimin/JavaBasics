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
 * 这个加法运算类用于演示基于Message模式的Provider，它会把消息作为整体来处理
 * 
 * @author Administrator
 * 
 */
@WebServiceProvider()
@ServiceMode(value = Service.Mode.MESSAGE)
public class CalcPlusServiceProvider implements Provider<DOMSource> {
	/**
	 * 这个方法用于定义如何处理DOMSource的XML消息的逻辑，并且构造响应消息
	 */
	@SuppressWarnings("restriction")
	public DOMSource invoke(DOMSource request) {
		try {
			// 先构造 一个SOAPMessage，用于放入请求的SOAP消息
			MessageFactory factory = MessageFactory.newInstance();
			SOAPMessage soapRequestMsg = factory.createMessage();
			// 注意，因为我们的代码是吧消息作为整体处理，所以放入的是soapPart，而不是soapBody
			soapRequestMsg.getSOAPPart().setContent(request);

			// 打印到客户端请求来的消息到控制台
			System.out.println("从客户端请求来的消息为:");
			soapRequestMsg.writeTo(System.out);
			System.out.println();

			// 现在我们从请求消息中分离出我们所要的信息
			SOAPBody soapBody = soapRequestMsg.getSOAPBody();

			Node calcSumNode = soapBody.getFirstChild();

			// 获得要做加法运算的数
			Node aNode = calcSumNode.getChildNodes().item(0);
			int a = Integer.parseInt(aNode.getTextContent());
			Node bNode = calcSumNode.getChildNodes().item(1);
			int b = Integer.parseInt(bNode.getTextContent());

			// 计算加法
			String sum = String.valueOf(a + b);

			// 封装结果到响应对象中
			SOAPMessage soapResponseMsg = factory.createMessage();

			// 构造<calcSumResponse>元素，它的namespace为"http://services.server.cxfstudy.charles.com",注意这个元素在SOAPMessage的<soap:Body>部分
			QName calcSumResponseQName = new QName(
					"http://com.learning.cxf.dispatch",
					"calcSumResponse");
			SOAPElement calcSumResponseEle = soapResponseMsg.getSOAPBody()
					.addChildElement(calcSumResponseQName);
			calcSumResponseEle.addChildElement("sum").addTextNode(sum);

			// 打印即将返回到客户端的响应消息到控制台
			System.out.println("要发送到客户端的消息为:");
			soapResponseMsg.writeTo(System.out);
			System.out.println();

			// 把SOAPMessage转为DOMSource类型
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