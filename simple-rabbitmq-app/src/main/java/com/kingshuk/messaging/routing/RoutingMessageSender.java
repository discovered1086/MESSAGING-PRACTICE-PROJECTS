package com.kingshuk.messaging.routing;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class RoutingMessageSender {

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel();) {
            channel.exchangeDeclare("routing_logs", BuiltinExchangeType.DIRECT);

            extracted(channel,
                    "Hello from Kingshuk. I'm in the routing - info zone....!!",
                    "Info");
            extracted(channel,
                    "Hello from Kingshuk. I'm in the routing - Warning zone....!!",
                    "Warning");
            extracted(channel,
                    "Hello from Kingshuk. I'm in the routing - Error zone....!!",
                    "Error");
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void extracted(Channel channel,
                                  String message,
                                  String routingKey) throws IOException {

        channel.basicPublish("routing_logs", routingKey, null,
                message.getBytes(StandardCharsets.UTF_8));
        System.out.println(" [x] Sent '" + message + "'");
    }
}
