package com.kingshuk.messaging.springjms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class FirstSpringJmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstSpringJmsApplication.class, args);
	}

}
