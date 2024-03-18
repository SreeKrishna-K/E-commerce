package com.tcs.bookstore.service;

import java.time.LocalDate;
import java.util.List;

import com.tcs.bookstore.model.Order;

public interface OrderService {
	public Order saveOrder(String address);
	public Order getOrderById(int orderId);
	public List<Order> getAllOrders();
	public List<Order> getAllOrdersByDate(LocalDate date);
	public List<Order> getAllOrdersOfUser();
	
}
