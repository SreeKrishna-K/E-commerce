package com.tcs.bookstore.service;

import java.util.List;

import com.tcs.bookstore.model.Address;
import com.tcs.bookstore.model.User;

public interface UserService {
	public User updateAddress(Address address, String type);	
	public User saveUser(User user);
	public User getUser(String email);
	public List<User> getAllUser();
	public User updateName(String newName);
	public User updateEmail(String newEmail);
	public User updatePassword(String password);
	public User forgotPassword(String email,String password);
}
