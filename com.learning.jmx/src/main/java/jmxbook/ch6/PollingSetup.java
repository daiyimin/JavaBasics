package jmxbook.ch6;

import javax.management.*;

import com.sun.jdmk.comm.*;
import jmxbook.ch3.*;

public class PollingSetup implements NotificationListener
{

  public PollingSetup()
  {
    try
    {
      MBeanServerConnection client = RMIClientFactory.getClient();
      ObjectName pollingName = new ObjectName( "JMXBookAgent:name=polling");
      client.createMBean( "jmxbook.ch6.Polling", pollingName );
      client.addNotificationListener( pollingName, this, null, null );
    }
    catch( Exception e )
    {
      ExceptionUtil.printException( e );
    }
  }

  public void handleNotification( Notification not, Object obj )
  {
    String type = not.getType();
    System.out.println( type );
  }

  public static void main( String args[] )
  {
    PollingSetup setup = new PollingSetup ();
    
    // start a dummy thread to prevent this program stop immediately 
    // otherwise no notification can be received
    new Thread( new Runnable() {
        public void run() {
           while (true) {
               try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
           }
        }
    }).start();
  }

}

