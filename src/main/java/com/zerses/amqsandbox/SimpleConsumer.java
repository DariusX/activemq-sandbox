package com.zerses.amqsandbox;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * Hello world!
 */
public class SimpleConsumer implements Runnable, ExceptionListener {

    public void run() {
        try {

            // Create a ConnectionFactory
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(TestConfig.BROKER_URL);

            // Create a Connection
            Connection connection = connectionFactory.createConnection();
            connection.start();

            connection.setExceptionListener(this);

            // Create a Session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            Destination destination = session.createQueue(TestConfig.QUEUE_NAME);

            // Create a MessageConsumer from the Session to the Topic or Queue
            MessageConsumer consumer = session.createConsumer(destination);

            // Create a MessageProducer from the Session to the Topic or Queue
            MessageProducer producer = session.createProducer(null);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            // Will wait and read the first N messages, then exit
            // Note: We use a time-out, so we might get null messages too
            for (int msgIdx = 0; msgIdx < TestConfig.MAX_READS; msgIdx++) {
                // Wait for a message
                Message inMessage = consumer.receive(TestConfig.READ_TIMEOUT);

                if (inMessage instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage)inMessage;
                    String text = textMessage.getText();
                    System.out.println("Received text: " + text);
                } else if (inMessage == null) {
                    System.out.println("Received null message. Probably timed-out waiting. Will re-try. ");
                } else {
                    System.out.println("Received non-text message: " + inMessage);
                }

                if (inMessage != null) {
                    // Create a message and reply
                    // Before replying, tie it to the incoming message, using
                    // the correlationId
                    TextMessage outMessage = session.createTextMessage("Thanks for your message. Here's your reply: Boo!");
                    outMessage.setJMSCorrelationID(inMessage.getJMSCorrelationID());
                    System.out.println("Sending reply: " + inMessage.getJMSCorrelationID() + " Hashcode=" + outMessage.hashCode() + " : " + Thread.currentThread().getName());
                    System.out.println("Sending reply text: " + outMessage.getText());
                    if (inMessage.getJMSReplyTo() == null) {
                        System.out.println("Replying to dest: " + inMessage.getJMSReplyTo());
                        producer.send(inMessage.getJMSReplyTo(), outMessage);
                    } else {
                        System.out.println("Sender has not sent a repto-to destination. No reply will be sent.");
                    }
                }

            }

            consumer.close();
            session.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        Thread myThread = new Thread(new SimpleConsumer());
        myThread.setDaemon(false);
        myThread.start();
    }

    public synchronized void onException(JMSException ex) {
        System.out.println("JMS Exception occured.  Shutting down client.");
    }

}
