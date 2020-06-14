package com.kingshuk.messaging.javajmspractice.consumer;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueSession;
import javax.jms.TextMessage;

import com.kingshuk.messaging.javajmspractice.commonconfig.JMSSessionBuilder;

public class JMSMessageConsumer {

	public static void main(String[] args) {
		try {

			JMSSessionBuilder sessionBuilder = new JMSSessionBuilder();
			QueueSession session = sessionBuilder.getSession();
			Queue queue = sessionBuilder.getQueue();
			QueueConnection queueConnection = sessionBuilder.getQueueConnection();

			queueConnection.start();

			receiveMessage(session, queue);

			queueConnection.stop();

			session.close();

		} catch (JMSException ex) {
			ex.printStackTrace();
		}
	}

	private static void receiveMessage(QueueSession session, Queue queue) {

		try (MessageConsumer consumer = session.createConsumer(queue);) {

			TextMessage message = (TextMessage) consumer.receive();

			System.out.println("Message received from the sender is \n" + message);

		} catch (JMSException ex) {
			ex.printStackTrace();
		}
	}
}
