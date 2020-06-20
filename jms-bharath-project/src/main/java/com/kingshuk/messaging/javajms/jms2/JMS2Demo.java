package com.kingshuk.messaging.javajms.jms2;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class JMS2Demo {

	public static void main(String[] args) {
		InitialContext context = null;
		Queue queue = null;

		try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
				JMSContext jmsContext = connectionFactory.createContext();) {
			context = new InitialContext();

			queue = (Queue) context.lookup("queue/myQueue");

			JMSProducer producer = jmsContext.createProducer();

			producer.send(queue, "This is message from Kingshuk using JMS 2.0");

			JMSConsumer consumer = jmsContext.createConsumer(queue);

			String messageBody = consumer.receiveBody(String.class);

			System.out.println(messageBody);
		} catch (NamingException e) {
		} finally {

		}
	}

}
