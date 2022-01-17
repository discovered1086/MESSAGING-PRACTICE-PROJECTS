package com.kingshuk.messaging.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableScheduling
public class TimotiusRabbitmqProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimotiusRabbitmqProducerApplication.class, args);
    }
}
