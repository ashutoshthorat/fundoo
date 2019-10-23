package com.bridgelabz.util;

import java.util.Date;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bridgelabz.configuration.RabbitMqconfig;

@Component
public class MessageProducer {
 
 @Autowired
    private RabbitTemplate rabbitTemplate;
 
 public void sendMessage(Email email) {
byte[] emailString = email.toString().getBytes();

  System.out.println(new Date());
  System.out.println();
  rabbitTemplate.convertAndSend(RabbitMqconfig.ROUTING_KEY, email);
     System.out.println("Is Producer returned ::: "+rabbitTemplate.isReturnListener());
     System.out.println(email);
     System.out.println(new Date());
 }
}