package com.kingshuk.messaging.springjms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MessageSender {
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Value("${springjms.queueName}")
	private String queueName;
	
	public void sendMessage(String message) {
		log.info("Inside the message sender method.....");
		jmsTemplate.convertAndSend(queueName,message);
	}

}
