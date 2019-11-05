package com.bridgelabz.util;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bridgelabz.service.MailService;
/**
 *  
 *  Purpose:MESSAGE LISTNER IMPLEMENTATION FOR RABBITMQ
 *
 * @author Ashutosh
 *  @version 1.0
 *  @since   05-11-2019
 */
@Component
public class MessageListnerImpl implements MessageListener{


@Autowired
private MailService  mailsender;
	
	
 @SuppressWarnings("static-access")
public void onMessage(Email email) throws NoSuchFieldException, SecurityException, ClassNotFoundException {
	//email = email.replaceAll("Email ", "Email=");

	
	System.out.println(email);
	System.out.println("to : "+email.to);
	System.out.println("from : "+email.from);
	System.out.println("body:"+email.body);
	System.out.println("subject:"+email.subject);
	//SENDING MESSAGE FROM JMS GETTING FROM RABBITMQ ONE BY ONE
	mailsender.send(email.to, email.subject, email.body);
	System.out.println("****************************************");
	

  
  System.out.println("Message Received:" +email.toString());

  System.out.println(new Date());
  
 }

}
