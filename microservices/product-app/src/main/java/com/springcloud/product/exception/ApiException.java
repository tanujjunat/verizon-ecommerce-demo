package com.springcloud.product.exception;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;

public class ApiException {
	private final String message;
	private final HttpStatus httpstatus;
	private final ZonedDateTime timestamp;
	
	public ApiException(String message, HttpStatus httpstatus, ZonedDateTime timestamp) {
		super();
		this.message = message;
		this.httpstatus = httpstatus;
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public HttpStatus getHttpstatus() {
		return httpstatus;
	}

	public ZonedDateTime getTimestamp() {
		return timestamp;
	}
	
	

}
