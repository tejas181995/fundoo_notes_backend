package com.backend.fundoo.repository;

import com.backend.fundoo.dto.UpdatePassword;
import com.backend.fundoo.model.UserEntity;

public interface IUserRepository {

	public UserEntity save(UserEntity user);
	public UserEntity getUser(String email);
	public UserEntity getUser(long userId);
	public boolean isVerified(long userId);
	public boolean updatePassword(UpdatePassword updatePassword, long userId);
}
