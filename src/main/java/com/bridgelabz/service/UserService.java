package com.bridgelabz.service;

 
import java.util.Optional;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.dto.ForgotPass;
import com.bridgelabz.dto.Login;
import com.bridgelabz.dto.ResetPass;
import com.bridgelabz.dto.UserDto;
import com.bridgelabz.model.User;
import com.bridgelabz.repository.UserRepository;
import com.bridgelabz.util.Email;
import com.bridgelabz.util.Response;
import com.bridgelabz.util.TokenUtil;
 @Service
public class UserService implements IService
{

	@Autowired
	UserRepository repository;
	@Autowired
	ModelMapper modelmapper;
	@Autowired
	TokenUtil tokenutil;
	@Autowired
	PasswordEncoder passwordencoder;
	@Autowired
	Email email;
	@Autowired
	MailService mailservice;
	
	

public Response Register(UserDto userdto) 
{
	Optional<User> isThere=repository.findByEmailid(userdto.getEmailid());

	if(isThere.isPresent())
	{
		 
		return new Response(400,"User Present",null);
	}
	else
	{
		userdto.setPassword(passwordencoder.encode(userdto.getPassword()));
		User user=modelmapper.map(userdto,User.class);
		User status=repository.save(user);
		System.out.println(user.getFname());
		System.out.println(status);
		
	}
	return  new Response(200,"Registration successfully done..",null); 
}

@Override
public Response login(Login login) {
	// TODO Auto-generated method stub

	
	String emailid=login.getEmailid();
	String password=login.getPassword();
	String token=null;
	Optional<User> isPresent=repository.findByEmailidAndPassword(emailid, password);
	if(isPresent.isPresent())
	{ 
		token=tokenutil.createToken(isPresent.get().getId());
		System.out.println("present");	
		System.out.println(token);
		return new Response(200,"login successfully done..",token);
	}
	 
	
		return new Response(400,"incoorect password or emailid.....",null);
	
	
}

@Override
public Response reset(String password, String token)
{
	// TODO Auto-generated method stub
	 
	Long id=tokenutil.decodeToken(token);
	Optional<User>  user=repository.findById(id);
	if(user.isPresent())
	{
	String emailid=user.get().getEmailid();
	user.get().setPassword(password);
	repository.save(user.get());
	return  new Response(400," password set succesfully.....",null);
	}
	return new Response(200,"user not found.....",null);
 
}

@Override
public Response forgot(ForgotPass forgotpass) {
	// TODO Auto-generated method stub
	String emailid=forgotpass.getEmailid();
	Optional<User> isPresent=repository.findByEmailid(emailid);
	if(isPresent.isPresent())
	{
	email.setTo(forgotpass.getEmailid()); 
	String toemailid=email.getTo();
	email.setFrom("ashutoshrit64@gmail.com");
	String fromemailid=email.getFrom();
	email.setSubject("forgot password link");
	String subject=email.getSubject();
	email.setBody(mailservice.getLink("http://localhost:8080/user/reset", isPresent.get().getId()));
	String body=email.getBody();
	MailService.send(toemailid, subject, body );
	 return new Response(400,"link is sent.....",null);
	}
	return null;
}

}
	
	
	
	

