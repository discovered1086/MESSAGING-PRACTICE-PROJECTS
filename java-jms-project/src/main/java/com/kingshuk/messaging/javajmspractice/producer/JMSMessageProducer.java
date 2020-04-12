package com.kingshuk.messaging.javajmspractice.producer;


import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueSession;
import javax.jms.TextMessage;

import com.kingshuk.messaging.javajmspractice.commonconfig.JMSSessionBuilder;

public class JMSMessageProducer {

    public static void main(String[] args) {

        try {

            JMSSessionBuilder sessionBuilder = new JMSSessionBuilder();
            QueueSession session = sessionBuilder.getSession();
            Queue queue = sessionBuilder.getQueue();
            QueueConnection queueConnection = sessionBuilder.getQueueConnection();

            queueConnection.start();


            sendMessage(session, queue);


            queueConnection.stop();

            session.close();

        } catch (JMSException ex) {
            ex.printStackTrace();
        }

    }


    private static void sendMessage(QueueSession session, Queue queue) {

        try {
            MessageProducer producer = session.createProducer(queue);

            TextMessage textMessage = session.createTextMessage("This is hello from the producer");


            producer.send(textMessage);

            producer.close();
        } catch (JMSException ex) {
            ex.printStackTrace();
        }
    }


}
