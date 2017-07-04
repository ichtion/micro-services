package com.example;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("EligibilityService")
public interface EligibilityService {
    @RequestMapping(value = "/eligibility/{tradeId}", method = RequestMethod.GET)
    Eligibility eligibility(@PathVariable(value = "tradeId") String tradeId);
}
