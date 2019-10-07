package com.bridgelabz;

import javax.sound.midi.Receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.rabbitmq.client.ConnectionFactory;

@SpringBootApplication
@ComponentScan("com.bridgelabz")
@EnableJpaRepositories("com.bridgelabz.repository")
 

public class MainProjectApplication {


	
	public static void main(String[] args) {
		SpringApplication.run(MainProjectApplication.class, args);

	
	
	
	}

}
