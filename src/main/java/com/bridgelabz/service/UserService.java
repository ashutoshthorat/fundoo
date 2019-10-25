package com.bridgelabz.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files
;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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
import com.bridgelabz.util.TokenUtil;
import com.bridgelabz.util.UserResponse;
import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;

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
	
	private final Path fileLocation = Paths.get("/home/user/");
	
	
	
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
	public UserResponse login(Login login) {

		Optional<User> isPresent = repository.findByEmailid(login.getEmailid());

		if (isPresent.isPresent()) {
			if (passwordencoder.matches(login.getPassword(), isPresent.get().getPassword())) {

				LoginModel loginmodel=modelmapper.map(login,LoginModel.class);
				UserResponse response = new UserResponse();
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
				response.setFirstName(isPresent.get().getFname());
				response.setlName(isPresent.get().getLname());
				response.setEmail(isPresent.get().getEmailid());
				return response;
			}
			else {
				System.out.println("wrong emailid or password");
				throw new RegistrationException(200, "password does not match....");
			}
			
			
		}
		else {
			throw new RegistrationException(200, "user not exist....");
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

	
	@Override
	public Response setprofile(MultipartFile path, String token)
//	{ 
	{
		
		
		long userId = tokenutil.decodeToken(token);

		Optional<User> user = repository.findById(userId);

		if (!user.isPresent()) {
			throw new RegistrationException(-5, "user is not present");
		}

		UUID uuid = UUID.randomUUID();

		String uniqueId = uuid.toString();
		try {
			Files.copy(path.getInputStream(), fileLocation.resolve(uniqueId), StandardCopyOption.REPLACE_EXISTING);
			user.get().setProfilepic(uniqueId);
			repository.save(user.get());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  new Response(400, "Profile pic is set.....", null);
		
		
			}

	@Override
	public Resource getprofile(String token) {
		
		
		
		
		long userId = tokenutil.decodeToken(token);

		Optional<User> user = repository.findById(userId);
		if (!user.isPresent()) {
			throw new RegistrationException(-5, "user already exist");
		}

		try {
			Path imageFile = fileLocation.resolve(user.get().getProfilepic());

			Resource resource = new UrlResource(imageFile.toUri());

			if (resource.exists() || (resource.isReadable())) {
				System.out.println(resource);
				return resource;
			} else {
				throw new Exception("Couldn't read file: " + imageFile);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


}
