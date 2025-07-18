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


	 */

	@Override
	public void run(String... args) throws Exception {
		
		try {
			Thread.sleep(5000);
		} catch (

		InterruptedException ignored) {
		}
		
		
		Object response = producerTemplate.requestBody("direct:return-request", "ReturnRequest#123");
		log.info("Aggregated Return Result: {}" , response);
		
		response = producerTemplate.requestBody("direct:return-request", "ReturnRequest#VIP");
		log.info("Aggregated Return Result: {}" , response);
	}
}
