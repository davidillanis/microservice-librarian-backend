package org.microservice.utils;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MicroserviceUtilsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceUtilsApplication.class, args);
	}

}
