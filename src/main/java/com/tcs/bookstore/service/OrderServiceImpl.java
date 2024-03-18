package com.tcs.bookstore.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tcs.bookstore.config.UserInfoDetails;
import com.tcs.bookstore.model.Cart;
import com.tcs.bookstore.model.CartItem;
import com.tcs.bookstore.model.Order;
import com.tcs.bookstore.model.User;
import com.tcs.bookstore.repository.OrderRepo;
import com.tcs.bookstore.repository.UserRepo;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	OrderRepo orderRepo;
	
	@Autowired
	CartService cartService;

	@Override
	public Order saveOrder(String address) {
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
			List<CartItem> cartItems = new ArrayList<>();
	        for (CartItem item : customerCart.getCartItems()) {
	            	cartItems.add(item);
	        }
			Order newOrder = new Order();
			newOrder.setUser(existingCustomer);
			newOrder.setOrdercartItems(cartItems);
			newOrder.setAddress(existingCustomer.getAddress().get(address));
			newOrder.setTotal(customerCart.getCartTotal());
			newOrder.setDate(LocalDate.now());
			cartService.clearCart();
			return orderRepo.save(newOrder);
			
		}
		return null;
	}

	@Override
	public Order getOrderById(int orderId) {
		// TODO Auto-generated method stub
		return orderRepo.findById(orderId).get();
	}

	@Override
	public List<Order> getAllOrders() {
		// TODO Auto-generated method stub
		return orderRepo.findAll();
	}

	@Override
	public List<Order> getAllOrdersByDate(LocalDate date) {
		// TODO Auto-generated method stub
		List<Order> list= orderRepo.findByDate(date);
		return list;
	}

	@Override
	public List<Order> getAllOrdersOfUser() {
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
			return orderRepo.getAllOrdersOfUser(existingCustomer.getId());
		}
		return null;
	}
	

}
