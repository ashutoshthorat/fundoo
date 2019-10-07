package com.bridgelabz.util;

public class StatusHelper {

public static Response statusMessage(int code,String message) {
	Response response=new Response();
	response.setStatusCode(code);
	response.setStatusMessage(message);
	return response;
}
}