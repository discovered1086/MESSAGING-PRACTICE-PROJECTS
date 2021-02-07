package com.kingshuk.messaging.publisherconfirms;

import com.kingshuk.messaging.util.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeoutException;
import java.util.function.BooleanSupplier;

public class PublisherConfirmAsynchronousSender {

    private static ConcurrentMap<Long, String> publisherMap = new ConcurrentHashMap<>();

    public static void main(String[] args) throws IOException, TimeoutException {
        Queue<String> messages = RabbitMQUtils.getMessages();
        ConnectionFactory connectionFactory = RabbitMQUtils.getConnectionFactory();
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel();) {
            channel.confirmSelect();
            final String queue = channel.queueDeclare().getQueue();

            channel.addConfirmListener((sequenceNumber, multiple) -> {
                System.out.println("Have received confirmation from the broker for the message sequence: " + sequenceNumber);
                publisherMap.remove(sequenceNumber);
            }, (sequenceNumber, multiple) -> {
                String body = publisherMap.get(sequenceNumber);
                System.err.format(
                        "Message with body %s has been nack-ed. Sequence number: %d, multiple: %b%n",
                        body, sequenceNumber, multiple);
                publisherMap.remove(sequenceNumber);
            });


            while (hasMoreMessagesToPublish(messages)) {
                final long nextPublishSeqNo = channel.getNextPublishSeqNo();
                final String message = messages.poll();
                publisherMap.put(nextPublishSeqNo, message);
                sendMessage(channel, queue, message);
            }

//            int waitedFor = 0;
//
//            while (waitedFor <= Duration.ofMinutes(2).toMillis() && !publisherMap.isEmpty()) {
//                Thread.sleep(5000);
//                waitedFor = waitedFor + 5000;
//                System.out.println("Waiting for 5 more seconds...");
//                System.out.println("Size of the publisher map: " + publisherMap.size());
//            }

            if (!waitUntil(Duration.ofSeconds(60), publisherMap::isEmpty)) {
                throw new IllegalStateException("All messages could not be confirmed in 60 seconds");
            }

        } catch (TimeoutException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    static boolean waitUntil(Duration timeout, BooleanSupplier condition) throws InterruptedException {
        int waited = 0;
        while (!condition.getAsBoolean() && waited < timeout.toMillis()) {
            Thread.sleep(100L);
            waited = +100;
        }
        return condition.getAsBoolean();
    }

    private static boolean hasMoreMessagesToPublish(Queue<String> messages) {
        return !messages.isEmpty();
    }

    private static void sendMessage(Channel channel,
                                    String queue,
                                    String message) throws IOException {

        channel.basicPublish("", queue, null, message.getBytes(StandardCharsets.UTF_8));
        System.out.println(" [x] Sent '" + message + "'");
    }
}
