package com.niulbird.clubmgr.email.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
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
	Logger log = LoggerFactory.getLogger(EmailService.class);

	private static final int SECOND = 1000;

	@Autowired
	Properties props;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private Configuration freeMarkerConfiguration;

	@Autowired
	private JavaMailSender mailSender;

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

		mailUtil.sendMail(mailSender, emailList, props.getProperty("email.subject.contact"), body, props);
	}

	@Async
	public void sendPasswordResetEmail(Map<String, Object> map) {
		MailUtil mailUtil = new MailUtil();

		String body = new String();

		try {
			map.put("msg", new MessageResolverMethod(messageSource, null));
			body = FreeMarkerTemplateUtils
					.processTemplateIntoString(freeMarkerConfiguration.getTemplate("passwordReset.ftl"), map);
		} catch (IOException | TemplateException e) {
			log.error("Error generating freemarker template: " + e.getMessage(), e);
		}

		String[] emailList = new String[]{(String) map.get("email")};

		mailUtil.sendMail(mailSender, emailList, props.getProperty("email.subject.password"), body, props);
	}

	@Async
	@Transactional
	public void sendFixtureEmailAsync(String uuid, boolean today) {
		sendFixtureEmail(uuid, today);
	}

	@Transactional
	public void sendFixtureEmail(String uuid, boolean today) {
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
			if ((playerFixtureInfo.getStatus() == Status.PENDING && !today) ||
					(playerFixtureInfo.getStatus() == Status.MAYBE && today)) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("lang", messageSource);
				String body = new String();
				try {
					map.put("msg", new MessageResolverMethod(messageSource, null));
					map.put("fixture", fixture);
					map.put("playerFixtureInfo", playerFixtureInfo);

					if (today) {
						body = FreeMarkerTemplateUtils
								.processTemplateIntoString(freeMarkerConfiguration.getTemplate("fixture-final.ftl"), map);
					} else {
						body = FreeMarkerTemplateUtils
								.processTemplateIntoString(freeMarkerConfiguration.getTemplate("fixture.ftl"), map);
					}
				} catch (IOException | TemplateException e) {
					log.error("Error generating Fixture Email Template: " + e.getMessage(), e);
				}
				String subject = messageSource.getMessage("email.fixture.subject", null, null);
				if (today) {
					subject = messageSource.getMessage("email.fixture-final.subject", null, null);
				}
				log.debug("Sending message to [" + playerFixtureInfo.getPlayer().getEmail() + "]");
				boolean isSent = false;
				try {
					String[] emailList = {playerFixtureInfo.getPlayer().getEmail()};
					isSent = mailUtil.sendMail(mailSender, emailList,
								subject + " - "
								+ dateFormatter.format(fixture.getDate()) + " @ "
								+ timeFormatter.format(fixture.getTime()) + " on " + fixture.getField(),
						body, props);
				} catch (Exception e) {
					log.error("Error sending Email: " + e.getMessage(), e);
				}
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



	@Async
	public void sendAvailabilityUpdateEmail(Map<String, Object> map) {
		MailUtil mailUtil = new MailUtil();

		String body = new String();

		try {
			map.put("msg", new MessageResolverMethod(messageSource, null));
			body = FreeMarkerTemplateUtils
					.processTemplateIntoString(freeMarkerConfiguration.getTemplate("availability-update.ftl"), map);
		} catch (IOException | TemplateException e) {
			log.error("Error generating freemarker template: " + e.getMessage(), e);
		}

		String[] emailList = props.getProperty("email.toEmail.availability.update").split("\\|");

		mailUtil.sendMail(mailSender, emailList, messageSource.getMessage("email.availability.update.subject", null, null), body, props);
	}

}