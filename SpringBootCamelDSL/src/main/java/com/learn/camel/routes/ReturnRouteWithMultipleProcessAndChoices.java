package com.learn.camel.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.learn.camel.service.predicates.ReturnTypePredicate;
import com.learn.camel.service.process.DamagedReturnProcessor;

@Component
public class ReturnRouteWithMultipleProcessAndChoices extends RouteBuilder {

	@Autowired DamagedReturnProcessor damagedReturnProcessor;
	
    @Override
    public void configure() {

        from("direct:return-multi-process")
            .routeId("direct:return-multi-process")
            .log("1️⃣ Return request received: ${body}")
            .process("returnValidator")

            .choice()
                .when(method(ReturnTypePredicate.class, "isDamaged"))
                    .process(damagedReturnProcessor)
                .when(method(ReturnTypePredicate.class, "isExpired"))
                    .process("expiredReturnProcessor")
                .otherwise()
                    .process("defaultReturnProcessor")
            .end()

            .log("✅ Return flow completed: ${body}");
    }
}
