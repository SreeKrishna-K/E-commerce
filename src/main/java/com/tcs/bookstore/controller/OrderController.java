package com.tcs.bookstore.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.bookstore.model.Order;
import com.tcs.bookstore.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/placeOrder")
	public ResponseEntity<Order> makeOrder(@RequestParam("address") String address) {
		return new ResponseEntity<Order>(orderService.saveOrder(address), HttpStatus.CREATED);
	}
	
	@GetMapping("/orderId/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Order> getOrderById(@PathVariable("id") Integer id){
		return new ResponseEntity<Order>(orderService.getOrderById(id), HttpStatus.OK);
	}
	
	@GetMapping("/all")
	@PreAuthorize("hasAuthority('ADMIN')")
	public  ResponseEntity<List<Order>> getAllOrder(){
		return new ResponseEntity<List<Order>>(orderService.getAllOrders() ,HttpStatus.OK);
	}
	
	
	@GetMapping("/getByDate")
	public List<Order> getOrdersByDate(@RequestParam("date") String date){
		DateTimeFormatter dtf=DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate ld=LocalDate.parse(date,dtf);
		return orderService.getAllOrdersByDate(ld);
	}
	
	@GetMapping("/user/all")
	public ResponseEntity<List<Order>> getAllOrdersOfUser(){
		return new ResponseEntity<List<Order>>(orderService.getAllOrders() ,HttpStatus.OK);
	}

}
