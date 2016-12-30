package com.niulbird.clubmgr.db.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.niulbird.clubmgr.db.model.PasswordReset;
import com.niulbird.clubmgr.db.model.User;
import com.niulbird.clubmgr.db.repository.PasswordResetRepository;
import com.niulbird.clubmgr.db.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private PasswordResetRepository passwordResetRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public User getUser(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	@Transactional
	public PasswordReset findByResetKey(UUID resetKey) {
		return passwordResetRepository.findByResetKey(resetKey);
	}

	@Override
	@Transactional
	public void updatePassword(UUID resetKey, String password) {
		PasswordReset passwordReset = passwordResetRepository.findByResetKey(resetKey);
		User user = passwordReset.getUser();
		user.setPassword(password);
		user.setChangePassword(false);
		userRepository.save(user);
		
		passwordReset.setComplete(true);
		passwordReset.setUpdated(new Date());
		passwordResetRepository.save(passwordReset);
	}

	@Override
	public void updatePassword(User user, String password) {
		user.setPassword(password);
		user.setChangePassword(false);
		userRepository.save(user);
	}
}