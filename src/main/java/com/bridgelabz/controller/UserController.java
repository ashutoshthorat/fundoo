package com.bridgelabz.controller;





import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;


import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.dto.ForgotPass;
import com.bridgelabz.dto.Login;
import com.bridgelabz.dto.UserDto;
import com.bridgelabz.model.User;
import com.bridgelabz.service.IService;
import com.bridgelabz.util.Response;
import com.bridgelabz.util.UserResponse;

/**
 *  
 *   Purpose: controller for user service
 *
 * @author Ashutosh
 *  @version 1.0
 *  @since   05-11-2019
 */
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping("/user")
@RestController
public class UserController {
	@Autowired(required = true)
	private IService service;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@PostMapping("/register")
	
	
	//controller api for register
	ResponseEntity<Response> register(@Valid @RequestBody UserDto userDto) {

		Response response = service.register(userDto);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	//controller api for login
	@PostMapping("/login")
	ResponseEntity<UserResponse> login(@Valid @RequestBody Login login) {
		System.out.println(login.getPassword());
		 
		
		UserResponse response = service.login(login);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	//controller api for forgot password
	@PostMapping("/forgot")
	ResponseEntity<Response> forgotpass(@Valid @RequestBody ForgotPass forgotpass) {
		Response response = service.forgot(forgotpass);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	//controller for api to verify user
	@PostMapping("/verify/{token}")
	ResponseEntity<Response> verify(@Valid @PathVariable String token) {
		Response response = service.verify(token);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	//controller for api to reset password
	@PostMapping("/reset/{token}")
	ResponseEntity<Response> resetpass(@Valid @RequestParam String password, @PathVariable String token) {
		Response response = service.reset(password, token);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	//controller for the api to send link of verify user
	@GetMapping("/verifyuser/{token}")
	boolean verifyuser(@Valid @RequestParam String emailid) {
		boolean response = service.verifyuser(emailid);
		return response;

	}
	// api for adding profile photo
	@PostMapping(value = "/uploadImage/{token}")
	ResponseEntity<Response> setprofile(@RequestParam(value="File") MultipartFile path,@PathVariable String token)
	{
		System.out.println("------------------>>>>>>>>"+path);
		Response response = service.setprofile(path, token);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	//api for the get the profile pic
	@GetMapping("/getphoto")
   Resource getprofile(@RequestParam String token){
		Resource response = service.getprofile(token);
		System.out.println(response);
		return response;

	}
	
	//api for the checking user present or not
	@GetMapping("/checkuser")
	ResponseEntity<Boolean> checkuser(@RequestParam String emailid)
	{
		boolean user =service.checkuser(emailid);
		return new ResponseEntity<Boolean>(user,HttpStatus.OK);
	}
	
	//api for getting userid int the database
	@GetMapping("/userdata")
	ResponseEntity<Long> getuser(@RequestParam String emailid)
	{
		long user =service.getuser(emailid);
		return new ResponseEntity<Long>(user,HttpStatus.OK);
	}

}
