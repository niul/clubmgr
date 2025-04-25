package com.niulbird.clubmgr.email.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class MailUtil {
	private final Logger log = LoggerFactory.getLogger(MailUtil.class);
	
	public boolean sendMail(JavaMailSender mailSender,
			String[] emailList,
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
			
			for (String email : emailList) {
				InternetAddress emailAddress = new InternetAddress(email);
				message.addRecipient(Message.RecipientType.TO, emailAddress);
			}
			mailSender.send(message);
		} catch (UnsupportedEncodingException | MessagingException e) {
			log.error("Error Sending Email: " + e.getMessage(), e);
		}
		
		return retVal;
	}
	
	public boolean sendMail(JavaMailSenderImpl mailSender,
			String email,
			String subject,
			String body,
			Properties props) {
		String[] emailArr = new String[] {email};
		
		return sendMail(mailSender, emailArr, subject, body, props);
	}
}
