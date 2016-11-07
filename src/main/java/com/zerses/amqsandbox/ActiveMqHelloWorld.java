package com.zerses.amqsandbox;

/**
 * Hello world!
 */
public class ActiveMqHelloWorld {
 
    public static void main(String[] args) throws Exception {
        thread(new SimpleProducer(), false);
        thread(new SimpleProducer(), false);
        thread(new SimpleConsumer(), false);
        Thread.sleep(1000);
        thread(new SimpleConsumer(), false);
        thread(new SimpleProducer(), false);
        thread(new SimpleConsumer(), false);
        thread(new SimpleProducer(), false);
        Thread.sleep(1000);
        thread(new SimpleConsumer(), false);
        thread(new SimpleProducer(), false);
        thread(new SimpleConsumer(), false);
        thread(new SimpleConsumer(), false);
        thread(new SimpleProducer(), false);
        thread(new SimpleProducer(), false);
        Thread.sleep(1000);
        thread(new SimpleProducer(), false);
        thread(new SimpleConsumer(), false);
        thread(new SimpleConsumer(), false);
        thread(new SimpleProducer(), false);
        thread(new SimpleConsumer(), false);
        thread(new SimpleProducer(), false);
        thread(new SimpleConsumer(), false);
        thread(new SimpleProducer(), false);
        thread(new SimpleConsumer(), false);
        thread(new SimpleConsumer(), false);
        thread(new SimpleProducer(), false);
    }
 
    public static void thread(Runnable runnable, boolean daemon) {
        Thread brokerThread = new Thread(runnable);
        brokerThread.setDaemon(daemon);
        brokerThread.start();
    }
 

 
}