package com.example;

import com.google.common.collect.ImmutableList;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class CheckTradesEligibilityCommand extends HystrixCommand<List<String>> {
    private final RestTemplate restTemplate;
    private final List<String> tradeIds;

    protected CheckTradesEligibilityCommand(List<String> tradeIds, RestTemplate restTemplate) {
        super(HystrixCommandGroupKey.Factory.asKey("CheckEligibility"));
        this.restTemplate = restTemplate;
        this.tradeIds = tradeIds;
    }

    @Override
    protected List<String> run() throws Exception {
        ImmutableList.Builder<String> responseBuilder = new ImmutableList.Builder<String>();
        for (String id: tradeIds) {
            ResponseEntity<String> response = restTemplate.getForEntity("http://EligibilityService/", String.class);
            responseBuilder.add("TradeId: " + id + "; response: " + response.getBody());
        }
        return responseBuilder.build();
    }

    @Override
    protected List<String> getFallback() {
        ImmutableList.Builder<String> responseBuilder = new ImmutableList.Builder<String>();
        for (String id: tradeIds) {
            responseBuilder.add("TradeId: " + id + "; response: fallback");
        }
        return responseBuilder.build();
    }
}
