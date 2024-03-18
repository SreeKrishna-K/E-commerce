package com.tcs.bookstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tcs.bookstore.config.UserInfoDetails;
import com.tcs.bookstore.exception.UserException;
import com.tcs.bookstore.model.Address;
import com.tcs.bookstore.model.Cart;
import com.tcs.bookstore.model.User;
import com.tcs.bookstore.repository.UserRepo;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepo userRepo;
	
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private EntityManager entityManager;
	
	public User updateAddress(Address address, String type) {	
		
		org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserInfoDetails userDetail = (UserInfoDetails) auth.getPrincipal();
			Optional<User> opt = userRepo.findByEmail(userDetail.getUsername());
			if(opt.isEmpty())
			{
				return null;
			}
			User existingCustomer = opt.get();
			address.setUser(existingCustomer);
			existingCustomer.getAddress().put(type, address);
			return userRepo.save(existingCustomer);
		}
		return null;
	}

	@Override
	public User saveUser(User user) throws UserException{
		// TODO Auto-generated method stub
		User res = null;
		try {
	    	user.setPassword(passwordEncoder.encode(user.getPassword()));
	    	Cart cart = new Cart();
	    	cart.setUser(user);
	    	user.setCustomerCart(cart);
	        res = userRepo.save(user);
		}catch(Exception e) {
			throw new UserException("User Registration Unsuccessfull!!!");
		}
		return res;
	}

	@Override
	public User getUser(String email) throws UserException {
		// TODO Auto-generated method stub
		Optional<User> u = userRepo.findByEmail(email);
		if(u.isPresent()) {
			return u.get();
		}
		throw new UserException("No User Found with email " + email);
	}

	@Override
	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		return userRepo.findAll();
	}

	@Override
	@Transactional
	public User updateName(String newName) {
		// TODO Auto-generated method stub
		org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserInfoDetails userDetail = (UserInfoDetails) auth.getPrincipal();
			Optional<User> opt = userRepo.findByEmail(userDetail.getUsername());
			if(opt.isEmpty())
			{
				return null;
			}
			User existingCustomer = opt.get();
			userRepo.updateUserName(newName, existingCustomer.getId());
            entityManager.flush();
            entityManager.refresh(existingCustomer);
			return existingCustomer;
		}
		return null;
	}
	
	@Override
	@Transactional
	public User updateEmail(String newEmail) {
		org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserInfoDetails userDetail = (UserInfoDetails) auth.getPrincipal();
			Optional<User> opt = userRepo.findByEmail(userDetail.getUsername());
			if(opt.isEmpty())
			{
				return null;
			}
			User existingCustomer = opt.get();
			userRepo.updateUserEmail(newEmail, existingCustomer.getId());
            entityManager.flush();
            entityManager.refresh(existingCustomer);
			return existingCustomer;
		}
		return null;
	}

	@Override
	@Transactional
	public User updatePassword(String password) {
		// TODO Auto-generated method stub
		org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserInfoDetails userDetail = (UserInfoDetails) auth.getPrincipal();
			Optional<User> opt = userRepo.findByEmail(userDetail.getUsername());
			if(opt.isEmpty())
			{
				return null;
			}
			User existingCustomer = opt.get();
			userRepo.updateUserPassword(passwordEncoder.encode(password), existingCustomer.getId());
            entityManager.flush();
            entityManager.refresh(existingCustomer);
			return existingCustomer;
		}
		return null;
	}

	@Override
	@Transactional
	public User forgotPassword(String email, String password) throws UserException{
		// TODO Auto-generated method stub
		
		Optional<User> opt= userRepo.findByEmail(email);
		User existingCustomer = opt.get();
		if(opt.isEmpty())
		{
			throw new UserException("User with email: "+email+" does not exist");
		}
		userRepo.forgotPassword(email,passwordEncoder.encode(password));
        entityManager.flush();
        entityManager.refresh(existingCustomer);
		return existingCustomer;
	}
	
}
