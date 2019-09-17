package com.bridgelabz.controller;

 import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.dto.ForgotPass;
import com.bridgelabz.dto.Login;
import com.bridgelabz.dto.UserDto;
import com.bridgelabz.service.IService;
import com.bridgelabz.util.Response;
import com.bridgelabz.util.TokenUtil;

@RequestMapping("/user")
@RestController
public class UserController 
{
	@Autowired
    TokenUtil tokenutil;
	@Autowired(required=true)
	private IService service;
	
	@PostMapping("/register")
	ResponseEntity<Response> register(@RequestBody UserDto userDto)
	{

		Response response = service.Register(userDto);
	     return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/login")
	ResponseEntity<Response> login(@RequestBody Login login)
	{
		Response response=service.login(login);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/forgot")
	ResponseEntity<Response> forgotpass(@RequestBody ForgotPass forgotpass)
	{
		Response response=service.forgot(forgotpass);
		return  new ResponseEntity<>(response, HttpStatus.OK);
		
	}

	@PostMapping("/reset{token}")
	ResponseEntity<Response> resetpass(@RequestParam String password,@PathVariable String token)
	{
		Response response=service.reset(password,token);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	

}
