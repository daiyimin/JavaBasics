package jmxbook.ch8;
import java.util.Set;

import javax.management.*;

public class HelloWorld extends NotificationBroadcasterSupport implements HelloWorldMBean
{
  private MBeanServer server = null;
  
  public HelloWorld(MBeanServer server)
  {
    this.server = server;
    this.greeting = "Hello World! I am a Standard MBean";
  }

  public HelloWorld( String greeting )
  {
    this.greeting = greeting;
  }

  public void setGreeting( String greeting )
  {
    this.greeting = greeting;
    Notification notification = new Notification( "jmxbook.ch2.helloWorld.test", this, -1, System.currentTimeMillis(), greeting );
    sendNotification( notification );
  }

  public String getGreeting()
  {
    return greeting;
  }

  public void printGreeting()
  {
    System.out.println( greeting );
  }

  private String greeting;

  private int count;

  public void setCount(int count) {
      this.count = count;
  }

  public int getCount() {
    return count;
  }

  public void queryMBean() {
      QueryExp exp = Query.eq(Query.attr("Count"), Query.value(3));
      ObjectName name;
    try {
        name = new ObjectName( "HelloAgent:*" );
        Set<ObjectInstance> res = server.queryMBeans(name, exp);
        for (ObjectInstance oi : res) {
            System.out.println(oi.getObjectName());
        }
    } catch (Exception e) {
    }
  }
}//class

