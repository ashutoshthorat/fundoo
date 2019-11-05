package com.bridgelabz.repository;

import java.util.Optional;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.model.User;

/**
 *  
 *  Purpose:Repository connected to the database
 *
 * @author Ashutosh
 *  @version 1.0
 *  @since   05-11-2019
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>
{

	public Optional<User> findByEmailid(String emailid);
	public Optional<User> findByEmailidAndPassword(String emailid,String password);
	
}
