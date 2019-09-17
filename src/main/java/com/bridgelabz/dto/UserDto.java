package com.bridgelabz.dto;



public class UserDto 
{
	private String fname;
	private String lname;
	private String emailid;
	private String password;
	private String Dob;
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
		return Dob;
	}
	public void setDob(String dob) {
		Dob = dob;
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
				+ ", Dob=" + Dob + ", phoneno=" + phoneno + "]";
	}
 
	 
	
	
}
