package com.niulbird.clubmgr.task;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.niulbird.clubmgr.db.model.Fixture;
import com.niulbird.clubmgr.db.model.PlayerFixtureInfo;
import com.niulbird.clubmgr.db.model.Status;
import com.niulbird.clubmgr.db.service.FixtureService;
import com.niulbird.clubmgr.util.MailUtil;
import com.niulbird.clubmgr.util.freemarker.MessageResolverMethod;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

@Service
public class FixtureAvailabilityJob {
	private static final Logger log = Logger.getLogger(FixtureAvailabilityJob.class);
	
	private static final int SECOND = 1000;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	Properties props;

	@Autowired
	private FixtureService fixtureService;

	@Autowired
	private Configuration freeMarkerConfiguration;

	@Autowired
	private JavaMailSenderImpl mailSender;

	@Transactional
	@Scheduled(cron = "0 15 10 * * *")
	public void sendFixturePlayerStatus() {
		log.debug("Getting Fixtures to send Player Status Email.");
		MailUtil mailUtil = new MailUtil();

		SimpleDateFormat dateFormatter = new SimpleDateFormat("EEE, MMM d");
		SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mma");

		String[] daysBefore = props.getProperty("fixture.days.before", null).split("\\s*,\\s*");

		for (int i = 0; i < daysBefore.length; i++) {
			Date date = getDaysFromCurrentDate(Integer.parseInt(daysBefore[i]));
			log.debug("Getting fixtures for date: " + date);
			List<Fixture> fixtures = fixtureService.findFixturesByDate(date);

			for (Fixture fixture : fixtures) {
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
				
				// Add a little bit of time between fixtures not to overload SMTP server.
				if (playerFixtureInfoList.size() > 0) {
					try {
						Thread.sleep(30 * SECOND);
					} catch (InterruptedException e) {
						log.error("Sleep interupted: " + e.getMessage());
					}
				}
			}
		}
		log.debug("Updated Fixtures successfully");
	}

	private Date getDaysFromCurrentDate(int numberOfDays) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, numberOfDays);

		return new Date(cal.getTimeInMillis());
	}
}
