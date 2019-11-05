package com.bridgelabz.service;



import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.dto.ForgotPass;
import com.bridgelabz.dto.Login;
import com.bridgelabz.dto.UserDto;
 
import com.bridgelabz.util.Response;
import com.bridgelabz.util.UserResponse;

/**
 *  
 *  Purpose:service interface
 *
 * @author Ashutosh
 *  @version 1.0
 *  @since   05-11-2019
 */

// all the method for the service are registered are here
@Service
public interface IService
{

  public Response register(UserDto userdto);

public UserResponse login(Login login);

public Response forgot(ForgotPass forgotpass);

public Response reset(String password, String token);

public Response verify(String token);
	
public boolean verifyuser(String emailid);

public Response setprofile(MultipartFile path, String token);

public Resource getprofile(String token);

public long getuser(String emailid);

public boolean checkuser(String emailid);


 
	
	
}
