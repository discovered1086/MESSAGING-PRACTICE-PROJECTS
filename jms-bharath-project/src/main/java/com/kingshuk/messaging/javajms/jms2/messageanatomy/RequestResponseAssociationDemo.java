package com.kingshuk.messaging.javajms.jms2.messageanatomy;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestResponseAssociationDemo {

    public static Map<String, TextMessage> messages = new HashMap<>();

    public static void main(String[] args) {
        InitialContext context = null;
        Queue requestQueue = null;
        Queue replyQueue = null;

        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             JMSContext jmsContext = connectionFactory.createContext();) {
            context = new InitialContext();

            requestQueue = (Queue) context.lookup("queue/requestQueue");
            replyQueue = (Queue) context.lookup("queue/replyQueue");

            System.out.println("===============================================================");
            applicationASend(replyQueue, requestQueue, jmsContext);

            applicationB(requestQueue, jmsContext);

            applicationAReceive(replyQueue, jmsContext);
            System.out.println("\n===============================================================");

        } catch (NamingException | JMSException e) {
            System.out.print("An error occurred while testing request reply message...");
        }
    }

    private static void applicationASend(Queue replyQueue,
                                         Queue requestQueue,
                                         JMSContext jmsContext) throws JMSException {
        JMSProducer producer = jmsContext.createProducer();
        TextMessage textMessage = jmsContext.createTextMessage("This is message from Kingshuk using JMS 2.0");
        textMessage.setJMSReplyTo(replyQueue);
        producer.send(requestQueue, textMessage);
        messages.put(textMessage.getJMSMessageID(), textMessage);
        System.out.println(textMessage.getJMSMessageID());
    }

    private static void applicationB(Queue requestQueue, JMSContext jmsContext) throws JMSException {
        //Application B
        JMSConsumer consumer = jmsContext.createConsumer(requestQueue);
        TextMessage message = (TextMessage) consumer.receive();
        System.out.println(message.getText());

        TextMessage replyMessage = jmsContext.createTextMessage("This is a response to the message sent by application A");
        replyMessage.setJMSCorrelationID(message.getJMSMessageID());
        JMSProducer replyProducer = jmsContext.createProducer();
        replyProducer.send(message.getJMSReplyTo(), replyMessage);
    }

    private static void applicationAReceive(Queue replyQueue, JMSContext jmsContext) throws JMSException {
        //Application A
        JMSConsumer consumer2 = jmsContext.createConsumer(replyQueue);
        TextMessage messageReply = (TextMessage) consumer2.receive();
        System.out.println(messageReply.getText());
        System.out.print(messageReply.getJMSCorrelationID());
        TextMessage textMessage = messages.get(messageReply.getJMSCorrelationID());
        System.out.println("\nThe original message was: \n" +
                (Objects.nonNull(textMessage)?textMessage.getText():"Message not found"));
    }

}
