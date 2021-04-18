package com.kingshuk.messaging.rabbitmqcourse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kingshuk.messaging.rabbitmqcourse.publisher.RabbitMQMessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitmqCourseApplication implements CommandLineRunner {

    @Autowired
    private RabbitMQMessagePublisher publisher;

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqCourseApplication.class, args);
    }

    @Override
    public void run(String... args) throws JsonProcessingException {
        //publisher.publishMessage();
        //publisher.publishObjectMessage();
        publisher.publishStudentScoreMessage();
    }
}
