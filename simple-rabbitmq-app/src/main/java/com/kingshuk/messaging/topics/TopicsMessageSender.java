package com.kingshuk.messaging.topics;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class TopicsMessageSender {

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel();) {
            channel.exchangeDeclare("animals", BuiltinExchangeType.TOPIC);

            sendMessage(channel,
                    "Hello from Kingshuk. I'm in the tiger cage....!!",
                    "mammal.carnivorous.cat");
            sendMessage(channel,
                    "Hello from Kingshuk. I'm in Wolf cage....!!",
                    "mammal.carnivorous.dog");
            sendMessage(channel,
                    "Hello from Kingshuk. I'm in the crocodile lake...!!",
                    "reptile.carnivorous.none");

            sendMessage(channel,
                    "Hello from Kingshuk. I'm in the Cow shelter...!!",
                    "mammal.Herbivorous.none");
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendMessage(Channel channel,
                                  String message,
                                  String routingKey) throws IOException {

        channel.basicPublish("animals", routingKey, null,
                message.getBytes(StandardCharsets.UTF_8));
        System.out.println(" [x] Sent '" + message + "'");
    }
}
