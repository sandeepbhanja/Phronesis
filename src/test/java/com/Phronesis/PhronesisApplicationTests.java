package com.Phronesis;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.client.RestTestClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
class PhronesisApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private RestTestClient restTestClient;

	@Test
	void greetingShouldReturnDefaultMessage() {
		restTestClient.get()
				.uri("http://localhost:%d/health?isHealthy=true".formatted(port))
				.exchange()
				.expectBody(Map.class)
				.isEqualTo(Map.of("success", true));
	}

}
