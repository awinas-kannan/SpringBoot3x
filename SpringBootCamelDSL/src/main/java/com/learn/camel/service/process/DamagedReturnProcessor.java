package com.learn.camel.service.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component("damagedReturnProcessor")
public class DamagedReturnProcessor implements Processor {
    @Override
    public void process(Exchange exchange) {
        String input = exchange.getIn().getBody(String.class);
        exchange.getMessage().setBody("📦 Damaged return processed: " + input);
    }
}
