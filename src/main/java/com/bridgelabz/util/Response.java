package com.bridgelabz.util;

import org.springframework.stereotype.Component;

@Component
public class Response 
{
	private Integer statusCode;
	private String statusMessage;
	private Object token;
	public Response() {
		// TODO Auto-generated constructor stub
	}
 
	
	public Response(Integer statusCode, String statusMessage, Object token) 
	{
		super();
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
		this.token = token;
	}


	public Object getToken() {
		return token;
	}


	public void setToken(Object token) {
		this.token = token;
	}


	public Integer getStatusCode() {
		return statusCode;
	}
 
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	@Override
	public String toString() {
		return "Response [statusCode=" + statusCode + ", statusMessage=" + statusMessage + ", token=" + token + "]";
	}
	
	
}
