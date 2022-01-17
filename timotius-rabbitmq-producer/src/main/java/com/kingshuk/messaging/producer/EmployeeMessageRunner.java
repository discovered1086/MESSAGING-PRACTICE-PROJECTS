package com.kingshuk.messaging.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMessageRunner implements CommandLineRunner {

    @Autowired
    private EmployeeMessageProducer employeeMessageProducer;

    @Override
    public void run(String... args) throws JsonProcessingException {
        employeeMessageProducer.generateEmployeeMessage();
    }
}
