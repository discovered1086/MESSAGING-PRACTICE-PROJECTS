package com.kingshuk.messaging.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @RabbitListener(queues = "timotius.course")
    public void readMessage(String message){
        logger.info("The message is: {}", message);
    }
}
