
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package org.apache.hello_world_soap_http;

import java.util.logging.Logger;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * How to develop WSDL first web service
 * 1) http://cxf.apache.org/docs/developing-a-service.html
 * 2) generate server starting point code
 * >c:\Temp\apache-cxf-2.7.10\bin\wsdl2java -ant -impl -server -d wsdl2java_output
 -frontend jaxws21 hello_world.wsdl
 * 3) create an empty maven project
 * 4) copy generated server code in 2) to src/main/java
 * 5) implement business logic code in GreeterImpl.java
 * 
 * How to deploy web service?
 * 1) Copy webapp folder to your src/main, if you use maven
 * 2) Modify the related info in the cxfdemo-beans.xml
 * To be specific, change the implementor and address
 * 3) Add CXF dependency in pom file
 * 4) Add Jetty plugin in pom file
 * 5) Run Jetty goal: mvn jetty:run
 * 6) Open Jetty web console
 * http://localhost:8080/com.learning.cxf.wf.server/services
 */

@javax.jws.WebService(
                      serviceName = "SOAPService",
                      portName = "SoapPort",
                      targetNamespace = "http://apache.org/hello_world_soap_http",
                      wsdlLocation = "hello_world.wsdl",
                      endpointInterface = "org.apache.hello_world_soap_http.Greeter")
                      
public class GreeterImpl implements Greeter {

    private static final Logger LOG = Logger.getLogger(GreeterImpl.class.getName());

    /* (non-Javadoc)
     * @see org.apache.hello_world_soap_http.Greeter#pingMe(*
     */
    public void pingMe() throws PingMeFault    { 
        LOG.info("Executing operation pingMe");
        try {
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new PingMeFault("pingMeFault...");
    }

    /* (non-Javadoc)
     * @see org.apache.hello_world_soap_http.Greeter#sayHi(*
     */
    public java.lang.String sayHi() { 
        System.out.println("Executing operation sayHi\n");
        return "Bonjour";
    }

    /* (non-Javadoc)
     * @see org.apache.hello_world_soap_http.Greeter#greetMeOneWay(java.lang.String  requestType )*
     */
    public void greetMeOneWay(java.lang.String requestType) { 
        LOG.info("Executing operation greetMeOneWay");
        System.out.println(requestType);
        try {
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see org.apache.hello_world_soap_http.Greeter#greetMe(java.lang.String  requestType )*
     */
    public java.lang.String greetMe(java.lang.String requestType) { 
        LOG.info("Executing operation greetMe");
        System.out.println(requestType);
        return "Hello " + requestType;
    }

}
