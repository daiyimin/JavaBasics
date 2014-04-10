package jmxbook.ch3;

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

/**
 * Step 0. Install ActiveMQ on your test environment and start it.
 * Open ActiveMQ GUI http://localhost:8161/, use admin/admin to login
 * Step 1. Run main in JMXBookAgent
 * Step 2. Run main in JMSSetup
 * Step 3. Run main in DebugSubscriber
 * Step 4. Login localhost:9092/, open MBean view of JMS_Controller_Bean.
 * Invoke turnoffHomeTheater
 * Check the ActiveMQ GUI, find that topic/ControlMessages and topic/DeviceMessages is enqueued and dequeued correctly 
 * Step 5. Run main in JMSPublisher
 * Find that topic/ControlMessages and topic/DeviceMessages is enqueued and dequeued correctly 
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
        /*----------[ Added in Chapter 9 ]----------------*/
        // startTCPAdapter();
        // startJINIConnector();
        /*----------[ Added in Chapter 9 ]----------------*/

        /*----------[ Added in Chapter 10 ]----------------*/
        // startMletService();
        /*----------[ Added in Chapter 10 ]----------------*/

        /*----------[ Added in Chapter 11 ]----------------*/
        // createRelationService();
        /*----------[ Added in Chapter 11 ]----------------*/

        /*----------[ Added in Chapter 12 ]----------------*/
        // startTimerService();
        /*----------[ Added in Chapter 12 ]----------------*/
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

    public static void main(String[] args) {
        System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("\n>>> START of JMXBook Agent");
        System.out.println("\n>>> CREATE the agent...");
        JMXBookAgent agent = new JMXBookAgent();
        System.out.println("\nAgent is Ready for Service...\n");
    }

    /*----------[ Added in Chapter 9 ]----------------*/
    /*
     * protected void startTCPAdapter() { TCPServer tcp = new TCPServer(); ObjectName adapterName = null; try { adapterName = new ObjectName(
     * "JMXBookAgent:name=TCPAdapter" ); server.registerMBean( tcp, adapterName ); tcp.start(); } catch(Exception e) { e.printStackTrace(); } }
     */
    protected void startJINIConnector() {
        /*
         * ObjectName connectorName = null;
         * 
         * try { System.setSecurityManager( new RMISecurityManager() ); JINIServer jini = new JINIServer(); ObjectName jiniName = null; jiniName = new
         * ObjectName( "JMXBookAgent:name=JINIConnector" ); server.registerMBean( jini, jiniName ); jini.enableConnections(); } catch(Exception e) {
         * e.printStackTrace(); }
         */

    }

    /*----------[ Added in Chapter 9 ]----------------*/

    /*----------[ Added in Chapter 10 ]----------------*/
    protected void startMletService() {
        ObjectName mletName = null;
        try {
            mletName = new ObjectName("JMXBookAgent:name=mlet");
            server.createMBean("javax.management.loading.MLet", mletName);
        } catch (Exception e) {
            ExceptionUtil.printException(e);
        }
    }

    /*----------[ Added in Chapter 10 ]----------------*/

    /*----------[ Added in Chapter 11 ]----------------*/
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

    /*----------[ Added in Chapter 11 ]----------------*/

    /*----------[ Added in Chapter 12 ]----------------*/
    protected void startTimerService() {
        Timer timer = new Timer();
        ObjectName timerName = null;

        try {
            timerName = new ObjectName("JMXBookAgent:name=timer");
            server.registerMBean(timer, timerName);
            timer.setSendPastNotifications(true);
            // start timer
            timer.start();
        } catch (Exception e) {
            ExceptionUtil.printException(e);
        }

    }
    /*----------[ Added in Chapter 12 ]----------------*/

}
