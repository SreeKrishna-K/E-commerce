package com.tcs.bookstore.service;

import com.tcs.bookstore.model.CartDTO;
import com.tcs.bookstore.model.CartItem;

public interface CartItemService {
	public CartItem createItemforCart(CartDTO cartdto);
}
