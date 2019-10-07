package com.bridgelabz.util;


public interface MessageListener 
{
	 public void onMessage(byte[] email) throws NoSuchFieldException, SecurityException, ClassNotFoundException;
}
