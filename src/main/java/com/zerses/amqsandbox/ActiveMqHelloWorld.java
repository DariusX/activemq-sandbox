package com.zerses.amqsandbox;

/**
 * Hello world!
 */
public class ActiveMqHelloWorld {

    //public static final String BROKER_URL = "vm://localhost";
    public static final String BROKER_URL = "tcp://159.203.128.56:61616";
    
    public static void main(String[] args) throws Exception {
        thread(new SimpleConsumer(), false);
        for (int i = 0; i < 3; i++) {
            thread(new SimpleProducer("Hello Broker! Message:" + i), false);
        }
        Thread.sleep(6000);
        for (int i = 3; i < 6; i++) {
            thread(new SimpleProducer("Hello Broker! Message:" + i), false);
        }
        Thread.sleep(6000);
        for (int i = 6; i < 9; i++) {
            thread(new SimpleProducer("Hello Broker! Message:" + i), false);
        }

    }

    public static void thread(Runnable runnable, boolean daemon) {
        Thread brokerThread = new Thread(runnable);
        brokerThread.setDaemon(daemon);
        brokerThread.start();
    }

}
