package com.example;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("EligibilityService")
public interface EligibilityService {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    String eligibility();
}
