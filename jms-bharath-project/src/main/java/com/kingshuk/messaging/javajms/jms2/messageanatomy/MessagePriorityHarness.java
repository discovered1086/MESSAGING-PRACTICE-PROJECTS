package com.kingshuk.messaging.javajms.jms2.messageanatomy;

import java.util.Objects;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessagePriorityHarness {

	public static void main(String[] args) {
		InitialContext context = null;
		Queue queue = null;
		JMSConsumer consumer = null;

		try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
				JMSContext jmsContext = connectionFactory.createContext();) {
			context = new InitialContext();

			queue = (Queue) context.lookup("queue/myQueue");

			JMSProducer producer = jmsContext.createProducer();

			producer.setPriority(7);
			producer.send(queue, "This is the FIRST message from Kingshuk using JMS 2.0");

			producer.setPriority(5);
			producer.send(queue, "This is the SECOND message from Kingshuk using JMS 2.0");

			producer.setPriority(8);
			producer.send(queue, "This is the THIRD message from Kingshuk using JMS 2.0");

			consumer = jmsContext.createConsumer(queue);

			for (int i = 0; i < 3; i++) {
				System.out.println(consumer.receiveBody(String.class));
			}
		} catch (NamingException e) {
			System.out.println("Something went wrong....");
		} finally {
			if (Objects.nonNull(consumer)) {
				consumer.close();
			}
		}
	}

}
