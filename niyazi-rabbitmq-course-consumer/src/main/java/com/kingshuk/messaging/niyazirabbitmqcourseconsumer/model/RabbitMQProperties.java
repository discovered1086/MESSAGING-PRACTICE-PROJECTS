package com.kingshuk.messaging.niyazirabbitmqcourseconsumer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "rabbitmq")
public class RabbitMQProperties {

    private String host;

    private int port;

    private String username;

    private String password;
}
