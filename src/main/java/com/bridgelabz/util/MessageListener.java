package com.bridgelabz.util;

/**
 *  
 *  Purpose:INTERFACE OF MESSAGE LISTNER FOR RABBITMQ GETTING FROM QUEUE
 *
 * @author Ashutosh
 *  @version 1.0
 *  @since   05-11-2019
 */
public interface MessageListener 
{
	 public void onMessage(Email email) throws NoSuchFieldException, SecurityException, ClassNotFoundException;
}
