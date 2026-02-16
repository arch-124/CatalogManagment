package com.example.Catalog.Managment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CatalogManagmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogManagmentApplication.class, args);
	}

}
