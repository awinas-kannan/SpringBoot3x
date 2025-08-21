package com.learn.testng.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReturnService {

    private static final Map<String, Double> ORDER_PRICE = Map.of(
            "ORD123", 100.0,
            "ORD456", 250.0
    );

    public String checkEligibility(String orderId) {
        if (ORDER_PRICE.containsKey(orderId)) {
            return "Eligible for return";
        }
        return "Not eligible – order not found";
    }

    public List<String> getReturnReasons() {
        return List.of("Damaged Item", "Wrong Item Delivered", "Changed Mind", "Better Price Found");
    }

    public String createReturn(Map<String, Object> request) {
        return "Return Created for Order: " + request.get("orderId") +
                " with Reason: " + request.get("reason");
    }

    public Double computeRefund(String orderId) {
        return ORDER_PRICE.getOrDefault(orderId, 0.0) * 0.9; // 90% refund after restocking fee
    }

    public String getDisposition(String itemId) {
        return switch (itemId) {
            case "ITEM100" -> "Return to Vendor";
            case "ITEM200" -> "Resell in Store";
            default -> "Recycle";
        };
    }
}
