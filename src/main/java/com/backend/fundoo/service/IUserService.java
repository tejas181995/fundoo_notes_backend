package com.backend.fundoo.service;

import org.springframework.stereotype.Component;

import com.backend.fundoo.dto.LoginDetails;
import com.backend.fundoo.dto.UpdatePassword;
import com.backend.fundoo.dto.UserDto;
import com.backend.fundoo.model.UserEntity;

@Component
public interface IUserService {

	public boolean registration(UserDto userDto);
	public boolean isVerified(String token);
	public UserEntity login(LoginDetails login);
	public boolean isUserAvailable(String email);
	public boolean updatePassword(String token, UpdatePassword passwordInfo);
}