package com.kingshuk.messaging.javajms.jms1;

import java.util.Enumeration;
import java.util.Objects;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class QueueMessageBrowser {

	public static void main(String[] args) throws JMSException {
		InitialContext context = null;
		Connection connection = null;
		Session session = null;
		MessageProducer producer = null;
		MessageConsumer consumer = null;
		QueueBrowser queueBrowser = null;
		try {
			context = new InitialContext();

			ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");

			connection = connectionFactory.createConnection();

			session = connection.createSession();

			Queue queue = (Queue) context.lookup("queue/myQueue");

			producer = session.createProducer(queue);

			TextMessage textMessage1 = session.createTextMessage("This is the first proper message from Kingshuk");
			TextMessage textMessage2 = session.createTextMessage("This is the second proper message from Kingshuk");

			producer.send(textMessage1);

			System.out.println("Message sent: " + textMessage1.getText());

			producer.send(textMessage2);

			System.out.println("Message sent: " + textMessage2.getText());

			queueBrowser = session.createBrowser(queue);

			@SuppressWarnings("unchecked")
			Enumeration<TextMessage> messages = queueBrowser.getEnumeration();

			System.out.println("\n=================Printing browsed messages=================\n");
			while (messages.hasMoreElements()) {
				System.out.println(messages.nextElement().getText());

			}
			System.out.println("========================================\n");

			// Consume the message
			consumer = session.createConsumer(queue);

			connection.start();

			TextMessage message = (TextMessage) consumer.receive(5000);

			System.out.println("First Message received: " + message.getText());
			
			message = (TextMessage) consumer.receive(5000);

			System.out.println("Second Message received: " + message.getText());
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			if (Objects.nonNull(connection)) {
				connection.close();
			}

			if (Objects.nonNull(session)) {
				session.close();
			}

			if (Objects.nonNull(producer)) {
				producer.close();
			}

			if (Objects.nonNull(consumer)) {
				consumer.close();
			}

			if (Objects.nonNull(queueBrowser)) {
				queueBrowser.close();
			}
		}
	}

}
