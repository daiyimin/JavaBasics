
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package org.apache.hello_world_async_soap_http;

import java.util.logging.Logger;
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
 * This class was generated by Apache CXF 2.7.10
 * 2014-03-30T10:26:18.433+08:00
 * Generated source version: 2.7.10
 * 
 */

@javax.jws.WebService(
                      serviceName = "SOAPService",
                      portName = "SoapPort",
                      targetNamespace = "http://apache.org/hello_world_async_soap_http",
                      wsdlLocation = "hello_world_async.wsdl",
                      endpointInterface = "org.apache.hello_world_async_soap_http.GreeterAsync")
                      
public class GreeterAsyncImpl implements GreeterAsync {

    private static final Logger LOG = Logger.getLogger(GreeterAsyncImpl.class.getName());

    /* (non-Javadoc)
     * @see org.apache.hello_world_async_soap_http.GreeterAsync#greetMeSometimeAsync(java.lang.String  requestType )*
     */
    public Response<org.apache.hello_world_async_soap_http.types.GreetMeSometimeResponse> greetMeSometimeAsync(java.lang.String requestType) { 
       return null;
       /* not called */
    }

    /* (non-Javadoc)
     * @see org.apache.hello_world_async_soap_http.GreeterAsync#greetMeSometimeAsync(java.lang.String  requestType ,)AsyncHandler<org.apache.hello_world_async_soap_http.types.GreetMeSometimeResponse>  asyncHandler )*
     */
    public Future<?> greetMeSometimeAsync(java.lang.String requestType,AsyncHandler<org.apache.hello_world_async_soap_http.types.GreetMeSometimeResponse> asyncHandler) { 
       return null;
       /* not called */
    }

    /* (non-Javadoc)
     * @see org.apache.hello_world_async_soap_http.GreeterAsync#greetMeSometime(java.lang.String  requestType )*
     */
    public java.lang.String greetMeSometime(java.lang.String requestType) { 
        LOG.info("Executing operation greetMeSometime");
        System.out.println(requestType);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

}
