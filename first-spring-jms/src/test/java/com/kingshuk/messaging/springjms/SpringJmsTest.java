package com.kingshuk.messaging.springjms;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringJmsTest {
	
	@Autowired
	MessageSender sender;

	@Test
	void test() {
		sender.sendMessage("Hello Spring JMS");
	}

}
