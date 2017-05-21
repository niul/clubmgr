package com.niulbird.clubmgr.email.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.niulbird.clubmgr.db.model.Fixture;
import com.niulbird.clubmgr.db.model.PlayerFixtureInfo;
import com.niulbird.clubmgr.db.model.Status;
import com.niulbird.clubmgr.db.service.FixtureService;
import com.niulbird.clubmgr.email.util.MailUtil;
import com.niulbird.clubmgr.util.freemarker.MessageResolverMethod;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

@Service
public class EmailService {
	Logger log = Logger.getLogger(EmailService.class);
	
	private static final int SECOND = 1000;

	@Autowired
	Properties props;
	
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private Configuration freeMarkerConfiguration;
	
	@Autowired
	private JavaMailSenderImpl mailSender;
	
	@Autowired
	private FixtureService fixtureService;
	
	@Async
	public void sendContactEmail(Map<String, Object> map) {
		MailUtil mailUtil = new MailUtil();
		
		String body = new String();

		try {
			map.put("msg", new MessageResolverMethod(messageSource, null));
			body = FreeMarkerTemplateUtils
					.processTemplateIntoString(freeMarkerConfiguration.getTemplate("contact.ftl"), map);
		} catch (IOException | TemplateException e) {
			log.error("Error generating freemarker template: " + e.getMessage(), e);
		}
					
		String[] emailList = props.getProperty("email.toEmail.contact." + map.get("subject")).split("\\|");
		
		mailUtil.sendMail(mailSender, emailList, props.getProperty("email.subject"), body, props);
	}

	@Async
	@Transactional
	public void sendFixtureEmailAsync(String uuid) {
		sendFixtureEmail(uuid);
	}
	
	@Transactional
	public void sendFixtureEmail(String uuid) {
		MailUtil mailUtil = new MailUtil();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("EEE, MMM d");
		SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a");
		
		Fixture fixture = fixtureService.findFixtureByUuid(uuid);
		
		log.debug("Fixture: " + fixture.getFixtureId());
		log.debug("Date: " + fixture.getDate());
		
		List<PlayerFixtureInfo> playerFixtureInfoList = fixtureService.findPlayerInfoByFixture(fixture);

		for (PlayerFixtureInfo playerFixtureInfo : playerFixtureInfoList) {
			log.debug("Player: " + playerFixtureInfo.getPlayer().getFirstName() + " "
					+ playerFixtureInfo.getPlayer().getLastName());
			if (playerFixtureInfo.getStatus() == Status.PENDING) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("lang", messageSource);
				String body = new String();
				try {
					map.put("msg", new MessageResolverMethod(messageSource, null));
					map.put("fixture", fixture);
					map.put("playerFixtureInfo", playerFixtureInfo);
					body = FreeMarkerTemplateUtils
							.processTemplateIntoString(freeMarkerConfiguration.getTemplate("fixture.ftl"), map);
				} catch (IOException | TemplateException e) {
					log.error("Error generating Fixture Email Template: " + e.getMessage(), e);
				}
				boolean isSent = mailUtil.sendMail(mailSender, playerFixtureInfo.getPlayer().getEmail(),
						messageSource.getMessage("email.fixture.subject", null, null) + " - "
								+ dateFormatter.format(fixture.getDate()) + " @ "
								+ timeFormatter.format(fixture.getTime()) + " on " + fixture.getField(),
						body, props);
				log.debug("Message sent to [" + playerFixtureInfo.getPlayer().getEmail() + "]: " + isSent);
				
				// Give some time between sending out emails not to overload SMTP server.
				try {
					Thread.sleep(3 * SECOND);
				} catch (InterruptedException e) {
					log.error("Sleep interupted: " + e.getMessage());
				}
			}
		}
	}
	
}