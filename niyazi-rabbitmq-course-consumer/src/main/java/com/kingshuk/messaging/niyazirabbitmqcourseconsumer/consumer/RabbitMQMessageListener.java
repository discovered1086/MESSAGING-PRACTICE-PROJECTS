package com.kingshuk.messaging.niyazirabbitmqcourseconsumer.consumer;

import com.kingshuk.messaging.niyazirabbitmqcourseconsumer.ConstantsUtil;
import com.kingshuk.messaging.niyazirabbitmqcourseconsumer.model.Student;
import com.kingshuk.messaging.niyazirabbitmqcourseconsumer.model.StudentTestScore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQMessageListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitListener(queues = ConstantsUtil.STUDENT_QUEUE,
            messageConverter = "messageConverter")
    public void onStudentMessage(Student student) {
        logger.info("The student is: {}", student);
    }

    @RabbitListener(queues = ConstantsUtil.STUDENT_SCORE_QUEUE,
            messageConverter = "messageConverter")
    public void onStudentTestScoreMessage(StudentTestScore testScore) {
        logger.info("The score is: {}", testScore);
    }
}
