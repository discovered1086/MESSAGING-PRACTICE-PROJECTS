package com.kingshuk.messaging.workerqueue;

import com.kingshuk.messaging.util.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class NewTask {

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = RabbitMQUtils.getConnectionFactory();

        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel();) {

            channel.queueDeclare("firstQueue", false, false, false, null);
            String message = String.join(" ", args);
            channel.basicPublish("", "taskQueue", null,
                    message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }
    }
}