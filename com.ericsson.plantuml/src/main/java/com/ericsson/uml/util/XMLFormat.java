package com.ericsson.uml.util;

import java.io.IOException;  
import java.io.StringReader;  
import java.io.StringWriter;  
  


import org.dom4j.Document;  
import org.dom4j.io.OutputFormat;  
import org.dom4j.io.SAXReader;  
import org.dom4j.io.XMLWriter;

public class XMLFormat {
    public static String formatXML(String inputXML) throws Exception {  
        String requestXML = null;  
    	try {
            SAXReader reader = new SAXReader();  
            Document document = reader.read(new StringReader(inputXML));  
            XMLWriter writer = null;  
            if (document != null) {  
              try {  
                StringWriter stringWriter = new StringWriter();  
                OutputFormat format = new OutputFormat(" ", true);  
                writer = new XMLWriter(stringWriter, format);  
                writer.write(document);  
                writer.flush();  
                requestXML = stringWriter.getBuffer().toString();  
              } finally {  
                if (writer != null) {  
                  try {  
                    writer.close();  
                  } catch (IOException e) {  
                  }  
                }  
              }  
            }  
    	} catch (Exception e) {
    		System.out.println("Input XML is:");
    		System.out.println(inputXML);
    		throw e;
    	}
        return requestXML;  
      }
    
    public static void main(String[] args) throws Exception {
        String longXMLString = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cai3=\"http://schemas.ericsson.com/cai3g1.2/\" xmlns:ns=\"http://schemas.ericsson.com/pg/hlr/13.5/\"><soapenv:Header><cai3:SessionId xmlns:cai3=\"http://schemas.ericsson.com/cai3g1.2/\" xmlns:ns=\"http://schemas.ericsson.com/pg/hlr/13.5/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">6f133e1cf7944392bdbd8b2b35be67b4</cai3:SessionId></soapenv:Header><soapenv:Body>\n      <cai3:Set xmlns:cai3=\"http://schemas.ericsson.com/cai3g1.2/\" xmlns:ns=\"http://schemas.ericsson.com/pg/hlr/13.5/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xml=\"http://www.w3.org/XML/1998/namespace\">\n         <cai3:MOType>SubscriberData@http://schemas.ericsson.com/pg/hlr/13.5/</cai3:MOType>\n         <cai3:MOId>\n            <ns:msisdn>494601000007</ns:msisdn>\n         </cai3:MOId>\n         <cai3:MOAttributes>\n            <ns:SetSubscriberData msisdn=\"494601000007\">\n               <ns:sud>PWD-******</ns:sud>\n            </ns:SetSubscriberData>\n         </cai3:MOAttributes>\n      </cai3:Set></soapenv:Body></soapenv:Envelope>";
        XMLFormat formatter = new XMLFormat();
    	String out = formatter.formatXML(longXMLString);
    	System.out.println(out);
    }

}
