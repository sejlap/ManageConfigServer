package com.example.rentservice.rentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;


//@EnableEurekaClient
@SpringBootApplication
public class RentserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RentserviceApplication.class, args);
	}

}
@RefreshScope
@RestController
@RequestMapping("/api/test")
class TestController {

	@Value("${test.name}")
	private String name;

	@GetMapping
	public String test() {
		return name;
	}
}