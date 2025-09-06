package com.jobms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class JobmsEmbarkxProjApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobmsEmbarkxProjApplication.class, args);
	}

}
