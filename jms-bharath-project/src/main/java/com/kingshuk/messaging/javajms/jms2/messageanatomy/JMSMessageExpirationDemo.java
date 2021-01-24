package com.kingshuk.messaging.javajms.jms2.messageanatomy;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JMSMessageExpirationDemo {

    public static void main(String[] args) {
        InitialContext context = null;
        Queue queue = null;

        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             JMSContext jmsContext = connectionFactory.createContext();) {
            context = new InitialContext();

            queue = (Queue) context.lookup("queue/myQueue");

            JMSProducer producer = jmsContext.createProducer();
            producer.setTimeToLive(2000);
            producer.send(queue, "This is message from Kingshuk using JMS 2.0");
            Thread.sleep(2500);

            JMSConsumer consumer = jmsContext.createConsumer(queue);
            String messageBody = consumer.receiveBody(String.class, 3000);

            System.out.println("The message is: " + messageBody);
        } catch (NamingException | InterruptedException e) {
            System.out.println(e);
        }
    }

}
