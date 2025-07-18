package com.learn.camel;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class SpringBootCamelDslApplication implements CommandLineRunner {

	@Autowired
	private ProducerTemplate producerTemplate;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCamelDslApplication.class, args);
	}
	
	/*
	 *
	 *
	 
Customer Return Request --> [Scatter] --> 
   ├── UpdateInventory      (parallel)
   ├── ProcessRefund        (parallel)
   └── SendNotification     (parallel)
                            ↓
                    [Gather + Aggregate]
                            ↓
               Final response: Return Completed


🚦 In a Parallel Processing Scenario (e.g., with .split().parallelProcessing())
When you use .split().parallelProcessing() followed by .aggregate(), each split part is processed in parallel.

Aggregator then waits based on the completion condition you define (e.g., timeout, size, custom predicate).

	 */

	@Override
	public void run(String... args) throws Exception {
		
		try {
			Thread.sleep(3000);
		} catch (

		InterruptedException ignored) {
		}
		
		
		Object response = producerTemplate.requestBody("direct:return-request", "ReturnRequest#123");
		log.info("Aggregated Return Result: {}" , response);
		
		response = producerTemplate.requestBody("direct:return-request", "ReturnRequest#VIP");
		log.info("Aggregated Return Result: {}" , response);
		
		response = producerTemplate.requestBody("direct:return-request-reason", "ReturnID:123, Product:ABC, Reason:Damaged");
		log.info("Return Return Result: {}" , response);
	}
}
