package com.kingshuk.messaging.routing;

import com.kingshuk.messaging.util.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
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

        Arrays.asList(args).forEach(s -> {
            try {
                channel.queueBind(queueName, "routing_logs", s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback callback = ((consumerTag, message) -> {
            String bufferMessage = new String(message.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + bufferMessage + "'");
        });

        channel.basicConsume(queueName, true, callback, consumerTag -> {
        });
    }
}
