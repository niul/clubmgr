package com.niulbird.clubmgr.email.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.springframework.mail.javamail.JavaMailSenderImpl;

public class MailUtil {
	private final Logger log = LogManager.getLogger();
	
	public boolean sendMail(JavaMailSenderImpl mailSender,
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
			// TODO Auto-generated catch block
			e.printStackTrace();
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
