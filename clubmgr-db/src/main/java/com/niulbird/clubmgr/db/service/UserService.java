package com.niulbird.clubmgr.db.service;

import java.util.UUID;

import com.niulbird.clubmgr.db.model.PasswordReset;
import com.niulbird.clubmgr.db.model.User;

public interface UserService {
	public User getUser(String username);
	public PasswordReset findByResetKey(UUID resetKey);
	public void updatePassword(UUID resetKey, String password);
	public void updatePassword(User user, String password);
}