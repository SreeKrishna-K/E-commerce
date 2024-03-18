package com.tcs.bookstore.exception;

@SuppressWarnings("serial")
public class NoProductFound extends RuntimeException {
	public NoProductFound(String message){
		super(message);
		System.out.print("came here");
	}
}