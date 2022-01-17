package com.kingshuk.messaging.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQFixedRateConsumer {
    private static final Logger LOGGER= LoggerFactory.getLogger(RabbitMQFixedRateConsumer.class);

    @RabbitListener(queues = "timotius.fixed-rate")
    public void readMessage(String message){
        LOGGER.info("The counter value is: {}", message);
    }
}
