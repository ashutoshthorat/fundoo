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

/**
 *  
 *  Purpose:Service implementation of the user
 *
 * @author Ashutosh
 *  @version 1.0
 *  @since   05-11-2019
 */
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
	
	private final Path fileLocation = Paths.get("/home/user/profilephoto");
	
	
	/**
	 *  
	 *  Purpose:Register service is written
	 *
	 * @author Ashutosh
	 *  @version 1.0
	 *  @since   05-11-2019
	 */
	public Response register(UserDto userdto) {
		//check whether the given email id is present in the database
		Optional<User> isThere = repository.findByEmailid(userdto.getEmailid());

		if (isThere.isPresent()) {
			throw new RegistrationException(400, "user already present");
		} else {
			userdto.setPassword(passwordencoder.encode(userdto.getPassword()));
			//map the all entities with userdto
			User user = modelmapper.map(userdto, User.class);
			//save the user in database
			repository.save(user);
			
			//send email to the user for verification
			email.setTo(userdto.getEmailid());
			email.setFrom("ashutoshrit64@gmail.com");
			email.setSubject("Verification...");
			String token=tokenutil.createToken(user.getId());
			email.setBody(mailservice.getLink("http://localhost:4200/verify/", user.getId()));
			messageproducer.sendMessage(email);

			return new Response(200, "registered succesfully..", token);
		}
	}
	/**
	 *  
	 *  Purpose:verification
	 *
	 * @author Ashutosh
	 *  @version 1.0
	 *  @since   05-11-2019
	 */
	//verification
	public Response verify(String token) {
		Long id = tokenutil.decodeToken(token);
		Optional<User> isPresent = repository.findById(id);
		//if user present and is active verified succefully
		if (isPresent.isPresent()) {
			isPresent.get().setVerify(true);
			repository.save(isPresent.get());
			return new Response(200, "succesfully registered......", null);

		}
		throw new RegistrationException(200, "user not found...");

	}

	/**
	 *  
	 *  Purpose:login service
	 *
	 * @author Ashutosh
	 *  @version 1.0
	 *  @since   05-11-2019
	 */
	@Override
	public UserResponse login(Login login) {
		//SEARCH EMAIL ID IN DATABASE
		Optional<User> isPresent = repository.findByEmailid(login.getEmailid());

		if (isPresent.isPresent()) {
			//CHECK FOR PASSWORD WHETHER IT IS CORRECT
			if (passwordencoder.matches(login.getPassword(), isPresent.get().getPassword())) {
				//IF PRESENT LOGIN SUCCESFULLY DONE
				LoginModel loginmodel=modelmapper.map(login,LoginModel.class);
				UserResponse response = new UserResponse();
				System.out.println("login succesfull");
				
				//TO SEARCH FOR THE USER HAS INTIALLY WHEN LOGIN USING REDDIS
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
				//SEND THE RESPONSE BACK TO THE USER
				response.setStatusCode(200);
				response.setStatusMessage("Login succesfully");
				response.setToken(tokenutil.createToken(isPresent.get().getId()));
				response.setFirstName(isPresent.get().getFname());
				response.setlName(isPresent.get().getLname());
				response.setEmail(isPresent.get().getEmailid());
				return response;
			}
			else {
				//THROW EXCEPTION IF EMAIL OR PASSWORD DOES NOT MATCH
				System.out.println("wrong emailid or password");
				throw new RegistrationException(200, "password does not match....");
			}
			
			
		}
		else {
			throw new RegistrationException(200, "user not exist....");
		}
	}

	/**
	 *  
	 *  Purpose:SERVICE FOR RESET PASSWORD
	 *
	 * @author Ashutosh
	 *  @version 1.0
	 *  @since   05-11-2019
	 */
	@Override
	public Response reset(String password, String token) {
		// TODO Auto-generated method stub
		//DECODING TOKEN
		Long id = tokenutil.decodeToken(token);
		//CHECKING USER PRESENT OR NOT
		Optional<User> user = repository.findById(id);
		if (user.isPresent()) {
			//SETTING THE NEW PASSWORD AND SAVING INTO THE DATABASE
			user.get().setPassword(passwordencoder.encode(password));
			
			user.get().setUpdatedDate(LocalDateTime.now());
			repository.save(user.get());

			return new Response(400, " password set succesfully.....", null);
		}
		//THROWING EXCEPTION IF USER NOT PRESENT
		throw new RegistrationException(200, "user not found.....");

	}
	/**
	 *  
	 *  Purpose:FORGOT PASSWORD METHOD
	 *
	 * @author Ashutosh
	 *  @version 1.0
	 *  @since   05-11-2019
	 */
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

	/**
	 *  
	 *  Purpose:USER VERIGICATION SERVICE
	 *
	 * @author Ashutosh
	 *  @version 1.0
	 *  @since   05-11-2019
	 */
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

	/**
	 *  
	 *  Purpose:FOR SETTING PROFILE
	 *
	 * @author Ashutosh
	 *  @version 1.0
	 *  @since   05-11-2019
	 */
	@Override
	public Response setprofile(MultipartFile path, String token)
	{
		//TAKING PATH INTO MULTIPART FILE
		
		long userId = tokenutil.decodeToken(token);

		Optional<User> user = repository.findById(userId);

		if (!user.isPresent()) {
			throw new RegistrationException(-5, "user is not present");
		}
		//CONVERTING PATH INTO RANDOM UNIQUE ID AND SAVING INTO THE DATABASE
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
	/**
	 *  
	 *  Purpose:SERVICE FOR GETTING USER PROFILE
	 *
	 * @author Ashutosh
	 *  @version 1.0
	 *  @since   05-11-2019
	 */
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

	/**
	 *  
	 *  Purpose:FINDING USER PRESENT OR NOT
	 *
	 * @author Ashutosh
	 *  @version 1.0
	 *  @since   05-11-2019
	 */
	public long getuser(String emailid)
	{
	
	Optional<User> user=repository.findByEmailid(emailid);
	if(user.isEmpty()) {
		throw new RegistrationException(400, "user is not present.....");
	}
	else 
	{
		return user.get().getId();
	}
	 
	}
	/**
	 *  
	 *  Purpose:CHECKL THE USER IS PRESENT OR NOT
	 *
	 * @author Ashutosh
	 *  @version 1.0
	 *  @since   05-11-2019
	 */
	@Override
	public boolean checkuser(String emailid) {
		// TODO Auto-generated method stub
		Optional<User> user=repository.findByEmailid(emailid);
		if(user.isEmpty()) {
			return false;
		}
		else 
		{
			return true;
		}
		 
	}

	 
}
