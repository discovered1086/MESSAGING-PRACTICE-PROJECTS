package com.kingshuk.messaging.basicqueue;

import com.kingshuk.messaging.util.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class MessageReceiver {
    public static void main(String[] args) throws IOException, TimeoutException {
        //Get the connection factory
        ConnectionFactory connectionFactory = RabbitMQUtils.getConnectionFactory();

        //Get a new connection
        Connection connection = connectionFactory.newConnection();

        //Create a channel from the connection
        Channel channel = connection.createChannel();

        //Declare the queue
        channel.queueDeclare("firstQueue", false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback callback = ((consumerTag, message) -> {
            String bufferMessage = new String(message.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + bufferMessage + "'");
        });

        channel.basicConsume("firstQueue", true, callback, consumerTag -> {
        });
    }
}
