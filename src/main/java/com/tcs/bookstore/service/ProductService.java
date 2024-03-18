package com.tcs.bookstore.service;

import java.util.List;

import com.tcs.bookstore.model.Product;

public interface ProductService {
	public Product addProduct(Product product);
	public List<Product> getAllProducts();
	public List<Product> getProductsByCategory(String cate);
	public List<Product> getAllProductsByName(String name);
	public String updateProductQuantity(int id,int quantity);
	public String deleteProduct(int id);
}
