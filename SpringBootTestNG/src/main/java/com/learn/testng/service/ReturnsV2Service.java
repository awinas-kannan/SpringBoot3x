package com.learn.testng.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ReturnsV2Service {

    private static final Set<String> ELIGIBLE_ITEMS = Set.of("ITEM123", "ITEM456", "ITEM789");

    // Eligibility check
    public Map<String, Object> checkEligibility(String orderId, String itemId) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("orderId", orderId);
        response.put("itemId", itemId);

        if (ELIGIBLE_ITEMS.contains(itemId)) {
            response.put("eligible", true);
            response.put("message", "Item is eligible for return");
        } else {
            response.put("eligible", false);
            response.put("message", "Item not eligible for return");
        }
        response.put("checkedAt", LocalDateTime.now().toString());
        return response;
    }

    // Reasons
    public List<String> getReturnReasons(String itemId) {
        if ("ITEM123".equals(itemId)) {
            return Arrays.asList("Damaged", "Wrong Item Sent", "Quality Issue");
        } else if ("ITEM456".equals(itemId)) {
            return Arrays.asList("Not Needed", "Changed Mind", "Better Price Found");
        }
        return Arrays.asList("Other");
    }

    // Disposition
    public Map<String, Object> getDisposition(String itemId) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("itemId", itemId);

        if ("ITEM123".equals(itemId)) {
            response.put("disposition", "Return to Warehouse");
        } else if ("ITEM456".equals(itemId)) {
            response.put("disposition", "Return to Seller Store");
        } else {
            response.put("disposition", "Keep at Customer (No Return Needed)");
        }
        response.put("timestamp", LocalDateTime.now().toString());
        return response;
    }

    // Create Return
    public Map<String, Object> createReturn(String orderId, String itemId, String reason) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("returnId", UUID.randomUUID().toString());
        response.put("orderId", orderId);
        response.put("itemId", itemId);
        response.put("reason", reason);
        response.put("status", "Return Created");
        response.put("createdAt", LocalDateTime.now().toString());
        return response;
    }

    // Refund
    public Map<String, Object> computeRefund(String orderId, String itemId) {
        Map<String, Object> response = new LinkedHashMap<>();
        BigDecimal refundAmount = BigDecimal.valueOf(new Random().nextInt(500) + 100);

        response.put("orderId", orderId);
        response.put("itemId", itemId);
        response.put("refundAmount", refundAmount);
        response.put("currency", "USD");
        response.put("status", "Refund Approved");
        response.put("processedAt", LocalDateTime.now().toString());

        return response;
    }

    // Receipt
    public Map<String, Object> generateReceipt(String returnId) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("returnId", returnId);
        response.put("receiptNumber", "RCPT-" + new Random().nextInt(99999));
        response.put("issuedAt", LocalDateTime.now().toString());
        response.put("message", "Receipt generated for return");
        return response;
    }

    // Cancel Return
    public Map<String, Object> cancelReturn(String returnId) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("returnId", returnId);
        response.put("status", "Cancelled");
        response.put("cancelledAt", LocalDateTime.now().toString());
        response.put("message", "Return has been successfully cancelled");
        return response;
    }
}
