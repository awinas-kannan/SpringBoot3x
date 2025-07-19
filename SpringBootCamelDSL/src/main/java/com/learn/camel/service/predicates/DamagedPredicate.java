package com.learn.camel.service.predicates;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.springframework.stereotype.Component;

@Component
public class DamagedPredicate implements Predicate {
	@Override
	public boolean matches(Exchange exchange) {
		String body = exchange.getIn().getBody(String.class);
		return body != null && body.contains("type=damaged");
	}
}