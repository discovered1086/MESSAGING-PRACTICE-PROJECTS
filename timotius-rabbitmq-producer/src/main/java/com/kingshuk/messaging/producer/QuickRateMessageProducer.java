package com.kingshuk.messaging.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class QuickRateMessageProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuickRateMessageProducer.class);
    private static final String QUEUE_NAME = "timotius.multi-consumer-fixed-rate";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private int counter = 0;

    @Scheduled(fixedRate = 500)
    public void sendMessage(){
        counter++;
        LOGGER.info("Multiple consumer check: the counter value is {}",counter);
        rabbitTemplate.convertAndSend(QUEUE_NAME, counter);
    }
}
