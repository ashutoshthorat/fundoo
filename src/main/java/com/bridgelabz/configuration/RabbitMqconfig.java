package com.bridgelabz.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bridgelabz.util.MessageListener;
 

 

@Configuration
public class RabbitMqconfig {
 
 public static final String ROUTING_KEY = "my.queue.key";
 
 @Bean
 Queue queue() {
  return new Queue(ROUTING_KEY, true);
 }


 @Bean
 TopicExchange exchange() {
  return new TopicExchange("my_queue_exchange");
 }


 @Bean
 Binding binding(Queue queue, TopicExchange exchange) {
  return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
 }
 //Below highlighted for RabbitMQ listener


 @Bean
 SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
 MessageListenerAdapter listenerAdapter) {
  SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
  container.setConnectionFactory(connectionFactory);
  container.setQueueNames(ROUTING_KEY);
  container.setMessageListener(listenerAdapter);
  return container;	
 }


 @Bean
 MessageListenerAdapter myQueueListener(MessageListener listener) {
  return new MessageListenerAdapter(listener, "onMessage");
 }
}
