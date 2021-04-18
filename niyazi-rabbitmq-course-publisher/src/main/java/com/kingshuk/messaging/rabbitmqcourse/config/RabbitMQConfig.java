package com.kingshuk.messaging.rabbitmqcourse.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.kingshuk.messaging.rabbitmqcourse.model.RabbitMQProperties;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import static com.kingshuk.messaging.rabbitmqcourse.ConstantsUtil.*;

@Configuration
@PropertySource("classpath:rabbitmq.properties")
@EnableConfigurationProperties(RabbitMQProperties.class)
public class RabbitMQConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        return objectMapper;
    }

    @Bean
    public Queue studentQueue() {
        return new Queue(STUDENT_QUEUE, true);
    }

    @Bean
    public Queue studentScoreQueue() {
        return QueueBuilder
                .durable(STUDENT_SCORE_QUEUE)
                //.autoDelete()
                //.exclusive()
                .build();
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DEFAULT_DIRECT, true, false);
    }

    @Bean
    public DirectExchange studentExchange(){
        return ExchangeBuilder.directExchange(STUDENT_DIRECT)
                .durable(true)
                //.autoDelete()
                .build();
    }

    @Bean
    public Binding myBindings(@Qualifier("directExchange") DirectExchange exchange,
                              @Qualifier("studentQueue") Queue queue) {
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with(STUDENT_ROUTING_KEY);
    }

    @Bean
    public Binding studentBindings(@Qualifier("studentExchange") DirectExchange exchange,
                              @Qualifier("studentScoreQueue") Queue queue) {
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with(STUDENT_ROUTING_KEY);
    }

    @Bean
    public ConnectionFactory rabbitMQConnectionFactory(RabbitMQProperties rabbitMQProperties) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(rabbitMQProperties.getHost());
        connectionFactory.setPort(rabbitMQProperties.getPort());
        connectionFactory.setUsername(rabbitMQProperties.getUsername());
        connectionFactory.setPassword(rabbitMQProperties.getPassword());
        //connectionFactory.setCacheMode(CacheMode.CHANNEL);
        return connectionFactory;
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter(objectMapper());
    }
}
