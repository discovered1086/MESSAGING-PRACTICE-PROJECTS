package com.kingshuk.messaging.util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeoutException;

public class RabbitMQUtils {

    private RabbitMQUtils(){
        throw new UnsupportedOperationException("This is not allowed");
    }

    public static ConnectionFactory getConnectionFactory() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("123456");
        return connectionFactory;
    }


    public static Queue<String> getMessages() {
        final Queue<String> objects = new LinkedList<>();
        objects.add("First message from Kingshuk");
        objects.add("Second message from Kingshuk");
        objects.add("Third message from Kingshuk");
        objects.add("Fourth message from Kingshuk");
        objects.add("Fifth message from Kingshuk");
        objects.add("Sixth message from Kingshuk");
        return objects;
    }
}
