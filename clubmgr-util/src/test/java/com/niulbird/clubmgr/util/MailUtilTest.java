package com.niulbird.clubmgr.util;

import org.junit.Test;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.niulbird.clubmgr.BaseTestCase;
import com.niulbird.clubmgr.util.MailUtil;

public class MailUtilTest extends BaseTestCase {

	@Autowired
	private JavaMailSenderImpl mailSender;
	
	@Autowired
	private SimpleMailMessage mailMessage;
	
	@Test
	public void sendEmailTest() {
		
		MailUtil mailUtil = new MailUtil();
		boolean retVal = mailUtil.sendMail(mailSender, mailMessage, "test@test.com", "Joe Bloggs", "Testing 1 2 3");
		
		Assert.assertTrue(retVal);
	}
}
