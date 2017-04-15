package com.niulbird.clubmgr.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class MailUtil {
	private final Logger log = Logger.getLogger(MailUtil.class);
	
	public boolean sendMail(JavaMailSenderImpl mailSender, 
			SimpleMailMessage mailMessage, 
			String emailAddress, 
			String name, 
			String body) {
		log.info("MailUtil::sendMail(): " + body);
		
		boolean retVal = true;
		
		SimpleMailMessage message = new SimpleMailMessage(mailMessage);
		message.setText("Contact Us Received:\n" +
				"Email: " + emailAddress + "\n" +
				"Name: " + name + "\n" +
				"Message: " + body);
		mailSender.send(message);
		
		return retVal;
	}
	
	public boolean sendMail(JavaMailSenderImpl mailSender,
			String email,
			String subject,
			String body,
			Properties props) {
		log.trace("MailUtil::sendMail(): " + body);
		
		boolean retVal = true;
		MimeMessage message = mailSender.createMimeMessage();
		try {
			message.setFrom(new InternetAddress(props.getProperty("email.fromEmail"), props.getProperty("email.fromName")));
			message.setSubject(subject);
			message.setSentDate(new Date());
			message.setContent(body, "text/html; charset=UTF-8");
			
			InternetAddress emailAddress = new InternetAddress(email);
			message.addRecipient(Message.RecipientType.TO, emailAddress);
			mailSender.send(message);
		} catch (UnsupportedEncodingException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return retVal;
	}
}
