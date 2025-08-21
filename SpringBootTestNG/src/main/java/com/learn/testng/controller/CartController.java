package com.learn.testng.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learn.testng.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

	private final CartService cartService;

	public CartController(CartService cartService) {
		this.cartService = cartService;
	}

	@PostMapping("/add")
	public String addItem(@RequestParam String productId, @RequestParam int qty) {
		cartService.addItem(productId, qty);
		return "Item added successfully";
	}

	@DeleteMapping("/remove/{productId}")
	public String removeItem(@PathVariable String productId) {
		cartService.removeItem(productId);
		return "Item removed successfully";
	}

	@GetMapping("/count/{productId}")
	public int getItemCount(@PathVariable String productId) {
		return cartService.getItemCount(productId);
	}

	@GetMapping("/total")
	public int getTotalItems() {
		return cartService.getTotalItems();
	}
}
