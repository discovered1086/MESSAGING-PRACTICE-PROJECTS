package com.kingshuk.messaging.springjms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MessageListener {

	@JmsListener(destination = "${springjms.queueName}")
	public void receiveMessage(String message) {
		log.info("Inside the message receiver method.....");
		log.info("The message is {}", message);
	}
}
