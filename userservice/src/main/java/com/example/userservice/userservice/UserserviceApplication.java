package com.example.userservice.userservice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
public class UserserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserserviceApplication.class, args);
	}


	/*
	@RefreshScope
	@RestController
	class MessageRestController {

		@Value("${message:Hello default}")
		private String message;

		@RequestMapping("/message")
		String getMessage() {
			return this.message;
		}

		@PostMapping("/refresh")
	   String setMessage(@RequestBody String message) {
			return this.setMessage(message);
		}
	}
	*/
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
}