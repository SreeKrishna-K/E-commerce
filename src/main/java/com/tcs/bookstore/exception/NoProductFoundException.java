package com.tcs.bookstore.exception;

@SuppressWarnings("serial")
public class NoProductFoundException extends RuntimeException {
	public NoProductFoundException(String msg) {
		super(msg);
	}
}
