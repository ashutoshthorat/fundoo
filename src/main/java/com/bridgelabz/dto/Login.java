package com.bridgelabz.dto;

public class Login 
{

	
	String emailid;
	String Password;
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	@Override
	public String toString() {
		return "Login [emailid=" + emailid + ", Password=" + Password + "]";
	}
	
}
