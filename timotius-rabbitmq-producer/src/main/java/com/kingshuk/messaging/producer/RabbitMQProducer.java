package com.kingshuk.messaging.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQProducer {
    private static final String QUEUE_NAME = "timotius.course";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String name){
        rabbitTemplate.convertAndSend(QUEUE_NAME, String.format("Hello %s", name));
    }
}
