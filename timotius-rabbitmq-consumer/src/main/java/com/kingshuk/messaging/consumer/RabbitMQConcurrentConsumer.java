package com.kingshuk.messaging.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Service
public class RabbitMQConcurrentConsumer {
    private static final Logger LOGGER= LoggerFactory.getLogger(RabbitMQConcurrentConsumer.class);

    @RabbitListener(queues = "timotius.multi-consumer-fixed-rate", concurrency = "3-5")
    public void readMessage(String message) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextLong(2000));
        LOGGER.info("Message consumer: {} | The counter value is: {}", Thread.currentThread().getName(), message);
    }
}
