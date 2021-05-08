package com.kingshuk.messaging.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@Component
public class SendMessageRunner implements CommandLineRunner {

    @Autowired
    private RabbitMQProducer rabbitMQProducer;

    @Override
    public void run(String... args) {
        rabbitMQProducer.sendMessage("Kingshuk");
    }
}
