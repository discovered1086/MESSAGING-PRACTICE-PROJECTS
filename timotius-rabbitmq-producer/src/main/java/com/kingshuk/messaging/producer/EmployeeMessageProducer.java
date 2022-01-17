package com.kingshuk.messaging.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingshuk.messaging.producer.message.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;

@Component
public class EmployeeMessageProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeMessageProducer.class);
    private static final String QUEUE_NAME="timotius.course.employee";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    @Qualifier("rabbitMQObjectMapper")
    private ObjectMapper objectMapper;

    public void generateEmployeeMessage() throws JsonProcessingException {
        var employeeObject = Employee.builder()
                .employeeId("232649")
                .name("Kingshuk Mukherjee")
                .dob(LocalDate.of(1986, Month.OCTOBER, 16)).build();

        final var employee = objectMapper.writeValueAsString(employeeObject);

        LOGGER.info("The employee object is {}", employeeObject);

        rabbitTemplate.convertAndSend(QUEUE_NAME, employee);

    }
}
