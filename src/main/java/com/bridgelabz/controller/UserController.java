package com.bridgelabz.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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


@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping("/user")
@RestController
public class UserController {
	@Autowired(required = true)
	private IService service;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@PostMapping("/register")
	ResponseEntity<Response> register(@Valid @RequestBody UserDto userDto) {

		Response response = service.register(userDto);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/login")
	ResponseEntity<Response> login(@Valid @RequestBody Login login) {
		System.out.println(login.getPassword());
		 
		
		Response response = service.login(login);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/forgot")
	ResponseEntity<Response> forgotpass(@Valid @RequestBody ForgotPass forgotpass) {
		Response response = service.forgot(forgotpass);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PostMapping("/verify/{token}")
	ResponseEntity<Response> verify(@Valid @PathVariable String token) {
		Response response = service.verify(token);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/reset/{token}")
	ResponseEntity<Response> resetpass(@Valid @RequestParam String password, @PathVariable String token) {
		Response response = service.reset(password, token);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/verifyuser")
	boolean verifyuser(@Valid @RequestParam String emailid) {
		boolean response = service.verifyuser(emailid);
		return response;

	}

}
