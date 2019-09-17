package com.bridgelabz.service;


import org.springframework.stereotype.Service;


import com.bridgelabz.dto.ForgotPass;
import com.bridgelabz.dto.Login;
import com.bridgelabz.dto.UserDto;
import com.bridgelabz.util.Response;



@Service
public interface IService
{

  public Response Register(UserDto userdto);

public Response login(Login login);

public Response forgot(ForgotPass forgotpass);

public Response reset(String password, String token);
	
	
	
}
