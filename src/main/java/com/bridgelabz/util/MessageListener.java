package com.bridgelabz.util;


public interface MessageListener 
{
	 public void onMessage(Email email) throws NoSuchFieldException, SecurityException, ClassNotFoundException;
}
