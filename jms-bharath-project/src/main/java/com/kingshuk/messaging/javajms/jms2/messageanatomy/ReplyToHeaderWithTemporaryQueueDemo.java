package com.kingshuk.messaging.javajms.jms2.messageanatomy;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Objects;

public class ReplyToHeaderWithTemporaryQueueDemo {

    public static void main(String[] args) throws NamingException {
        InitialContext context = null;
        JMSConsumer consumer = null;
        JMSConsumer consumer2 = null;

        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             JMSContext jmsContext = connectionFactory.createContext();) {
            context = new InitialContext();

            Queue requestQueue = (Queue) context.lookup("queue/requestQueue");
            TemporaryQueue replyQueue = jmsContext.createTemporaryQueue();
            JMSProducer producer = jmsContext.createProducer();
            TextMessage textMessage = jmsContext.createTextMessage("This is message from Kingshuk using JMS 2.0");
            textMessage.setJMSReplyTo(replyQueue);
            producer.send(requestQueue, textMessage);

            consumer = jmsContext.createConsumer(requestQueue);
            TextMessage message = (TextMessage) consumer.receive();
            System.out.println(message.getText());

            JMSProducer replyProducer = jmsContext.createProducer();
            replyProducer.send(message.getJMSReplyTo(), "I have received the message");

            consumer2 = jmsContext.createConsumer(replyQueue);
            String messageReply = consumer2.receiveBody(String.class);
            System.out.println(messageReply);

        } catch (NamingException | JMSException e) {
            System.out.print("An error occurred while testing request reply message...");
        } finally {
            Objects.requireNonNull(context).close();
            Objects.requireNonNull(consumer).close();
            Objects.requireNonNull(consumer2).close();
        }
    }

}
