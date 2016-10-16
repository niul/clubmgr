package com.niulbird.clubmgr.bfc.command;

import javax.validation.constraints.Size;

public class LoginData {
	@Size(min=8, max=30)
	String username;
	@Size(min=8, max=30)
	String password;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}
