package com.tcs.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.bookstore.model.Product;
import com.tcs.bookstore.service.ProductService;

@RestController
@RequestMapping
public class AdminController {
	@Autowired
	private ProductService productService;
	
	@PostMapping("/prod/addproduct")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String addProduct(@RequestBody Product product) {
		productService.addProduct(product);
		return "saved Successfully";
	}

}
