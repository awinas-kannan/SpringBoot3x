package com.learn.testng.controller;


import com.learn.testng.service.ReturnsV2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v2/returns")
public class ReturnsV2Controller {

    @Autowired
    private ReturnsV2Service returnService;

    @GetMapping("/eligibility")
    public Map<String, Object> checkEligibility(@RequestParam String orderId,
                                                @RequestParam String itemId) {
        return returnService.checkEligibility(orderId, itemId);
    }

    @GetMapping("/reasons")
    public List<String> getReasons(@RequestParam String itemId) {
        return returnService.getReturnReasons(itemId);
    }

    @GetMapping("/disposition")
    public Map<String, Object> getDisposition(@RequestParam String itemId) {
        return returnService.getDisposition(itemId);
    }

    @PostMapping("/create")
    public Map<String, Object> createReturn(@RequestParam String orderId,
                                            @RequestParam String itemId,
                                            @RequestParam String reason) {
        return returnService.createReturn(orderId, itemId, reason);
    }

    @PostMapping("/refund")
    public Map<String, Object> refund(@RequestParam String orderId,
                                      @RequestParam String itemId) {
        return returnService.computeRefund(orderId, itemId);
    }

    @GetMapping("/receipt")
    public Map<String, Object> receipt(@RequestParam String returnId) {
        return returnService.generateReceipt(returnId);
    }

    @PostMapping("/cancel")
    public Map<String, Object> cancel(@RequestParam String returnId) {
        return returnService.cancelReturn(returnId);
    }
}




