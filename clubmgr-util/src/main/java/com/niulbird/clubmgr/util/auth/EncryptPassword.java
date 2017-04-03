package com.niulbird.clubmgr.util.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncryptPassword {
	public static void main(String args[]) throws Exception {
		String cryptedPassword = new BCryptPasswordEncoder().encode("pass123");
		System.out.println(cryptedPassword);
	}
}