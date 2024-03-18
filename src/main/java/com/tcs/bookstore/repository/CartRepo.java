package com.tcs.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.bookstore.model.Cart;

public interface CartRepo extends JpaRepository<Cart, Integer>{

}
