package com.kingshuk.messaging.rabbitmqcourse;

public class ConstantsUtil {
    public static final String STUDENT_QUEUE="student-queue";
    public static final String STUDENT_SCORE_QUEUE="student-score-queue";
    public static final String DEFAULT_DIRECT="amq.direct";
    public static final String STUDENT_DIRECT="student.direct";
    public static final String EMPLOYEE_TOPIC="employee.topic";
    public static final String NOTIFICATION_EXCHANGE="notification.fanout";
    public static final String HEADERS_EXCHANGE="test.headers";
    public static final String STUDENT_ROUTING_KEY="student-routing-key";
    public static final String EMPLOYEE_ROUTING_KEY="employee-routing-key";

    private ConstantsUtil(){
        throw new UnsupportedOperationException("This is not allowed");
    }
}
