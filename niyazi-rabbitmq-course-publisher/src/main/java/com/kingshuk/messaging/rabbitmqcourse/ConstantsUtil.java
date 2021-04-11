package com.kingshuk.messaging.rabbitmqcourse;

public class ConstantsUtil {
    public static final String STUDENT_QUEUE="student-queue";
    public static final String DEFAULT_DIRECT="amq.direct";
    public static final String STUDENT_ROUTING_KEY="student-routing-key";

    private ConstantsUtil(){
        throw new UnsupportedOperationException("This is not allowed");
    }
}
