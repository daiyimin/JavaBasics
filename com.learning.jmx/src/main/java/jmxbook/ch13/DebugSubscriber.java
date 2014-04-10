package jmxbook.ch13;

import javax.naming.NamingException;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Topic;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.jms.Session;
import javax.jms.MessageListener;
import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnectionFactory;

public class DebugSubscriber implements MessageListener
{
  private Connection topicConnection=null;
  private Session topicSession=null;
  private MessageConsumer topicSubscriber=null;
  private Topic topic=null;
  private ConnectionFactory topicFactory=null;
  private int count_=0;

  public DebugSubscriber() throws JMSException, NamingException
  {
      topicFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

      // Create the connection
      topicConnection = topicFactory.createConnection();
      topicConnection.start();

    // Create the session
      topicSession = topicConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    //
    // Look up the destination
      topic = topicSession.createTopic("topic/deviceMessages");
    //
    // Create a subscriber
      topicSubscriber = topicSession.createConsumer(topic);
    //
    // Set the message listener,
    // which is this class since we implement
    // the MessageListener interface
      topicSubscriber.setMessageListener(this);
      System.out.println("DeviceSubscriber subscribed to topic: " + "topic/deviceMessages");

  }

  public void onMessage(Message m)
  {
    try {
      String msg = ((TextMessage)m).getText();
      System.out.println("DeviceSubscriber got message: " + msg);
    }
    catch(Exception ex) {
      System.err.println("Device Could not handle message: " + ex);
      ex.printStackTrace();
    }
  }

  public void close() throws JMSException
  {
    topicSession.close();
    topicConnection.close();
  }

  public static void main(String[] args)
  {
    DebugSubscriber subscriber=null;

    try{
      System.out.println("Starting Debugging Subscriber");
      subscriber=new DebugSubscriber();
    }
    catch(Exception e){
      System.out.println("Error Starting Device DebugClient");
      e.printStackTrace();
    }

  }

}


