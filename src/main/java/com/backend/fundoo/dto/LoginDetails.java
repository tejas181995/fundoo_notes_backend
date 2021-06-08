package com.backend.fundoo.dto;

import lombok.Data;

@Data
public class LoginDetails {

	private String email;
	private String password;
	
	public LoginDetails(String email, String password) {
		this.email = email;
		this.password = password;
	}
	
	
}
