package com.niulbird.clubmgr.db.service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
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
	@Transactional(readOnly = true)
	public User getUser(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	@Transactional
	@Nullable
	public UUID addResetKey(String username) {
		PasswordReset passwordReset = new PasswordReset();
		User user = getUser(username);
		
		if (user != null) {
			passwordReset.setUser(user);
			passwordReset.setCreated(new Date());
			passwordReset.setResetKey(UUID.randomUUID());
			passwordReset.setComplete(false);
			passwordResetRepository.save(passwordReset);
			return passwordReset.getResetKey();
		}
		
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public PasswordReset findByResetKey(UUID resetKey) {
		return passwordResetRepository.findByResetKey(resetKey);
	}

	@Override
	@Transactional
	public void updatePassword(UUID resetKey, String password) {
		PasswordReset passwordReset = passwordResetRepository.findByResetKey(resetKey);
		if (passwordReset != null && passwordReset.getUser() != null) {
			User user = passwordReset.getUser();
			user.setPassword(password);
			user.setChangePassword(false);
			userRepository.save(user);
			
			passwordReset.setComplete(true);
			passwordReset.setUpdated(new Date());
			passwordResetRepository.save(passwordReset);
		}
	}

	@Override
	@Transactional
	public void updatePassword(User user, String password) {
		if (user != null) {
			user.setPassword(password);
			user.setChangePassword(false);
			userRepository.save(user);
		}
	}
}