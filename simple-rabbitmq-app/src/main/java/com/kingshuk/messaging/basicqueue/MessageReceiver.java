package com.kingshuk.messaging.basicqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MessageReceiver {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare("firstQueue", false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback callback = ((consumerTag, message) -> {
            String bufferMessage = new String(message.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + bufferMessage + "'");
        });

        channel.basicConsume("firstQueue", true, callback, consumerTag -> {});
    }
}
