package com.learn.camel.routes;



import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ReturnRouteWithReason extends RouteBuilder {
	@Override
	public void configure() {

		from("direct:return-request-reason")
		.routeId("retail-return-route")
		.log("Return Request Received: ${body}")
		.to("bean:returnProcessorService?method=process")
		.log("Return Processed: ${body}");
	}
}