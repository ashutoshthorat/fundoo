package com.bridgelabz.dto;

import java.io.Serializable;
 

import javax.validation.constraints.NotNull;

import lombok.Data;


/**
 *  
 *  Purpose:dto for the Login
 *
 * @author Ashutosh
 *  @version 1.0
 *  @since   05-11-2019
 */
@Data
public class Login implements Serializable
{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotNull
	private String emailid;
	@NotNull
	private String password;
 
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
	this.password = password;
	}
	
	 
	@Override
	public String toString() {
		return "Login [emailid=" + emailid + ", password=" + password + "]";
	}
	

	

}
