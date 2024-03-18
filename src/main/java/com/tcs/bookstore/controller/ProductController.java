package com.tcs.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.bookstore.service.ProductService;

import com.tcs.bookstore.model.Product;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@PostMapping("/addproduct")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Product> addProduct(@RequestBody Product product) {
		Product savedProd = productService.addProduct(product);
		return new ResponseEntity<Product>(savedProd,HttpStatus.CREATED);
	}
	
	@PostMapping("/updateProduct")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String updateProductQuantity(@RequestParam int id,
            @RequestParam int quantity) {
		String updatedProd = productService.updateProductQuantity(id, quantity);
		return updatedProd;
	}
	
	@GetMapping("/all")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public ResponseEntity<List<Product>> getAllProducts(){
		List<Product> list = productService.getAllProducts();
		return new ResponseEntity<List<Product>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/category/{cate}")
	public ResponseEntity<List<Product>> getAllProductsInCategory(@PathVariable("cate") String cate) {
		List<Product> list = productService.getProductsByCategory(cate);
		return new ResponseEntity<List<Product>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/search/{name}")
	public ResponseEntity<List<Product>> getAllProductsByName(@PathVariable("name") String name) {
		List<Product> list = productService.getAllProductsByName(name);
		return new ResponseEntity<List<Product>>(list, HttpStatus.OK);
	}
	
	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<String> deleteProductFromCatalogHandler(@PathVariable("id") Integer id) {
		String res = productService.deleteProduct(id);
		return new ResponseEntity<String>(res, HttpStatus.OK);
	}
	
}
