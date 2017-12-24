package com.niulbird.clubmgr.email.util;

import org.junit.Test;

import java.util.Properties;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.niulbird.clubmgr.email.BaseTestCase;
import com.niulbird.clubmgr.email.util.MailUtil;

public class MailUtilTest extends BaseTestCase {

	@Autowired
	private JavaMailSenderImpl mailSender;
	
	@Autowired
	private Properties props;
	
	@Test
	public void sendEmailTest() {
		
		MailUtil mailUtil = new MailUtil();
		boolean retVal = mailUtil.sendMail(mailSender, "nbird@incomm.com", "TEST", "Testing 1 2 3", props);
		
		Assert.assertTrue(retVal);
	}
}
