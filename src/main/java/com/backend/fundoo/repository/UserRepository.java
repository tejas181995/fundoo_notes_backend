package com.backend.fundoo.repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.backend.fundoo.dto.UpdatePassword;
import com.backend.fundoo.model.UserEntity;

@Repository
@SuppressWarnings({ "rawtypes", "deprecation" })
public class UserRepository implements IUserRepository{
	
	@Autowired
	private EntityManager entityManager;
	
	@Override
	public UserEntity save(UserEntity user) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(user);
		return user;
	}

	@Override
	public UserEntity getUser(String email) {
		Session session = entityManager.unwrap(Session.class);
		Query emailQuery = session.createQuery("FROM UserEntity where email=:email");
		emailQuery.setParameter("email", email);
		return (UserEntity) emailQuery.uniqueResult();
	}

	@Override
	public UserEntity getUser(long userId) {
		Session session = entityManager.unwrap(Session.class);
		Query idQuery = session.createQuery("FROM UserEntity where userId=:userId");
		idQuery.setParameter("userId", userId);
		return (UserEntity) idQuery.uniqueResult();
	}

	@Override
	@Transactional
	public boolean isVerified(long userId) {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("update UserEntity set is_verified=:verified" + " where userId=:userId");
		query.setParameter("userId", userId);
		int affectedRows = query.executeUpdate();
		if(affectedRows > 0)
			return true;
		return false;
	}

	@Override
	@Transactional
	public boolean updatePassword(UpdatePassword updatePassword, long userId) {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("update UserEntity set password=:updatedPassword" + " where userId=:userId");
		query.setParameter("updatedPassword", updatePassword.getConfirmPassword());
		query.setParameter("userId", userId);
		query.executeUpdate();
		return false;
	}

}
