package com.bridgelabz.model;

import java.io.Serializable;
import java.time.LocalTime;

import javax.validation.constraints.NotNull;

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
	public LocalTime getTime() {
		return time;
	}
	public void setTime(LocalTime time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "LoginModel [emailid=" + emailid + ", password=" + password + ", time=" + time + "]";
	}
	
	
	
	
}
