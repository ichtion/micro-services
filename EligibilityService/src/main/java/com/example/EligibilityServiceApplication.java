package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
    private Logger logger = LoggerFactory.getLogger(EligibilityServiceApplication.class);

    @Value("${eligibility.response}")
    private String eligibilityResponse;

    public static void main(String[] args) {
        SpringApplication.run(EligibilityServiceApplication.class, args);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String isEligible() {
        logger.info("========> My message");
        return eligibilityResponse;
    }

}
