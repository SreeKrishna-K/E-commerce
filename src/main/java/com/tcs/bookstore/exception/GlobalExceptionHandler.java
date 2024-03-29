package com.tcs.bookstore.exception;


import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(NoProductFound.class)
	public ResponseEntity<ErrorDetails> productNotFound(NoProductFound npf,WebRequest wr){
		ErrorDetails err = new ErrorDetails(LocalDateTime.now(),npf.getMessage(),wr.getDescription(false));
		return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(UserException.class)
	public ResponseEntity<ErrorDetails> userException(UserException ue,WebRequest wr){
		ErrorDetails err = new ErrorDetails(LocalDateTime.now(),ue.getMessage(),wr.getDescription(false));
		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
	}
	
	// Custom Exception Handler Area Ends
	
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ErrorDetails> noHandlerFoundExceptionHandler(NoHandlerFoundException nhfe, WebRequest wr){
		ErrorDetails err = new ErrorDetails(LocalDateTime.now(), nhfe.getMessage(), wr.getDescription(false));
		return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}
	
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDetails> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException manv, WebRequest wr){
		String message = manv.getBindingResult().getFieldError().getDefaultMessage();
		ErrorDetails err = new ErrorDetails(LocalDateTime.now(), message, wr.getDescription(false));
		return new ResponseEntity<ErrorDetails>(err, HttpStatus.FORBIDDEN);
	}
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> exceptionHandler(Exception e, WebRequest wr){
		ErrorDetails err = new ErrorDetails(LocalDateTime.now(), e.getMessage(), wr.getDescription(false));
		return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}

}
