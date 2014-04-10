package jmxbook.ch7;

import com.sun.jdmk.comm.*;
import javax.management.*;
import javax.management.modelmbean.ModelMBeanInfo;
import javax.management.modelmbean.RequiredModelMBean;
import javax.management.remote.JMXServiceURL;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;

/*----------[ Added in Chapter 9 ]----------------*/
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
/*----------[ Added in Chapter 9 ]----------------*/

/*----------[ Added in Chapter 12 ]----------------*/
import javax.management.timer.Timer;

/**
 * Step 1. Run main in JMXBookAgent
 * Step 2. Run main in ModelClass
 * Step 3. Open http://localhost:9092
 * 
 * @author eyimdai
 */
public class JMXBookAgent {
    private MBeanServer server = null;

    public JMXBookAgent() {
        System.out.println("\n\tCREATE the MBeanServer.");
        server = MBeanServerFactory.createMBeanServer("JMXBookAgent");
        startHTMLAdapter();
        startRMIConnector();
        try {
//            registerModeledMBean();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void startHTMLAdapter() {
        HtmlAdaptorServer adapter = new HtmlAdaptorServer();
        ObjectName adapterName = null;

        try {
            adapter.setPort(9092);
            // create the HTML adapter
            adapterName = new ObjectName("JMXBookAgent:name=html,port=9092");
            server.registerMBean(adapter, adapterName);
            adapter.start();
        } catch (Exception e) {
            ExceptionUtil.printException(e);
            System.out.println("Error Starting HTML Adapter for Agent");
        }

    }

    /**
     * RMI connector mentioned in the book is no longer supported
     * Instead, we can use JMXConnectorServer to accept RMI connections
     */
    protected void startRMIConnector() {

        try {
            LocateRegistry.createRegistry(9999);
            JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:9999/server");
            JMXConnectorServer cs = JMXConnectorServerFactory.newJMXConnectorServer(url, null, server);
            cs.start();
        } catch (Exception e) {
            ExceptionUtil.printException(e);
        }
    }
    
    protected ModelMBeanInfo buildModelMBeanInfo() throws Exception {
        // start to build ModelMBeanInfo
        ModelMBeanInfoBuilder builder = new ModelMBeanInfoBuilder();
        Descriptor attDesc = builder.buildAttributeDescriptor("MyAttribute", null, "always", "10", null, "getMyAttribute", null, "10");
        builder.addModelMBeanAttribute("MyAttribute", "java.lang.String", true, false, false, "", attDesc);
        
        Descriptor opGetDesc = builder.buildOperationDescriptor("getMyAttribute", null, "getter", null, null, "jmxbook.ch7.ModeledClass", "10");
        builder.addModelMBeanMethod("getMyAttribute", null, null, null, "", "java.lang.String", MBeanOperationInfo.INFO, opGetDesc);
        
        Descriptor opDesc = builder.buildOperationDescriptor("printAttribute", null, "operation", null, null, "jmxbook.ch7.ModeledClass", "10");
        builder.addModelMBeanMethod("printAttribute", null, null, null, "", "void", MBeanOperationInfo.ACTION, opDesc);
        
//        Descriptor opStoreDesc = builder.buildOperationDescriptor("store", null, "operation", null, null, "jmxbook.ch7.ModeledClass", "10");
//        builder.addModelMBeanMethod("store", null, null, null, "", "void", MBeanOperationInfo.ACTION, opStoreDesc);

        Descriptor mbeanDesc = builder.buildMBeanDescriptor("modeledClass", "", "always", "10", ".", "ModeledClass", null, null);
        
        ModelMBeanInfo info = builder.buildModelMBeanInfo(mbeanDesc);
        
        return info;
    }
    
    protected void registerModeledMBean() throws Exception {
        RequiredModelMBean mbean = new RequiredModelMBean();
        
        ModelMBeanInfo info = buildModelMBeanInfo();
        mbean.setModelMBeanInfo(info);
        
        mbean.setManagedResource(new ModeledClass(), "ObjectReference");

        ObjectName mName = new ObjectName("JMXBookAgent:name=Modeled");
        server.registerMBean(mbean, mName);
    }

    public static void main(String[] args) {
        System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("\n>>> START of JMXBook Agent");
        System.out.println("\n>>> CREATE the agent...");
        JMXBookAgent agent = new JMXBookAgent();
        System.out.println("\nAgent is Ready for Service...\n");
    }
}
