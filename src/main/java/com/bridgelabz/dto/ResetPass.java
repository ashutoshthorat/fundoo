package com.bridgelabz.dto;

public class ResetPass
{
	String emailid;
	String newpassword;
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	public String getNewpassword() {
		return newpassword;
	}
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	@Override
	public String toString() {
		return "ForgotPass [emailid=" + emailid + ", newpassword=" + newpassword + "]";
	}
	
	
}
