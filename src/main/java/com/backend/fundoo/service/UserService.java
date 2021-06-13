package com.backend.fundoo.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.fundoo.dto.LoginDetails;
import com.backend.fundoo.dto.UpdatePassword;
import com.backend.fundoo.dto.UserDto;
import com.backend.fundoo.exception.UserNotFoundException;
import com.backend.fundoo.exception.UserNotVerifiedException;
import com.backend.fundoo.model.UserEntity;
import com.backend.fundoo.repository.IUserRepository;
import com.backend.fundoo.response.MailResponse;
import com.backend.fundoo.util.JwtGenerator;
import com.backend.fundoo.util.MailServiceProvider;

import antlr.Token;

@Service
public class UserService implements IUserService {
	
	@Autowired
	public BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private JwtGenerator generate;
	
	@Autowired
	private MailServiceProvider mailServiceProvider;

	@Override
	public boolean registration(UserDto userDto) {
//		UserEntity fetchedUser = userRepository.getUser(userDto.getEmail());
//		if (fetchedUser != null)
//			return false;
		UserEntity user = new UserEntity();
		BeanUtils.copyProperties(userDto, user);
		String password = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(password);
		System.out.println(user);
		
		String response = MailResponse.formMessage("http://localhost:8083/user/verification",
				generate.createJwtToken(user.getUserId()));
		mailServiceProvider.sendEmail(user.getEmail(), "Registration verification", response);
		userRepository.save(user);
		return true;
	}

	@Override
	public boolean isVerified(String token) {
		long fetchedUserId = generate.parseJWT(token);
		userRepository.isVerified(fetchedUserId);
		return true;
	}

	@Override
	public UserEntity login(LoginDetails login) {
		UserEntity user = userRepository.getUser(login.getEmail());
		if(user != null) {
			if (bCryptPasswordEncoder.matches(login.getPassword(), user.getPassword())) {				
					return user;		
			
			}
			throw new UserNotVerifiedException("Invalid credentials");
		}
		throw new UserNotFoundException("User not found! ");
	}

	@Override
	public boolean isUserAvailable(String email) {
		UserEntity isUserAvailable = userRepository.getUser(email);
		
		if (isUserAvailable != null) {
			
				String response = MailResponse.formMessage("http://localhost:4200/user/updatePassword/id",
						generate.createJwtToken(isUserAvailable.getUserId()));
				MailServiceProvider.sendEmail(isUserAvailable.getEmail(), "Update password link", response);
				return true;
			
//			// if user is not verified
//			String response = MailResponse.formMessage("http://localhost:8081/user/verification",
//					generate.createJwtToken(isUserAvailable.getUserId()));
//			MailServiceProvider.sendEmail(isUserAvailable.getEmail(), "Registration  verification", response);
//			return false;
		}
		throw new UserNotFoundException("User not found");
	}
	

	@Override
	public boolean updatePassword(String token, UpdatePassword passwordInfo) {
		if (passwordInfo.getNewPassword().equals(passwordInfo.getConfirmPassword())) {
			passwordInfo.setConfirmPassword(bCryptPasswordEncoder.encode(passwordInfo.getConfirmPassword()));
			userRepository.updatePassword(passwordInfo, generate.parseJWT(token));
		}
		return false;
	}
	
	
}
