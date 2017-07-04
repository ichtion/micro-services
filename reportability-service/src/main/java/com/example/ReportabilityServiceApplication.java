package com.example;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.util.Arrays.asList;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@SpringBootApplication
@EnableEurekaClient
@RestController
@EnableHystrix
@EnableFeignClients
public class ReportabilityServiceApplication {
	private Logger logger = LoggerFactory.getLogger(ReportabilityServiceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ReportabilityServiceApplication.class, args);
	}

	@Autowired EligibilityService eligibilityService;
	@Autowired private DiscoveryClient discoveryClient;
	@Autowired private RestTemplate restTemplate;

	@RequestMapping(value = "/tradeId/{id}", method = GET)
	@HystrixCommand(fallbackMethod = "hystrixEligibiltyFallbackMethod")
	public @ResponseBody String getReport(@PathVariable("id") String tradeId) {
		logger.info("Get report for tradeId {}", tradeId);
		String response = eligibilityService.eligibility();
		return tradeId + " :: " + response;
	}

	private String hystrixEligibiltyFallbackMethod(String tradeId) {
		return tradeId + " :: eligible-hystrix";
	}

	@RequestMapping(value = "/tradeIds/{ids}", method = GET)
	public @ResponseBody List<String> getReportForTradeIds(@PathVariable("ids") String tradeIds) {
		logger.info("Get report for tradeIds {}", tradeIds);
		List<String> listOftradeIds = asList(tradeIds.split(","));
		CheckTradesEligibilityCommand checkTradesEligibilityCommand = new CheckTradesEligibilityCommand(listOftradeIds, restTemplate);
		return checkTradesEligibilityCommand.execute();
	}

	@RequestMapping(value = "/services", method = GET)
	public @ResponseBody List<String> discover() {
		return discoveryClient.getServices();
	}

	@RequestMapping(value = "/service/{serviceName}", method = GET)
	public @ResponseBody List<ServiceInstance> discoverService(@PathVariable("serviceName") String serviceName) {
		return discoveryClient.getInstances(serviceName);
	}

	@Bean
	@LoadBalanced
	public RestTemplate balancedRestTemplate() {
		return new RestTemplate();
	}
}


