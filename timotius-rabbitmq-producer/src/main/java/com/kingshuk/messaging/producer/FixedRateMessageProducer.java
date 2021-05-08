package com.kingshuk.messaging.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FixedRateMessageProducer {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String QUEUE_NAME = "timotius.fixed-rate";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private int counter = 0;

    @Scheduled(fixedRate = 5000)
    public void sendMessage(){
        counter++;
        logger.info("The counter value is {}",counter);
        rabbitTemplate.convertAndSend(QUEUE_NAME, counter);
    }
}
