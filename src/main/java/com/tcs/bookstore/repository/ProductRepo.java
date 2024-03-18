package com.tcs.bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tcs.bookstore.model.Product;

import jakarta.transaction.Transactional;

public interface ProductRepo extends JpaRepository<Product, Integer> {
	@Query(value = "select * from products where category = :cate", nativeQuery = true)
	public List<Product> getAllProductsInACategory(@Param("cate") String cate);
	@Query(value = "select * from products where name = :name", nativeQuery = true)
	public List<Product> getAllProductsByName(@Param("name") String name);
	@Modifying
	@Transactional
	@Query(value = "update products set quantity = :quantity where id = :id",nativeQuery = true)
	public void updateProductQuantity(@Param("id") int id,@Param("quantity") int quantity);	
}
