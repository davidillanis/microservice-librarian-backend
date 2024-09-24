package org.microservice.users;

import org.microservice.users.service.UserRoleService;
import org.microservice.users.service.implementation.AddDataServiceImpl;
import org.microservice.users.utils.dto.AuthCreateUserRequestDTO;
import org.microservice.users.utils.other.ERole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.List;

@EnableDiscoveryClient
@SpringBootApplication
public class MicroserviceUsersApplication implements CommandLineRunner {
	@Autowired
	private AddDataServiceImpl addDataService;

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceUsersApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		addDataService.add();
	}
}
