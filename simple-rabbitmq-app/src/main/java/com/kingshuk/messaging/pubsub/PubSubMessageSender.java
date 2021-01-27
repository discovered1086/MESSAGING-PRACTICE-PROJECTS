package com.kingshuk.messaging.pubsub;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class PubSubMessageSender {

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel();) {
            channel.exchangeDeclare("logs", BuiltinExchangeType.FANOUT);

            //channel.queueDeclare("pubSubQueue", false, false, false, null);
//            String queueName = channel.queueDeclare().getQueue();
//            channel.queueBind(queueName, "logs", "");
            String message = "Hello from Kingshuk. I'm in the pubsub zone....!!";
            channel.basicPublish("logs", "", null,
                    message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }
    }
}
