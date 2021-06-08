package com.backend.fundoo.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class UserDto {

	@NotBlank
	@Pattern(regexp = "[a-zA-Z]*", message = "Enter valid first name")
	private String firstName;
	
	@NotBlank
	@Pattern(regexp = "[a-zA-Z]*", message = "Enter valid last name")
	private String lastName;
	
	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	@Pattern(regexp =  "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Enter a valid password")
	private String password;
}
