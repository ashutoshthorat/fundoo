package com.bridgelabz.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.dto.ForgotPass;
import com.bridgelabz.dto.Login;
import com.bridgelabz.dto.UserDto;
import com.bridgelabz.exception.RegistrationException;
import com.bridgelabz.model.LoginModel;
import com.bridgelabz.model.User;
import com.bridgelabz.repository.UserRepository;
import com.bridgelabz.util.Email;
import com.bridgelabz.util.MessageProducer;
import com.bridgelabz.util.Response;
import com.bridgelabz.util.ResponseTime;
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
	private MessageProducer messageproducer;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	
	
	
	public Response register(UserDto userdto) {
		Optional<User> isThere = repository.findByEmailid(userdto.getEmailid());

		if (isThere.isPresent()) {
			throw new RegistrationException(400, "user already present");
		} else {
			userdto.setPassword(passwordencoder.encode(userdto.getPassword()));

			User user = modelmapper.map(userdto, User.class);

			repository.save(user);
			
			
			email.setTo(userdto.getEmailid());
			email.setFrom("ashutoshrit64@gmail.com");
			email.setSubject("Verification...");
			String token=tokenutil.createToken(user.getId());
			email.setBody(mailservice.getLink("http://localhost:4200/verify/", user.getId()));
			messageproducer.sendMessage(email);

			return new Response(200, "registered succesfully..", token);
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

				LoginModel loginmodel=modelmapper.map(login,LoginModel.class);
				Response response = new Response();
				System.out.println("login succesfull");
				
				
				if(redisTemplate.opsForValue().get("loginData").equals(null))
						{
					loginmodel.setTime(LocalTime.now());
					redisTemplate.opsForValue().set("loginData", loginmodel);
					Object data=redisTemplate.opsForValue().get("loginData");
					System.out.println(data.toString());
					  
						}
				else
				{
					Object data=redisTemplate.opsForValue().get("loginData");
					byte[] byteData = SerializationUtils.serialize(data);
					LoginModel loginModel = (LoginModel) SerializationUtils.deserialize(byteData);
					System.out.println("User login last time is:"+loginModel.getTime());
					
					 loginmodel.setTime(LocalTime.now());
					 
					redisTemplate.opsForValue().set("loginData", loginmodel);
					Object data1=redisTemplate.opsForValue().get("loginData");
					System.out.println(data1.toString());
					 
				}
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
			email.setFrom("ashutoshrit64@gmail.com");
			email.setSubject("forgot password link");
			String token=tokenutil.createToken(isPresent.get().getId());
			email.setBody(mailservice.getLink("http://localhost:4200/reset/", isPresent.get().getId()));
			messageproducer.sendMessage(email);
 
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
