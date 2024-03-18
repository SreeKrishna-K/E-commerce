package com.tcs.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.bookstore.model.CartItem;

public interface CartItemRepo extends JpaRepository<CartItem, Integer>{

}