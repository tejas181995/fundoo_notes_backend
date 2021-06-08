package com.backend.fundoo.dto;

import lombok.Data;

@Data
public class UpdatePassword {
	
	private String newPassword;
	private String confirmPassword;
	
	public UpdatePassword(String newPassword, String confirmPassword) {
		super();
		this.newPassword = newPassword;
		this.confirmPassword = confirmPassword;
	}
	
	
}
