package com.learn.testng.controller;

import com.learn.testng.service.ReturnService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/returns")
public class ReturnsController {

    private final ReturnService returnService;


    public ReturnsController(ReturnService returnService) {
        this.returnService = returnService;
    }
    @GetMapping("/eligibility/{orderId}")
    public ResponseEntity<String> checkEligibility(@PathVariable String orderId) {
        return ResponseEntity.ok(returnService.checkEligibility(orderId));
    }

    @GetMapping("/reasons")
    public ResponseEntity<List<String>> getReturnReasons() {
        return ResponseEntity.ok(returnService.getReturnReasons());
    }

    @PostMapping("/create")
    public ResponseEntity<String> createReturn(@RequestBody Map<String, Object> request) {
        return ResponseEntity.ok(returnService.createReturn(request));
    }

    @GetMapping("/refund/{orderId}")
    public ResponseEntity<Double> computeRefund(@PathVariable String orderId) {
        return ResponseEntity.ok(returnService.computeRefund(orderId));
    }

    @GetMapping("/disposition/{itemId}")
    public ResponseEntity<String> getDisposition(@PathVariable String itemId) {
        return ResponseEntity.ok(returnService.getDisposition(itemId));
    }
}
