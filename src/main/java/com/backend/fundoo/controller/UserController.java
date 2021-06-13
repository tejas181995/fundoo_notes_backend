package com.backend.fundoo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.fundoo.dto.LoginDetails;
import com.backend.fundoo.dto.UpdatePassword;
import com.backend.fundoo.dto.UserDto;
import com.backend.fundoo.model.UserEntity;
import com.backend.fundoo.service.IUserService;
import com.backend.fundoo.util.JwtGenerator;

import antlr.Token;

import  com.backend.fundoo.response.Response;
import com.backend.fundoo.response.UserAuthenticationResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService service;
	
	@Autowired
	private JwtGenerator generate;
	

	@CrossOrigin(origins = "*")
	@PostMapping("/registration")
	@ApiOperation(value = "Api to register user", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "registration successfull"),
			@ApiResponse(code = 400, message = "The resource you were trying to fetch is not found") })
	public ResponseEntity<Response> registration(@RequestBody UserDto userDto) {
		boolean result = service.registration(userDto);
		if (result)
			return ResponseEntity.status(HttpStatus.CREATED).body(new Response("registration successful", 200));
		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new Response("user exists", 400));

	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("login")
	@ApiOperation(value = "Api for user login", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "login successful"),
	@ApiResponse(code = 400, message = "The resource you were trying to fetch is not found") })
	public ResponseEntity<UserAuthenticationResponse> login(@RequestBody LoginDetails loginInfo) {
		UserEntity fetchedUser = service.login(loginInfo);
		if(fetchedUser != null) {
			
				String generatedToken = generate.createJwtToken(fetchedUser.getUserId());
				System.out.println("token : "+ generatedToken);
				return ResponseEntity.status(HttpStatus.ACCEPTED).header(generatedToken, loginInfo.getEmail())
						.body(new UserAuthenticationResponse(generatedToken, 200));
			
		}
		
		return null;
	}
	
	@PostMapping("forgotPassword")
	@ApiOperation(value = "Api to send forgot password link", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "link sent"),
			@ApiResponse(code = 400, message = "The resource you were trying to fetch is not found") })
	public ResponseEntity<Response> forgotPassword(@RequestParam("email") String email) {
		boolean result = service.isUserAvailable(email);
		System.out.println(service.isUserAvailable(email));
		return (result) ? ResponseEntity.status(HttpStatus.FOUND).body(new Response("User found", 200))
				: ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Response("User not verified", 400));
	}
	
	@PutMapping("updatePassword/{token}")
	@ApiOperation(value = "Api to update password", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "password updated"),
	@ApiResponse(code = 304, message = "The resource you were trying to fetch is not found") })
	public ResponseEntity<Response> updatePassword(@PathVariable("token") String token,
			@RequestBody UpdatePassword pwd) {
		boolean result = service.updatePassword(token, pwd);
		return (result) ? ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("Password updated", 200))
				: ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(new Response("Password not updated", 304));
	}
	@GetMapping("verification/{token}")
	@ApiOperation(value = "Api to verify user", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "verification done"),
			@ApiResponse(code = 400, message = "The resource you were trying to fetch is not found") })
	public ResponseEntity<Response> verifyRegistration(@PathVariable("token") String token) {
		if (service.isVerified(token))
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("verified", 200));
		return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new Response("not verified", 400));
	}
	@PostMapping("logout")
	public String logout () {
		
		return "Logout successfully";
	}
	
	
}
