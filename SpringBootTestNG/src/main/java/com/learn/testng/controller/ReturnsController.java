package com.learn.testng.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/returns")
public class ReturnsController {

    @GetMapping("/eligibility/{orderId}")
    public ResponseEntity<String> checkEligibility(@PathVariable String orderId) {
        if (orderId.startsWith("ELIGIBLE")) {
            return ResponseEntity.ok("Return Eligible");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not Eligible");
    }

    @GetMapping("/reasons")
    public ResponseEntity<List<String>> getReturnReasons() {
        return ResponseEntity.ok(List.of("Damaged", "Wrong Item", "Not Needed"));
    }

    @PostMapping("/create")
    public ResponseEntity<String> createReturn(@RequestBody Map<String, Object> request) {
        return ResponseEntity.ok("Return Created for Order: " + request.get("orderId"));
    }

    @GetMapping("/refund/{orderId}")
    public ResponseEntity<Double> computeRefund(@PathVariable String orderId) {
        return ResponseEntity.ok(250.75); // mock refund
    }

    @GetMapping("/disposition/{itemId}")
    public ResponseEntity<String> getDisposition(@PathVariable String itemId) {
        return ResponseEntity.ok("Send to Warehouse");
    }
}
