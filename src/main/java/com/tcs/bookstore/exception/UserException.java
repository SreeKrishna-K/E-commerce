package com.tcs.bookstore.exception;

@SuppressWarnings("serial")
public class UserException extends RuntimeException{
	public UserException() {
		super();
	}
	public UserException(String message) {
		super(message);
	}
}