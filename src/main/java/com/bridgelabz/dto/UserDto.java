package com.bridgelabz.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;
/**
 *  
 *  Purpose:dto for the User data
 *
 * @author Ashutosh
 *  @version 1.0
 *  @since   05-11-2019
 */
@Data
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
	 
	  
	
	
}
