package com.bridgelabz;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bridgelabz.dto.Login;
import com.bridgelabz.dto.UserDto;
import com.bridgelabz.model.LoginModel;
import com.bridgelabz.model.User;
import com.bridgelabz.repository.UserRepository;
import com.bridgelabz.service.UserService;
import com.bridgelabz.util.Response;
import com.bridgelabz.util.UserResponse;

/**
 * @author user
 *
 */

public class UserTestCases 
{

	@InjectMocks
	private UserService userservice;
	@Mock
	private UserRepository userrepository;
	@Mock
	private ModelMapper modelmapper;
	@Mock
	private PasswordEncoder passwordencoder;
	
	User  user=new User("ashutosh","thorat","ashutoshrit64@gmail.com","ashu123","12/07/1998","7768951521");
	@Test
	public void registerTest()
	{
		UserDto userdto=new UserDto();
		userdto.setFname("ashutosh");
		userdto.setLname("thorat");
		userdto.setEmailid("ashutoshrit64@gmail.com");
		userdto.setDob("12/07/1998");
		userdto.setPhoneno("7768951521");
		userdto.setPassword("ashu123");
		Optional<User> already = Optional.of(user);
//		when(userrepository.findByEmailid(anyString()).isPresent()).thenThrow(new UserException());
//		when(passwordencoder.encode(userdto.getPassword())).thenReturn(userdto.getPassword());
//		when(modelmapper.map(userdto, User.class)).thenReturn(user);
//		when(userrepository.save(user)).thenReturn(user);
		assertEquals(userdto.getEmailid(), already.get().getEmailid()); 
/*
 * Optional<User> already = Optional.of(user);
 *  when(userRepository.findByEmail(anyString()).isPresent()).thenThrow(new UserException());
 *   when(passwordEncoder.encode(userDto.getPassword())).thenReturn(userDto.getPassword());
 *    when(modelMappper.map(userDto, User.class)).thenReturn(user); when(userRepository.save(user)).thenReturn(user); // Response response = serviceImpl.register(userDto);
 *     assertEquals(userDto.getEmail(), already.get().getEmail()); 
 *     } @Test public void loginTest() 
 *     { LoginDTO loginDto = new LoginDTO(); 
 *     loginDto.setEmail("nainesh40@gmail.com");
 *      loginDto.setPassword("3940"); 
 *      Optional<User> already = Optional.of(user);
 *       when(userRepository.findByEmail(anyString()).isEmpty()).thenThrow(new UserException()); 
 *       when(passwordEncoder.matches(loginDto.getPassword(), user.getPassword())).thenReturn(true); 
 *       assertEquals(loginDto.getEmail(), already.get().getEmail());
 *        }
*/		
		
	}
	
	@Test
	public void loginTest() 
	{
		 Login login=new Login();
		 login.setEmailid("ashutoshrit64@gmail.com");
		 login.setPassword("ashu123");
 when(userrepository.findByEmailid("ashutoshrit64@gmail.com"));
		 when(passwordencoder.matches(login.getPassword(),"ashu123")).thenReturn(true);
		 UserResponse response=userservice.login(login);
		  assertEquals(200,response.getStatusCode());
	}

	 
	
	
}
