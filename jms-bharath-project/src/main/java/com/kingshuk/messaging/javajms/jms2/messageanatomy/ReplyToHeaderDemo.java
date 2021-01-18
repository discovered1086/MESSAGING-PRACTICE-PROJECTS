package com.kingshuk.messaging.javajms.jms2.messageanatomy;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ReplyToHeaderDemo {

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
            TextMessage textMessage = jmsContext.createTextMessage("This is message from Kingshuk using JMS 2.0");
            textMessage.setJMSReplyTo(replyQueue);
            producer.send(requestQueue, textMessage);


            JMSConsumer consumer = jmsContext.createConsumer(requestQueue);
            TextMessage message = (TextMessage) consumer.receive();
            System.out.println(message.getText());

            JMSProducer replyProducer = jmsContext.createProducer();
            replyProducer.send(message.getJMSReplyTo(), "I have received the message");

            JMSConsumer consumer2 = jmsContext.createConsumer(replyQueue);
            String messageReply = consumer2.receiveBody(String.class);
            System.out.println(messageReply);

        } catch (NamingException | JMSException e) {
            System.out.print("An error occurred while testing request reply message...");
        }
    }

}
