
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
 * This class was generated by Apache CXF 2.7.10
 * 2014-03-29T22:24:27.582+08:00
 * Generated source version: 2.7.10
 * 
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
        LOG.info("Executing operation sayHi");
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
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
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

}
