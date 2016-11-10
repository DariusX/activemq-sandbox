# activemq-sandbox

## Purpose

1. To create a program that reads messages from an ActiveMQ queue, and responds. This "listener" can then be used to facilitate testing from other systems
2. To test ActiveMQ features, as needed

## Prerequisites

The machine running this, must have
* An ActiveMQ broker (the code assumes the default port 61616 is in use; the IP is assumed to be localhost). To change these, edit the class TestConfig and re-build
* A JVM to run the JAR
* Notes specific to my box: `startamq.sh` starts AMQ, checks for port 61616 ; then, edit /etc/exports if the clientIP changed, and run `nfs.sh`; finally, start the Java listener/responder `java -jar atest`

## Packaging and installation

Running `mvn clean install` will create a jar file
Run it with `java -jar atest.jar`

## Runtime

* The class SimpleConsumer will run. 
* It will read from a queue named TEST.FOO. To change this, edit the class TestConfig and re-build
* The read will time out every 5 seconds (see class TestConfig)
* There's a max number of times it will read (see class TestConfig)
* If a message is received within the time-out window, a response will be sent back to the sender (the assumption is that the sender uses the JMS approach of setting a 'reply-to' queue name.

