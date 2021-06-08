package com.backend.fundoo.response;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Setter
public class Response {

	private String message;
	
	private int statusCode;
	
	private Object object;
	
	List<String> details;

	public Response(String message, int statusCode, Object object) {
		super();
		this.message = message;
		this.statusCode = statusCode;
		this.object = object;
	}

	public Response(String message, int statusCode) {
		super();
		this.message = message;
		this.statusCode = statusCode;
	}
	
}