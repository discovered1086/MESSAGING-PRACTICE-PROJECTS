package com.kingshuk.messaging.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQFixedRateConsumer {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @RabbitListener(queues = "timotius.fixed-rate")
    public void readMessage(String message){
        logger.info("The counter value is: {}", message);
    }
}
