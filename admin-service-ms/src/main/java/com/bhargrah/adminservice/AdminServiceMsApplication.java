package com.bhargrah.adminservice;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAdminServer
@SpringBootApplication
public class AdminServiceMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminServiceMsApplication.class, args);
	}

}
