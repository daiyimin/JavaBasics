
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
 * How to develop WSDL first web service
 * 1) http://cxf.apache.org/docs/developing-a-service.html
 * 2) generate server starting point code
 * >c:\Temp\apache-cxf-2.7.10\bin\wsdl2java -ant -impl -server -d wsdl2java_output 
-b async_binding.xml -frontend jaxws21 hello_world_async.wsdl hello_world.wsdl
 * 3) create an empty maven project
 * 4) copy generated server code in 2) to src/main/java
 * 5) implement business logic code in GreeterAsyncImpl
 * 
 * How to deploy web service?
 * 1) Copy webapp folder to your src/main, if you use maven
 * 2) Modify the related info in the cxfdemo-beans.xml
 * To be specific, change the implementor and address
 * 3) Add CXF dependency in pom file
 * 4) Add Jetty plugin in pom file
 * 5) Run Jetty goal: mvn jetty:run
 * 6) Open Jetty web console
 * http://localhost:8080/com.learning.cxf.asyncServer/services/GreeterAsyncImpl
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
            java.lang.String _return = "Hello " + requestType;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

}
