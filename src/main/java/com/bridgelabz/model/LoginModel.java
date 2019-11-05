package com.bridgelabz.model;

import java.io.Serializable;
import java.time.LocalTime;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 *  
 *  Purpose:Model for the login
 *
 * @author Ashutosh
 *  @version 1.0
 *  @since   05-11-2019
 */
@Data
public class LoginModel implements Serializable
{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotNull
	private String emailid;
	@NotNull
	private String password;
	
	private LocalTime time;


	
	
	
}
