package org.microservice.librarian;

import org.microservice.librarian.client.UserRoleClient;
import org.microservice.librarian.model.entity.LoanEntity;
import org.microservice.librarian.model.repository.LoanRepository;
import org.microservice.librarian.service.implentation.AddDataServiceImpl;
import org.microservice.librarian.util.dto.UserEntityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class MicroserviceLibrarianApplication implements CommandLineRunner {
	@Autowired
	private AddDataServiceImpl addDataService;

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceLibrarianApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		addDataService.addDataBase();
	}
}
