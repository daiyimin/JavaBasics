package com.learning.cxf.server.simple;

import org.apache.cxf.aegis.databinding.AegisDatabinding;
import org.apache.cxf.frontend.ServerFactoryBean;

/**
 * refer to http://www.ibm.com/developerworks/cn/webservices/ws-apachecxf/index.html
 * 
 * @author eyimdai
 */
public class CourseServer {
    public static void main(String...strings ) {
        CourseManagerImpl implementor = new CourseManagerImpl();
        
        ServerFactoryBean svrFactory = new ServerFactoryBean();
        svrFactory.setAddress("http://localhost:9000/CourseManager");
        svrFactory.setServiceBean(implementor);
        svrFactory.getServiceFactory().setDataBinding(new AegisDatabinding());
        svrFactory.create();
        System.out.println("Server ready...");    }
}
