package jmxbook.ch9;

import java.rmi.registry.LocateRegistry;

import javax.management.*;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

import com.sun.jdmk.comm.HtmlAdaptorServer;

/**
 * Step 1. Run main in JMXBookAgent
 * Step 2. Run main in PoolSetup
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
//        startRMIConnector();
        startTCPAdapter();
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
    
    protected void startTCPAdapter()
    {
        TCPServer tcp = new TCPServer();
        ObjectName adapterName = null;
        try
        {
            adapterName = new ObjectName(
            "JMXBookAgent:name=TCPAdapter");
            server.registerMBean( tcp, adapterName);
            tcp.start();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("\n>>> START of JMXBook Agent");
        System.out.println("\n>>> CREATE the agent...");
        JMXBookAgent agent = new JMXBookAgent();
        System.out.println("\nAgent is Ready for Service...\n");
    }
}

