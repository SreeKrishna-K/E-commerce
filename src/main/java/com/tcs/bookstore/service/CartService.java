package com.tcs.bookstore.service;

import com.tcs.bookstore.model.Cart;
import com.tcs.bookstore.model.CartDTO;

public interface CartService {
	public Cart addProductToCart(CartDTO cart);
	public Cart clearCart();
	
}
