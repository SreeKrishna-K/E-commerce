package com.tcs.bookstore.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tcs.bookstore.model.Order;

public interface OrderRepo extends JpaRepository<Order, Integer>{
	public List<Order> findByDate(LocalDate date);
	@Query(value = "select * from orders where customer_id = :id", nativeQuery = true)
	public List<Order> getAllOrdersOfUser(@Param("id") int id);
}
