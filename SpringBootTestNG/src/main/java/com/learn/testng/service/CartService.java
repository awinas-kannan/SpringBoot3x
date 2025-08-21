package com.learn.testng.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final Map<String, Integer> cart = new HashMap<>();

    public void addItem(String productId, int qty) {
        cart.put(productId, cart.getOrDefault(productId, 0) + qty);
    }

    public void removeItem(String productId) {
        cart.remove(productId);
    }

    public int getItemCount(String productId) {
        return cart.getOrDefault(productId, 0);
    }

    public int getTotalItems() {
        return cart.values().stream().mapToInt(Integer::intValue).sum();
    }
}
