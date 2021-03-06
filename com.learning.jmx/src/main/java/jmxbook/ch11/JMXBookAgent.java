package jmxbook.ch11;

import com.sun.jdmk.comm.*;
import javax.management.*;
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

import jmxbook.ch3.ExceptionUtil;

/**
 * Step 1. Run main in JMXBookAgent
 * Step 2. Run main in RelationMain
 * Step 3. Open http://localhost:9092
 * Step 4. Disable a phone card or fax card using relation service
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
        createRelationService();
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

    public static void main(String[] args) {
        System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("\n>>> START of JMXBook Agent");
        System.out.println("\n>>> CREATE the agent...");
        JMXBookAgent agent = new JMXBookAgent();
        System.out.println("\nAgent is Ready for Service...\n");
    }

    public void createRelationService() {
        ObjectName relationServiceName = null;
        try {
            relationServiceName = new ObjectName("JMXBookAgent:name=relationService");

            Object[] params = new Object[1];
            params[0] = new Boolean(true);
            String[] signature = new String[1];
            signature[0] = "boolean";

            server.createMBean("javax.management.relation." + "RelationService", relationServiceName, params, signature);
        } catch (Exception e) {
            System.out.println("Error Creating the Relation" + " Service MBean");
            e.printStackTrace();
        }
    }
    
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
}
