package com.kingshuk.messaging.rabbitmqcourse.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingshuk.messaging.rabbitmqcourse.model.Student;
import com.kingshuk.messaging.rabbitmqcourse.model.StudentTestScore;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static com.kingshuk.messaging.rabbitmqcourse.ConstantsUtil.*;

@Component
@AllArgsConstructor
public class RabbitMQMessagePublisher {

    private final ObjectMapper objectMapper;

    private final RabbitTemplate rabbitTemplate;

    public void publishMessage() {
        rabbitTemplate.convertAndSend(DEFAULT_DIRECT, "rabbitmq-course-key",
                "This is first message using rabbit template");
    }

    public void publishObjectMessage() throws JsonProcessingException {
        Message message = MessageBuilder.withBody(objectMapper.writeValueAsString(
                Student.builder()
                        .studentId(878789)
                        .name("Kingshuk Mukherjee")
                        .testScore(Collections.singletonList(StudentTestScore.builder()
                                .testScore(98.74).semester("1st").build())).build()).getBytes(StandardCharsets.UTF_8))
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .build();

        rabbitTemplate.convertAndSend(DEFAULT_DIRECT, STUDENT_ROUTING_KEY, message);
    }

    public void publishStudentScoreMessage() throws JsonProcessingException {
        Message message = MessageBuilder.withBody(objectMapper.writeValueAsString(
                StudentTestScore.builder()
                        .testScore(98.74).semester("1st").build()).getBytes(StandardCharsets.UTF_8))
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .build();

        rabbitTemplate.convertAndSend(STUDENT_DIRECT, STUDENT_ROUTING_KEY, message);
    }
}
