package com.tcs.bookstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tcs.bookstore.model.User;

import jakarta.transaction.Transactional;

public interface UserRepo extends JpaRepository<User, Integer> {
	@Query(value = "select * from users where email = :email", nativeQuery = true)
    Optional<User> findByEmail(String email);
	@Modifying
	@Transactional
	@Query(value= "update users set name = :name where id = :id",nativeQuery = true)
	void updateUserName(@Param("name") String name,@Param("id") int id);
	@Modifying
	@Transactional
	@Query(value= "update users set email = :email where id = :id",nativeQuery = true)
	void updateUserEmail(@Param("email") String email,@Param("id") int id);
	@Modifying
	@Transactional
	@Query(value= "update users set password = :password where id = :id",nativeQuery = true)
	void updateUserPassword(@Param("password") String password,@Param("id") int id);
	@Modifying
	@Transactional
	@Query(value= "update users set password = :password where email = :email",nativeQuery = true)
	void forgotPassword(@Param("email") String email,@Param("password") String password);
}
