package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class EligibilityServiceApplication {
    private Logger logger = LoggerFactory.getLogger(EligibilityServiceApplication.class);

    @Value("${eligibility.response}")
    private String eligibilityResponse;

    @Autowired Tracer tracer;

    public static void main(String[] args) {
        SpringApplication.run(EligibilityServiceApplication.class, args);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String isEligible() throws InterruptedException {
        logger.info("========> My message");

        tracer.addTag("myTagName", "1");
        Span myOwnSpan = tracer.createSpan("My own span");
        TimeUnit.MILLISECONDS.sleep(100);
        myOwnSpan.logEvent("My own event");
        tracer.addTag("myTagName", "2");
        TimeUnit.MILLISECONDS.sleep(100);
        tracer.close(myOwnSpan);

        return eligibilityResponse;
    }

}
