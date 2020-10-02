package com.niulbird.clubmgr.email.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.niulbird.clubmgr.email.BaseTestCase;
import com.niulbird.clubmgr.email.service.EmailService;

public class EmailServiceTest extends BaseTestCase {
	@Autowired
	private EmailService emailService;
	
	@Test
	public void sendEmailTest() {
		
		emailService.sendFixtureEmail("5afa1b20-3e45-4dae-a313-9cceb6647c48", false);
		
		Assert.assertTrue(true);
	}
}
