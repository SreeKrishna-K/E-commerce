package com.tcs.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.bookstore.config.UserInfoDetails;
import com.tcs.bookstore.exception.UserException;
import com.tcs.bookstore.model.Address;
import com.tcs.bookstore.model.User;
import com.tcs.bookstore.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody User ourUser) {
        User result = userService.saveUser(ourUser);
        return ResponseEntity.ok(result);
    }
	
	@GetMapping("/currentUser")
	public String home() {
		org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String res = "User Logged in is : ";
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserInfoDetails userDetail = (UserInfoDetails) auth.getPrincipal();
			res += userDetail.getUsername();
		}
		return res;
	}
	
	@GetMapping("/role")
	public String getMyRole() {
		org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String res = "Role : ";
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserInfoDetails userDetail = (UserInfoDetails) auth.getPrincipal();
			res += userDetail.getAuthorities().toString();
		}
		return res;
	}
	
	@GetMapping("/profile")
	public ResponseEntity<Object> getProfile(){
		org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserInfoDetails userDetail = (UserInfoDetails) auth.getPrincipal();
			return ResponseEntity.ok(userService.getUser(userDetail.getUsername()));
		}
        return new ResponseEntity<Object>("Unable to fetch the User Profile", HttpStatus.BAD_REQUEST);
	}
	
	
	@GetMapping("/all")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Object> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUser());
	}
	
	@PutMapping("/updateAddress")
	public String updateAddress(@RequestBody Address address, @RequestParam("type") String type) {
		String res = "";
		try {
			userService.updateAddress(address, type);
			res += "Address Updated Successfully";
		}catch(Exception e) {
			throw new UserException("Something went wrong while adding address");
		}
		return res;
	}
	
	@PutMapping("/updateName")
	public ResponseEntity<User> updateName(@RequestParam("newName") String newName){
		return new ResponseEntity<User>(userService.updateName(newName), HttpStatus.OK);
	}
	
	@PutMapping("/updateEmail")
	public ResponseEntity<User> updateEmail(@RequestParam("newEmail") String newEmail){
		return new ResponseEntity<User>(userService.updateEmail(newEmail), HttpStatus.OK);
	}
	
	@PutMapping("/updatePassword")
	public ResponseEntity<User> updatePassword(@RequestParam("password") String password){
		return new ResponseEntity<User>(userService.updatePassword(password), HttpStatus.OK);
	}
	
	@PutMapping("/forgetPassword")
	public ResponseEntity<User> forgotPassword(@RequestParam("email") String email,@RequestParam("password") String password){
		return new ResponseEntity<User>(userService.forgotPassword(email, password)  , HttpStatus.OK);
	}
}
