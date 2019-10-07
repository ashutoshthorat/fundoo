package com.bridgelabz.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserDto 
{
	@NotNull(message = "first name is required")
	private String fname;
	@NotNull
	private String lname;
	@NotNull
	@Pattern(regexp="^[a-z]+[a-z0-9._]+@[a-z]+\\.[a-z.]{2,5}$")
	private String emailid;
	@NotNull
	private String password;
	@NotNull
	private String dob;
	@NotNull
	private String phoneno;
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
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
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getPhoneno() {
		return phoneno;
	}
	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}
	@Override
	public String toString() {
		return "UserDto [fname=" + fname + ", lname=" + lname + ", emailid=" + emailid + ", password=" + password
				+ ", dob=" + dob + ", phoneno=" + phoneno + "]";
	}

	
	 
	
	
}
