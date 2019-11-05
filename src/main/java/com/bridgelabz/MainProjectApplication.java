package com.bridgelabz;

/*
 * JAVA STYLE GUIDE - SOURCE FILE HEADER STYLE
 * NOTE - A SINGLE BLANK LINE SEPARATES THE TWO BLOCKS i.e. BETWEEN HEADER, PACKAGE, 
 *        IMPORT STATEMENTS, CLASS DECLARATION, etc
 */

/******************************************************************************
 *   
 *  
 *  Purpose: USER REGISTRATION LOGIN RESETPASSWORD VERIFYUSER
 *
 *  @author  ASHUTOSH
 *  @version 1.0
 *  @since   05-11-2019
 *
 ******************************************************************************/

/*
 * JAVA STYLE GUIDE - PACKAGE NAMING STYLE
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
 
@SpringBootApplication
@ComponentScan("com.bridgelabz")
@EnableJpaRepositories("com.bridgelabz.repository")
@EnableEurekaClient
public class MainProjectApplication {


	
	public static void main(String[] args) {
		SpringApplication.run(MainProjectApplication.class, args);

	
	
	
	}

}
