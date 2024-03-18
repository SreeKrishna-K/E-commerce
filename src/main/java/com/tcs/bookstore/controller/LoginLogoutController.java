package com.tcs.bookstore.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.bookstore.config.UserInfoDetails;
import com.tcs.bookstore.model.Cart;
import com.tcs.bookstore.model.User;
import com.tcs.bookstore.repository.UserRepo;

@RestController
@RequestMapping
public class LoginLogoutController {


    @Autowired
    private PasswordEncoder passwordEncoder;
    
    
	@GetMapping("/")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public String home() {
		org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String res = "User Logged in is : ";
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserInfoDetails userDetail = (UserInfoDetails) auth.getPrincipal();
			res += userDetail.getUsername() + " with authority " +userDetail.getAuthorities().toString();
		}
		return res;
	}
	
	@GetMapping("/getrole")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public String getMyRole() {
		org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String res = "Role : ";
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserInfoDetails userDetail = (UserInfoDetails) auth.getPrincipal();
			res += userDetail.getAuthorities().toString();
		}
		return res;
	}
	
    @GetMapping("/data")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> getData() {
    	System.out.print("/data working");
        Map<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        data.put("key2", "value2");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

}
