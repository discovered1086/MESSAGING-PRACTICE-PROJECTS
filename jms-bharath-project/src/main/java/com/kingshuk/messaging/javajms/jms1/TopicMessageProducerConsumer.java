package com.kingshuk.messaging.javajms.jms1;

import java.util.Objects;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TopicMessageProducerConsumer {

	public static void main(String[] args) throws JMSException {
		InitialContext context = null;
		Connection connection = null;
		Session session = null;
		MessageProducer producer = null;
		MessageConsumer consumer1 = null;
		MessageConsumer consumer2 = null;
		try {
			context = new InitialContext();

			ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");

			connection = connectionFactory.createConnection();

			session = connection.createSession();

			Topic topic = (Topic) context.lookup("topic/myTopic");

			producer = session.createProducer(topic);

			// Create the consumers
			consumer1 = session.createConsumer(topic);

			// Second consumer
			consumer2 = session.createConsumer(topic);

			TextMessage message = session.createTextMessage("This is a general message for whoever is interested");

			producer.send(message);

			System.out.println("Message sent: " + message.getText());

			connection.start();

			TextMessage receivedMessage1 = (TextMessage) consumer1.receive();

			System.out.println("Message received by consumer 1: " + receivedMessage1.getText());

			TextMessage receivedMessage2 = (TextMessage) consumer2.receive();

			System.out.println("Message received by consumer 2: " + receivedMessage2.getText());
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

			if (Objects.nonNull(consumer1)) {
				consumer1.close();
			}
			
			if (Objects.nonNull(consumer2)) {
				consumer2.close();
			}
		}
	}

}
