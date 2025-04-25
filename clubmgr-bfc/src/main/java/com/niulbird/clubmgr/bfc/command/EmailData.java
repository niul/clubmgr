package com.niulbird.clubmgr.bfc.command;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;


public class EmailData {
	
	@NotEmpty @Email
	String email;
	String subject;
	String message;
	
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
