package com.niulbird.clubmgr.bfc.command;

import org.hibernate.validator.constraints.Email;

public class PasswordEmailData {
	@Email
	String email;
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
}
