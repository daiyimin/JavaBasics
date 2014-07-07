
package com.learning.cxf.client;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import org.apache.hello_world_soap_http.Greeter;
import org.apache.hello_world_soap_http.PingMeFault;
import org.apache.hello_world_soap_http.SOAPService;

/**
 * How to create client stub code
 * >c:\Temp\apache-cxf-2.7.10\bin\wsdl2java -ant -client -d wsdl2java_output -b a
sync_binding.xml -frontend jaxws21 hello_world_async.wsdl
 * 
 * How to invoke web service
 * 1) Copy hello_world.wsdl to application root directory
 * OR add an cmd line argument to specify wsdl location 
 * 2) Modiy hello_world.wsdl
 * Modify soap:address in wsdl:port, change soap:address to the real Soap web service endpoint
 * http://localhost:8080/com.learning.cxf.wf.server/services/GreeterImpl
 */
public final class Greeter_SoapPort_Client {

    private static final QName SERVICE_NAME = new QName("http://apache.org/hello_world_soap_http", "SOAPService");

    private Greeter_SoapPort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = SOAPService.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        SOAPService ss = new SOAPService(wsdlURL, SERVICE_NAME);
        Greeter port = ss.getSoapPort();  
        
        {
        System.out.println("Invoking pingMe...");
        try {
            port.pingMe();

        } catch (PingMeFault e) { 
            System.out.println("Expected exception: pingMeFault has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking sayHi...");
        java.lang.String _sayHi__return = port.sayHi();
        System.out.println("sayHi.result=" + _sayHi__return);


        }
        {
        System.out.println("Invoking greetMeOneWay...");
        java.lang.String _greetMeOneWay_requestType = "";
        port.greetMeOneWay(_greetMeOneWay_requestType);


        }
        {
        System.out.println("Invoking greetMe...");
        java.lang.String _greetMe_requestType = "";
        java.lang.String _greetMe__return = port.greetMe(_greetMe_requestType);
        System.out.println("greetMe.result=" + _greetMe__return);


        }

        System.exit(0);
    }

}
