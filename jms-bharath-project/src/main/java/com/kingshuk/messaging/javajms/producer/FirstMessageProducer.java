package com.kingshuk.messaging.javajms.producer;

import javax.jms.ConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class FirstMessageProducer {

	public static void main(String[] args) {
		InitialContext context = null;

		try {
			context = new InitialContext();
			ConnectionFactory connectionFactory = (ConnectionFactory) context
					.lookup("connectionFactory.ConnectionFactory");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

}
