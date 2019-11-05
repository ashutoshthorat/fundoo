package com.bridgelabz.exception;

import org.springframework.web.bind.annotation.ResponseStatus;


/**
 *  
 *  Purpose:Throwing exceptions at the time of the registration
 *
 * @author Ashutosh
 *  @version 1.0
 *  @since   05-11-2019
 */
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
