package org.microservice.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MicroserviceChatApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceChatApplication.class, args);
	}

}
