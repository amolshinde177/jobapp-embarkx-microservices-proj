package com.companyms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CompanymsEmbarkxProjApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompanymsEmbarkxProjApplication.class, args);
	}

}
