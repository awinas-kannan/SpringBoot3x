package com.learn.camel.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AggregationService implements AggregationStrategy {
	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		log.info("AggregationService aggregate {} {} ", oldExchange, newExchange);
		
		String newVal = newExchange.getMessage().getBody(String.class);
		
		if (oldExchange == null) {
			newExchange.getMessage().setBody(new ArrayList<>(List.of(newVal)));
			return newExchange;
		} else {
			List<String> list = oldExchange.getMessage().getBody(List.class);
			list.add(newVal);
			return oldExchange;
		}
	}
}
