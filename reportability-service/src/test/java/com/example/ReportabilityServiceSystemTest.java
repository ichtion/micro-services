package com.example;

import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static java.util.concurrent.Executors.newFixedThreadPool;

public class ReportabilityServiceSystemTest {
    private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void multipleInvocations() throws ExecutionException, InterruptedException {
        int poolSize = 20;
        ExecutorService executor = newFixedThreadPool(poolSize);
        List<Future<String>> jobs = new ArrayList<>();

        for (int i = 0; i < poolSize; i++) {
            jobs.add(executor.submit(
                    () -> restTemplate
                            .getForEntity("http://localhost:9001/tradeId/1234", String.class)
                            .getBody()
            ));
        }

        waitTillAllJobsAreDone(jobs);
    }

    private void waitTillAllJobsAreDone(List<Future<String>> jobs) throws ExecutionException, InterruptedException {
        for (Future<String> job : jobs) {
            System.out.println(job.get());
        }
    }
}
