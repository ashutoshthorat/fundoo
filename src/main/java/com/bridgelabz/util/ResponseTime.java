package com.bridgelabz.util;

import org.springframework.stereotype.Component;

@Component
public class ResponseTime {
	private Integer statusCode;
	private String statusMessage;
	private Object token;
	private String time;
	public ResponseTime() {
		// TODO Auto-generated constructor stub
	}
 
	
	 


	public ResponseTime(Integer statusCode, String statusMessage, Object token, String time) {
		super();
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
		this.token = token;
		this.time = time;
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
	
	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}


	@Override
	public String toString() {
		return "ResponseTime [statusCode=" + statusCode + ", statusMessage=" + statusMessage + ", token=" + token
				+ ", time=" + time + "]";
	}

 

}
