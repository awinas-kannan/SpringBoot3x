package com.learn.camel.routes;


import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ReturnRouteWithExchange extends RouteBuilder {
	
    @Override
    public void configure() {
        from("direct:return-exchange")
            .routeId("retail-return-exchange-route")
            .log("Received Return Request - Headers: ${headers}, Body: ${body}")
            .process(exchange -> {
                String returnId = exchange.getIn().getHeader("ReturnID", String.class);
                String product = exchange.getIn().getHeader("ProductCode", String.class);
                String reason = exchange.getIn().getBody(String.class);

                String result = String.format("Return [%s] for product [%s] is being processed for reason: %s", 
                                               returnId, product, reason);

                // Setting response back in body
                exchange.getMessage().setBody(result);
            })
            .log("Processed Return Exchange: ${body}");
    }
}