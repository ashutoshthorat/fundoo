package com.bridgelabz.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{

	public Optional<User> findByEmailid(String emailid);
	public Optional<User> findByEmailidAndPassword(String emailid,String password);
	
	
	
	
}
