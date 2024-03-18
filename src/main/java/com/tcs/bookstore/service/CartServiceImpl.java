package com.tcs.bookstore.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tcs.bookstore.config.UserInfoDetails;
import com.tcs.bookstore.model.Cart;
import com.tcs.bookstore.model.CartDTO;
import com.tcs.bookstore.model.CartItem;
import com.tcs.bookstore.model.User;
import com.tcs.bookstore.repository.CartItemRepo;
import com.tcs.bookstore.repository.CartRepo;
import com.tcs.bookstore.repository.UserRepo;

@Service
public class CartServiceImpl implements CartService{
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	CartItemService cartItemService;
	
	@Autowired
	CartRepo cartRepo;
	
	@Autowired
	CartItemRepo cartItemRepo;

	@Override
	public Cart addProductToCart(CartDTO cartDto) {
		// TODO Auto-generated method stub
		org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserInfoDetails userDetail = (UserInfoDetails) auth.getPrincipal();
			Optional<User> opt = userRepo.findByEmail(userDetail.getUsername());
			if(opt.isEmpty())
			{
				return null;
			}
			
			User existingCustomer = opt.get();
			Cart customerCart = existingCustomer.getCustomerCart();
			List<CartItem> cartItems = customerCart.getCartItems();
			
			CartItem item = cartItemService.createItemforCart(cartDto);
			
			if(cartItems.size() == 0) {
				cartItems.add(item);
				customerCart.setCartTotal(item.getCartProduct().getPrice() * item.getCartItemQuantity());
			}			
			else {
				boolean flag = false;
				for(CartItem c: cartItems) {
					if(c.getCartProduct().getId() == cartDto.getProductId()) {
						c.setCartItemQuantity(c.getCartItemQuantity() + item.getCartItemQuantity());
						customerCart.setCartTotal(customerCart.getCartTotal() + c.getCartProduct().getPrice() * item.getCartItemQuantity());
						flag = true;
					}
				}
				if(!flag) {
					cartItems.add(item);
					customerCart.setCartTotal(customerCart.getCartTotal() + item.getCartProduct().getPrice() * item.getCartItemQuantity());
				}
			}
			
			return cartRepo.save(existingCustomer.getCustomerCart());
			
		}
		return null;

	}
	
	@Override
	public Cart clearCart() {
		
		org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			
			UserInfoDetails userDetail = (UserInfoDetails) auth.getPrincipal();
			Optional<User> opt = userRepo.findByEmail(userDetail.getUsername());
			if(opt.isEmpty())
			{
				return null;
			}
			
			User existingCustomer = opt.get();
			Cart customerCart = existingCustomer.getCustomerCart();
			List<CartItem> emptyCart = new ArrayList<>();
			customerCart.setCartItems(emptyCart);
			customerCart.setCartTotal(0.0);
			return cartRepo.save(existingCustomer.getCustomerCart());
		}
		return null;
	}
	

}
