package com.springcloud.product.exception;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler  {

	@ExceptionHandler(value = {ApiRequestException.class})
	public ResponseEntity<Object> handleApiRequestException(ApiRequestException e){
	ApiException apiException = new ApiException(e.getMessage(), HttpStatus.NOT_FOUND, ZonedDateTime.now());	
	return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(value = {ArithmeticException.class})
	public ResponseEntity<Object> handleArithmeticException(ApiRequestException e){
	ApiException apiException = new ApiException(e.getMessage(), HttpStatus.NOT_FOUND, ZonedDateTime.now());	
	return new ResponseEntity<>(apiException, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
