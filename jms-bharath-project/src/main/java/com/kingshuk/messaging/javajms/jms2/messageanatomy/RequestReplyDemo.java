package com.kingshuk.messaging.javajms.jms2.messageanatomy;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class RequestReplyDemo {

    public static void main(String[] args) {
        InitialContext context = null;
        Queue requestQueue = null;
        Queue replyQueue = null;

        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             JMSContext jmsContext = connectionFactory.createContext();) {
            context = new InitialContext();

            requestQueue = (Queue) context.lookup("queue/requestQueue");
            replyQueue = (Queue) context.lookup("queue/replyQueue");

            JMSProducer producer = jmsContext.createProducer();
            producer.send(requestQueue, "This is message from Kingshuk using JMS 2.0");


            JMSConsumer consumer = jmsContext.createConsumer(requestQueue);
            String messageBody = consumer.receiveBody(String.class);
            System.out.println(messageBody);

            JMSProducer replyProducer = jmsContext.createProducer();
            replyProducer.send(replyQueue, "I have received the message");

            JMSConsumer consumer2 = jmsContext.createConsumer(replyQueue);
            String messageReply = consumer2.receiveBody(String.class);
            System.out.println(messageReply);

        } catch (NamingException e) {
            System.out.print("An error occurred while testing request reply message...");
        }
    }

}
