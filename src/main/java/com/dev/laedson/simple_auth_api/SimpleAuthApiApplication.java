package com.dev.laedson.simple_auth_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SimpleAuthApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleAuthApiApplication.class, args);
	}

}
