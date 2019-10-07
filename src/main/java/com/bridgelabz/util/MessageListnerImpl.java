package com.bridgelabz.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bridgelabz.service.MailService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MessageListnerImpl implements MessageListener{


@Autowired
private MailService  mailsender;
	
	
 @SuppressWarnings("static-access")
public void onMessage(byte[] email) throws NoSuchFieldException, SecurityException, ClassNotFoundException {
	//email = email.replaceAll("Email ", "Email=");
	
	
	
	
	
	
	 //System.out.println(Class.forName(email).getField("Email "));
	 ObjectMapper mapper = new ObjectMapper();
  System.out.println(new Date());
  try {
   Thread.sleep(5000);
  } catch (InterruptedException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  }


try {
	Email emailBody;
	String json=mapper.writerWithDefaultPrettyPrinter().writeValueAsString(email.toString());
	System.out.println("json-->"+json);
//	System.out.println(email);
	emailBody = mapper.readValue(email, Email.class);
	System.out.println( email);
	//System.out.println(emailBody.toString().getClass().getField("Email "));
//String to = null;
	//	Email emailBody = (Email)emai;
//	System.out.println(emailBody);

	System.out.println("****************************************"+emailBody );
	
	System.out.println("****************************************");
	//System.out.println(too.toString());
//	System.out.println("-->"+emai.getTo());
//	String to=emai.getTo();
//	 String subject=emai.getSubject();
//	 String body=emai.getBody();
//	  mailsender.send(to, subject, body);
} catch (JsonParseException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (JsonMappingException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
  
  System.out.println("Message Received:" +email.toString());

  System.out.println(new Date());
  
 }

}
