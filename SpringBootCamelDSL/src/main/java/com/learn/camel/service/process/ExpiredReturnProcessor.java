package com.learn.camel.service.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component("expiredReturnProcessor")
public class ExpiredReturnProcessor implements Processor {
    @Override
    public void process(Exchange exchange) {
        String input = exchange.getIn().getBody(String.class);
        exchange.getMessage().setBody("🧪 Expired return processed: " + input);
    }
}
