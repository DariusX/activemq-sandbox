package com.zerses.amqsandbox;

public interface TestConfig {

    //public static final String BROKER_URL = "vm://localhost";
    public static final String BROKER_URL = "tcp://localhost:61616";
    public static final String QUEUE_NAME = "TEST.FOO";
    public static final long READ_TIMEOUT =  5000;
    public static final long MAX_READS =  20000;

}
