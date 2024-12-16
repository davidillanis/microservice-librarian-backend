package org.microservice.issue;

import org.microservice.issue.service.implementation.AddDataServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MicroserviceIssueApplication implements CommandLineRunner {
	@Autowired
	private AddDataServiceImpl addDataService;

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceIssueApplication.class, args);
	}

	@Override
	public void run(String... args)  {
		//addDataService.addData();
	}
}
