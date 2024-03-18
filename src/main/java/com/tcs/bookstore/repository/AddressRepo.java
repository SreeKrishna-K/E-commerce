package com.tcs.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.bookstore.model.Address;

public interface AddressRepo extends JpaRepository<Address, Integer>{

}
