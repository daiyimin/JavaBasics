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

public class JMSController implements MessageListener, JMSControllerMBean {
    private Connection topicConnection = null;
    private Session topicSession = null;
    private MessageConsumer topicSubscriber = null;
    private Topic topic = null;
    private ConnectionFactory topicFactory = null;

    public JMSController() throws JMSException, NamingException {
        topicFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        topicConnection = topicFactory.createConnection();
        topicConnection.start();

        topicSession = topicConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        topic = topicSession.createTopic("topic/controlMessages");

        topicSubscriber = topicSession.createConsumer(topic);

        topicSubscriber.setMessageListener(this);
//        topicConnection.start();
    }

    public void onMessage(Message m) {
        String msg = null;
        String msg2 = null;

        try {
            msg = ((TextMessage) m).getText();

            if (msg.equals("MOVIELIGHTSOn")) {
                msg = "SurroundOn";
                msg2 = "ScreenDown";
                publishMessages(msg, msg2);
            } else if (msg.equals("MOVIELIGHTSOff")) {
                msg = "SurroundOff";
                msg2 = "ScreenUp";
                publishMessages(msg, msg2);
            } else {
                System.out.println("This message is not handled" + " by this MBean");
                return;
            }

        } catch (Exception ex) {
            System.err.println("Could not handle message: " + ex);
            ex.printStackTrace();
        }

    }

    public void publishMessages(String msg, String msg2) {
        Topic topic = null;
        MessageProducer topicPublisher = null;
        Session sendTopicSession = null;
        TextMessage message = null;

        try {
            System.out.println("Will publish " + msg + " Message to Device topic");

            topicFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            topicConnection = topicFactory.createConnection();
            topicConnection.start();
            
            // Create a publisher
            sendTopicSession = topicConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            topicPublisher = sendTopicSession.createProducer(topic);

            // Look up the destination
            topic = sendTopicSession.createTopic("topic/deviceMessages");
            System.out.println("Found the deviceMessages Topic");

            // Create a message
            message = sendTopicSession.createTextMessage();
            message.setText(msg);

            // Publish the message
            topicPublisher.send(topic, message);
            System.out.println("Published " + msg + " to deviceMessages Topic");

            // Create a message
            message = sendTopicSession.createTextMessage();
            message.setText(msg2);

            // Publish the message
            topicPublisher.send(topic, message);
            System.out.println("Published " + msg2 + " to deviceMessages Topic");
        } catch (Exception ex) {
            System.err.println("Could not handle message: " + ex);
            ex.printStackTrace();
        }
    }

    public void close() throws JMSException {
        topicSession.close();
        topicConnection.close();
    }

    public void turnOnHomeTheater() {
        System.out.println("Turning On Home Theater System");
        publishMessages("SurroundOn", "ScreenDown");
    }

    public void turnOffHomeTheater() {
        System.out.println("Turning Off Home Theater System");
        publishMessages("SurroundOff", "ScreenUp");
    }

}
