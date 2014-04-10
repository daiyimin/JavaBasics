package jmxbook.ch13;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;
import javax.jms.TopicPublisher;
import javax.jms.Topic;
import javax.jms.TextMessage;
import javax.jms.Session;
import javax.jms.JMSException;
import javax.jms.MessageProducer;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSPublisher
{
  private Connection topicConnection=null;
  private Session topicSession=null;
  private MessageProducer topicPublisher=null;
  private Topic topic=null;
  private ConnectionFactory topicFactory = null;

  public JMSPublisher( String factoryJNDI, String topicJNDI ) throws JMSException, NamingException
  {

    topicFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

    topicConnection = topicFactory.createConnection();
    topicConnection.start();

    topicSession = topicConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    topic = topicSession.createTopic("topic/controlMessages");

    // Create a publisher
    topicPublisher = topicSession.createProducer(topic);
  }

  public void publish(String msg) throws JMSException
  {

    // Create a message
    TextMessage message = topicSession.createTextMessage();
    message.setText(msg);

    // Publish the message
    topicPublisher.send(topic, message);
  }

  public void close() throws JMSException {
    topicSession.close();
    topicConnection.close();
  }

  public static void main(String[] args) {
    JMSPublisher publisher=null;

    try{
      publisher= new JMSPublisher("TopicConnectionFactory", "topic/controlMessages");
      String msg = "MOVIELIGHTSOn";

      System.out.println("Publishing message: "+msg);
            publisher.publish(msg);
      try{Thread.sleep(2000);}catch(InterruptedException e){}

      msg = "MOVIELIGHTSOff";
      System.out.println("Publishing message: "+msg);
      publisher.publish(msg);
      try{Thread.sleep(2000);}catch(InterruptedException e){}

      msg = "doNothing";
      System.out.println("Publishing message: "+msg);
      publisher.publish(msg);

      // Close down your publisher
      publisher.close();
    }
    catch(Exception ex) {
      System.err.println("An exception occurred " + "while testing Publisher: " + ex);
      ex.printStackTrace();
    }

  }

}


