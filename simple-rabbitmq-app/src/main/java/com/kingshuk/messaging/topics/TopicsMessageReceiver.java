package com.kingshuk.messaging.topics;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

public class TopicsMessageReceiver {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare("animals", BuiltinExchangeType.TOPIC);

        String queueName = channel.queueDeclare().getQueue();

        if (args.length < 1) {
            System.err.println("Usage: ReceiveLogsDirect [info] [warning] [error]");
            System.exit(1);
        }

        Arrays.asList(args).forEach(s -> {
            try {
                channel.queueBind(queueName, "animals", s);
                System.out.print("I'm listeneing to: " + s);
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
