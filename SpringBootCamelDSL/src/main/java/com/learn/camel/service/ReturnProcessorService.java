package com.learn.camel.service;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ReturnProcessorService {

	public String process(String task) {
		log.info("ReturnProcessorService : {}", task);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException ignored) {
		}
		return "✅ " + task;
	}
}
