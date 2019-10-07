package com.bridgelabz.service;

import java.time.LocalDateTime;


import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.dto.ForgotPass;
import com.bridgelabz.dto.Login;
import com.bridgelabz.dto.UserDto;
import com.bridgelabz.exception.RegistrationException;
import com.bridgelabz.model.User;
import com.bridgelabz.repository.UserRepository;
import com.bridgelabz.util.Email;
import com.bridgelabz.util.MessageProducer;
import com.bridgelabz.util.Response;
import com.bridgelabz.util.StatusHelper;
import com.bridgelabz.util.TokenUtil;

@Service
public class UserService implements IService {

	@Autowired
	private UserRepository repository;
	@Autowired
	private ModelMapper modelmapper;
	@Autowired
	private TokenUtil tokenutil;
	@Autowired
	private PasswordEncoder passwordencoder;
	@Autowired
	private Email email;
	@Autowired
	private MailService mailservice;
	@Autowired
	MessageProducer messageproducer;
	
	
	
	public Response register(UserDto userdto) {
		Optional<User> isThere = repository.findByEmailid(userdto.getEmailid());

		if (isThere.isPresent()) {
			throw new RegistrationException(400, "user already present");
		} else {
			userdto.setPassword(passwordencoder.encode(userdto.getPassword()));

			User user = modelmapper.map(userdto, User.class);

			repository.save(user);
			email.setTo(userdto.getEmailid());
			String toemailid = email.getTo();
			email.setFrom("ashutoshrit64@gmail.com");
			String fromemailid = email.getFrom();
			email.setSubject("Verification...");
			String subject = email.getSubject();
			email.setBody(mailservice.getLink("http://localhost:8080/user/verify", user.getId()));
			String body = email.getBody();
			messageproducer.sendMessage(email);
//			rabbitmqsender.sendtoqueue(email);
//			System.out.println("send to queue");
//			rabbitmqsender.sendemail(email);
			
		MailService.send(toemailid, subject, body);
			return new Response(200, "registered succesfully..", null);
		}
	}

	public Response verify(String token) {
		Long id = tokenutil.decodeToken(token);
		Optional<User> isPresent = repository.findById(id);
		if (isPresent.isPresent()) {
			isPresent.get().setVerify(true);
			repository.save(isPresent.get());
			return new Response(200, "succesfully registered......", null);

		}
		throw new RegistrationException(200, "user not found...");

	}

	@Override
	public Response login(Login login) {

		Optional<User> isPresent = repository.findByEmailid(login.getEmailid());

		if (isPresent.isPresent()) {
			if (passwordencoder.matches(login.getPassword(), isPresent.get().getPassword())) {

				Response response = new Response();
				System.out.println("login succesfull");
				response.setStatusCode(200);
				response.setStatusMessage("Login succesfully");
				response.setToken(tokenutil.createToken(isPresent.get().getId()));
				return response;
			}
			else {
				System.out.println("wrong emailid or password");
				Response  response=StatusHelper.statusMessage(404, "Password does not match");
				return response;
			}
			
			
		}
		else {
			Response  response=StatusHelper.statusMessage(404, "User does not exists");
			return response;
		}
	}

	@Override
	public Response reset(String password, String token) {
		// TODO Auto-generated method stub

		Long id = tokenutil.decodeToken(token);
		Optional<User> user = repository.findById(id);
		if (user.isPresent()) {
			user.get().setPassword(passwordencoder.encode(password));
			
			user.get().setUpdatedDate(LocalDateTime.now());
			repository.save(user.get());

			return new Response(400, " password set succesfully.....", null);
		}
		throw new RegistrationException(200, "user not found.....");

	}

	@Override
	public Response forgot(ForgotPass forgotpass) {
		// TODO Auto-generated method stub
		String emailid = forgotpass.getEmailid();
		Optional<User> isPresent = repository.findByEmailid(emailid);
		if (isPresent.isPresent()) {
			email.setTo(forgotpass.getEmailid());
			String toemailid = email.getTo();
			email.setFrom("ashutoshrit64@gmail.com");
			String fromemailid = email.getFrom();
			email.setSubject("forgot password link");
			String subject = email.getSubject();
			String token=tokenutil.createToken(isPresent.get().getId());
			email.setBody(mailservice.getLink("http://localhost:4200/reset/", isPresent.get().getId()));
			
			System.out.println(token);
			String body = email.getBody();
			MailService.send(toemailid, subject, body);
			return new Response(400, "link is sent.....", token);
		}
		throw new RegistrationException(400, "user is not present.....");
	}

	@Override
	public boolean verifyuser(String emailid) {
		// TODO Auto-generated method stub
		boolean value = false;
		Optional<User> isPresent = repository.findByEmailid(emailid);
		if (isPresent.isPresent()) {
			value = true;
			return value;
		} else {
			return value;
		}
	}

}
