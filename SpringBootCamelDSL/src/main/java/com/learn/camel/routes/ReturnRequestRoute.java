package com.learn.camel.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j

/*
 XML Equivaluent::
 
 <?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:camel="http://camel.apache.org/schema/spring"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="
         http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
         http://camel.apache.org/schema/spring http://camel.apache.org/schema/spring/camel-spring.xsd">

    <camel:camelContext id="camel-server" xmlns="http://camel.apache.org/schema/spring">
        <camel:route id="retail-return-route">
            <camel:from uri="direct:return-request"/>
            <camel:log message="Received return request: ${body}" />
            <camel:split strategyRef="aggregationService" parallelProcessing="true">
                <camel:method ref="scatterService" method="scatter"/>
                <camel:to uri="direct:process-return-task"/>
            </camel:split>
            <camel:log message="Aggregated Result: ${body}" />
        </camel:route>

        <camel:route id="task-processing-route">
            <camel:from uri="direct:process-return-task"/>
            <camel:log message="Processing task: ${body}" />
            <camel:bean ref="returnProcessorService" method="process"/>
        </camel:route>
    </camel:camelContext>

    <bean id="scatterService" class="com.example.retailreturns.ScatterService"/>
    <bean id="returnProcessorService" class="com.example.retailreturns.ReturnProcessorService"/>
    <bean id="aggregationService" class="com.example.retailreturns.AggregationService"/>

</beans>
 
 * */
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

