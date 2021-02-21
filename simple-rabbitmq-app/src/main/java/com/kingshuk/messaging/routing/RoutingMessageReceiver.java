package com.kingshuk.messaging.routing;

import com.kingshuk.messaging.util.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

public class RoutingMessageReceiver {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = RabbitMQUtils.getConnectionFactory();
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare("routing_logs", BuiltinExchangeType.DIRECT);

        String queueName = channel.queueDeclare().getQueue();

        if (args.length < 1) {
            System.err.println("Usage: ReceiveLogsDirect [info] [warning] [error]");
            System.exit(1);
        }

        performBinding(args, channel, queueName);

        System.out.println(" [*] Waiting for messages from routing queues. To exit press CTRL+C");

        DeliverCallback callback = ((consumerTag, message) -> {
            String bufferMessage = new String(message.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + bufferMessage + "'");
        });

        channel.basicConsume(queueName, true, callback, consumerTag -> {
        });
    }

    private static void performBinding(String[] args, Channel channel, String queueName) {
        Arrays.asList(args).forEach(s -> {
            try {
                channel.queueBind(queueName, "routing_logs", s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
