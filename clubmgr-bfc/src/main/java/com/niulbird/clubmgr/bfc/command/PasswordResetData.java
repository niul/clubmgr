package com.niulbird.clubmgr.bfc.command;

import javax.validation.constraints.Size;

import org.springmodules.validation.bean.conf.loader.annotation.handler.RegExp;

public class PasswordResetData {
	@RegExp(value = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,30})")
	@Size(min=8, max=30)
	String password;
	
	@RegExp(value = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,30})")
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
