package com.bridgelabz.util;

public class StatusHelper {

	/**
	 *  
	 *  Purpose:RESPONSE WITH STATUS MESSAGE
	 *
	 * @author Ashutosh
	 *  @version 1.0
	 *  @since   05-11-2019
	 */
public static Response statusMessage(int code,String message) {
	Response response=new Response();
	response.setStatusCode(code);
	response.setStatusMessage(message);
	return response;
}
}