package com.backend.fundoo.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Setter
public class UserAuthenticationResponse {
	
	private String tokenCode;
	private int statusCode;
	
	public UserAuthenticationResponse(String tokenCode, int statusCode) {
		
		this.tokenCode = tokenCode;
		this.statusCode = statusCode;
		
	}
	
	
	
	
}
