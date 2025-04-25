package com.niulbird.clubmgr.bfc.command;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PasswordResetData {
	@Pattern(regexp =  "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%]).{8,30})")
	@Size(min=8, max=30)
	String password;
	
	@Pattern(regexp =  "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%]).{8,30})")
	@Size(min=2, max=30)
	String passwordRepeat;
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPasswordRepeat() {
		return passwordRepeat;
	}
	
	public void setPasswordRepeat(String passwordRepeat) {
		this.passwordRepeat = passwordRepeat;
	}
}
