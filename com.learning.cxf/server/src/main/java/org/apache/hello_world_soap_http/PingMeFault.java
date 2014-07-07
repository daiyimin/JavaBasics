
package org.apache.hello_world_soap_http;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.7.10
 * 2014-03-29T22:24:27.552+08:00
 * Generated source version: 2.7.10
 */

@WebFault(name = "faultDetail", targetNamespace = "http://apache.org/hello_world_soap_http/types")
public class PingMeFault extends Exception {
    
    private org.apache.hello_world_soap_http.types.FaultDetail faultDetail;

    public PingMeFault() {
        super();
    }
    
    public PingMeFault(String message) {
        super(message);
    }
    
    public PingMeFault(String message, Throwable cause) {
        super(message, cause);
    }

    public PingMeFault(String message, org.apache.hello_world_soap_http.types.FaultDetail faultDetail) {
        super(message);
        this.faultDetail = faultDetail;
    }

    public PingMeFault(String message, org.apache.hello_world_soap_http.types.FaultDetail faultDetail, Throwable cause) {
        super(message, cause);
        this.faultDetail = faultDetail;
    }

    public org.apache.hello_world_soap_http.types.FaultDetail getFaultInfo() {
        return this.faultDetail;
    }
}
