package com.learn.camel.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ReturnRequestRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		/*
		 * #############################################################
		 */
		
		// Scatter-Gather
		from("direct:return-request")
		.log("Received return request: ${body}")
		.split(method("scatterService", "scatter"))
		.parallelProcessing()
		.aggregationStrategy("aggregationService")
		.to("direct:process-return-task")
		.end()
		.log("Final Aggregated Response: ${body}");

		// Processing each individual task
		from("direct:process-return-task")
		.log("Processing task: ${body}")
		.bean("returnProcessorService", "process");
		
		/*
		 * #############################################################
		 */
	}
}
