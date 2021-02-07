package com.kingshuk.messaging.pubsub;

import com.kingshuk.messaging.util.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class PubSubMessageReceiver {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = RabbitMQUtils.getConnectionFactory();
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare("logs", BuiltinExchangeType.FANOUT);

        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, "logs", "");
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback callback = ((consumerTag, message) -> {
            String bufferMessage = new String(message.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + bufferMessage + "'");
        });

        channel.basicConsume(queueName, true, callback, consumerTag -> {
        });
    }
}
