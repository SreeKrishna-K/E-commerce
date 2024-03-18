package com.tcs.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.bookstore.model.Cart;
import com.tcs.bookstore.model.CartDTO;
import com.tcs.bookstore.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@PostMapping(value = "/add")
	public ResponseEntity<Cart> addToCart(@RequestBody CartDTO cartdto) {
		Cart cart = cartService.addProductToCart(cartdto);
		return new ResponseEntity<Cart>(cart,HttpStatus.CREATED);
	}
	
	@PostMapping(value = "/clear")
	public ResponseEntity<Cart> clearCart() {
		Cart cart = cartService.clearCart();
		return new ResponseEntity<Cart>(cart,HttpStatus.OK);
	}

}
