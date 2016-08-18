package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class EligibilityServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EligibilityServiceApplication.class, args);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String isEligible() {
		return "eligibility_01";
	}

}
