package org.apache.hello_world_async_soap_http;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import java.util.concurrent.Future;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.AsyncHandler;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.Response;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.10 2014-03-29T17:34:00.876+08:00 Generated source version: 2.7.10
 * 
 */
public final class GreeterAsync_SoapPort_Client {

    private static final QName SERVICE_NAME = new QName("http://apache.org/hello_world_async_soap_http", "SOAPService");

    private GreeterAsync_SoapPort_Client() {
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
        GreeterAsync port = ss.getSoapPort();

        // Response extends Future, so its get() method will block till async method is returned
        {
            System.out.println("Invoking greetMeSometimeAsync...");
            java.lang.String _greetMeSometimeAsync_requestType = "Yimin";
            Response<org.apache.hello_world_async_soap_http.types.GreetMeSometimeResponse> _greetMeSometimeAsync__return = port
                    .greetMeSometimeAsync(_greetMeSometimeAsync_requestType);
//            System.out.println("greetMeSometimeAsync.result=" + _greetMeSometimeAsync__return);
            System.out.println("greetMeSometimeAsync.result=" + _greetMeSometimeAsync__return.get().getResponseType());
        }
        
        {
            System.out.println("Invoking greetMeSometimeAsync...");
            java.lang.String _greetMeSometimeAsync_requestType = "Yimin";
            AsyncHandler<org.apache.hello_world_async_soap_http.types.GreetMeSometimeResponse> _greetMeSometimeAsync_asyncHandler = null;
            Future<?> _greetMeSometimeAsync__return = port.greetMeSometimeAsync(_greetMeSometimeAsync_requestType, _greetMeSometimeAsync_asyncHandler);
            org.apache.hello_world_async_soap_http.types.GreetMeSometimeResponse returnVal = 
                (org.apache.hello_world_async_soap_http.types.GreetMeSometimeResponse)_greetMeSometimeAsync__return.get();
            System.out.println("greetMeSometimeAsync.result=" + returnVal.getResponseType());

        }
        {
            System.out.println("Invoking greetMeSometime...");
            java.lang.String _greetMeSometime_requestType = "Yimin";
            java.lang.String _greetMeSometime__return = port.greetMeSometime(_greetMeSometime_requestType);
            System.out.println("greetMeSometime.result=" + _greetMeSometime__return);

        }

        System.exit(0);
    }

}
