package com.learn.camel.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ScatterService {

	public List<String> scatter(String requestId) {
		List<String> tasks = new ArrayList<>();
		tasks.add("UpdateInventory for " + requestId);
		tasks.add("ProcessRefund for " + requestId);
		tasks.add("SendNotification for " + requestId);

		// Sample enhancement: based on requestId or logic, add extra task
		if (requestId.contains("VIP")) {
			tasks.add("OfferLoyaltyPoints for " + requestId);
		}

		log.info("Scatter generated tasks: {} ", tasks);
		return tasks;
	}
}
