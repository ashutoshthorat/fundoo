package com.bridgelabz.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class RegistrationException extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int statusCode;
	private String statusMessage;
	public RegistrationException(int statusCode, String statusMessage) {
		super(statusMessage);
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
	}
 
	
	
	
	
	
	
}
