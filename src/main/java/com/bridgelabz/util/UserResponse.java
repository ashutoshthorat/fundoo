package com.bridgelabz.util;

/**
 *  
 *  Purpose:RESPONSE AFTER LOGIN
 *
 * @author Ashutosh
 *  @version 1.0
 *  @since   05-11-2019
 */
public class UserResponse {

	private int statusCode;
	private String statusMessage;
	private String email;
	private String firstName;
	private String lName;
	private String token;
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	@Override
	public String toString() {
		return "UserResponse [statusCode=" + statusCode + ", statusMessage=" + statusMessage + ", email=" + email
				+ ", firstName=" + firstName + ", lName=" + lName + ", token=" + token + "]";
	}


	
}
