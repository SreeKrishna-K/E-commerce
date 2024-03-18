package com.tcs.bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcs.bookstore.exception.NoProductFoundException;
import com.tcs.bookstore.model.CartDTO;
import com.tcs.bookstore.model.CartItem;
import com.tcs.bookstore.model.Product;
import com.tcs.bookstore.repository.CartItemRepo;
import com.tcs.bookstore.repository.ProductRepo;

@Service
public class CartItemServiceImpl implements CartItemService{
	@Autowired
	ProductRepo productDao;
	
	@Autowired
	CartItemRepo cartItemDao;

	@Override
	public CartItem createItemforCart(CartDTO cartdto) {
		
		Product existingProduct = productDao.findById(cartdto.getProductId()).orElseThrow( () -> new NoProductFoundException("Product Not found"));
		
		if(existingProduct.getQuantity() == 0) {
			throw new NoProductFoundException("Product OUT OF STOCK");
		}
		
		CartItem newItem = new CartItem();
		
		newItem.setCartProduct(existingProduct);
		newItem.setCartItemQuantity(cartdto.getQuantity());
		return cartItemDao.save(newItem);
	}
}
