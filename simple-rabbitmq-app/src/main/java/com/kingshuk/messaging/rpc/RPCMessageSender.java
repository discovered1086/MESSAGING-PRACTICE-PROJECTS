package com.kingshuk.messaging.rpc;

import com.kingshuk.messaging.util.RabbitMQUtils;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class RPCMessageSender {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = RabbitMQUtils.getConnectionFactory();
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel();) {

            String callBackQueue = channel.queueDeclare().getQueue();

            BasicProperties properties = new BasicProperties()
                                .builder()
                                .replyTo(callBackQueue)
                                .build();

            sendMessage(channel, properties);
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendMessage(Channel channel,
                                    BasicProperties properties) throws IOException {

        channel.basicPublish("", "rpc_queue", properties,
                "This is an RPC message from Kingshuk".getBytes(StandardCharsets.UTF_8));
        System.out.println(" [x] Sent '" + "This is an RPC message from Kingshuk" + "'");
    }

}
