package com.bfb.bfbac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.bfb.api")
public class BfbAcApplication {

	public static void main(String[] args) {
		SpringApplication.run(BfbAcApplication.class, args);
	}

}
