package com.learn.camel.service.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component("returnValidator")
@Slf4j
public class ReturnValidator implements Processor {
	@Override
	public void process(Exchange exchange) {
		String body = exchange.getIn().getBody(String.class);
		if (!body.contains("ReturnID")) {
			throw new IllegalArgumentException("Invalid return request");
		}
		log.info("✔️ Validated: " + body);
	}
}
