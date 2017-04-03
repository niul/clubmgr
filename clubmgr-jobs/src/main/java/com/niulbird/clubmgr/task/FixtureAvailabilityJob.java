package com.niulbird.clubmgr.task;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.niulbird.clubmgr.task.util.MessageResolverMethod;
import com.niulbird.clubmgr.util.MailUtil;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

@Service
public class FixtureAvailabilityJob {
	private static final Logger log = Logger.getLogger(FixtureAvailabilityJob.class);

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
	@Scheduled(cron="0 */5 * * * *")
    public void sendFixturePlayerStatus() {
		log.debug("Getting Fixtures to send Player Status Email.");
		MailUtil mailUtil = new MailUtil();
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat("EEE, MMM d");
		SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mma");
		
		List<Fixture> fixtures = new ArrayList<Fixture>();
		try {
			fixtures = fixtureService.findFixturesByDate(new java.sql.Date((new SimpleDateFormat(("yyyy-MM-dd")).parse("2017-04-01").getTime())));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Fixture fixture : fixtures) {
				log.debug("Fixture: " + fixture.getFixtureId());
				log.debug("Date: " + fixture.getDate());
				List<PlayerFixtureInfo> playerFixtureInfoList = fixtureService.findPlayerInfoByFixture(fixture);
			
				for(PlayerFixtureInfo playerFixtureInfo : playerFixtureInfoList) {
					log.debug("Player: " + playerFixtureInfo.getPlayer().getFirstName() + " " + playerFixtureInfo.getPlayer().getLastName());
					if (playerFixtureInfo.getStatus() == Status.PENDING) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("lang", messageSource);
						String body = new String();
						try {
							map.put("msg", new MessageResolverMethod(messageSource, null));
							map.put("fixture", fixture);
							map.put("playerFixtureInfo", playerFixtureInfo);
							body = FreeMarkerTemplateUtils.processTemplateIntoString(freeMarkerConfiguration.getTemplate("fixture.ftl"),
									map);
						} catch (IOException | TemplateException e) {
							log.error("Error generating Fixture Email Template: " + e.getMessage(), e);
						}
						System.setProperty("socksProxyHost", "localhost");
				        System.setProperty("socksProxyPort", "3128");
						boolean isSent = mailUtil.sendMail(mailSender, 
								playerFixtureInfo.getPlayer().getEmail(), 
								messageSource.getMessage("email.fixture.subject", null, null) + " - " + dateFormatter.format(fixture.getDate()) 
								+ " @ " + timeFormatter.format(fixture.getTime()) + " on " + fixture.getField(), 
								body, 
								props);
						log.debug("Message sent to [" + playerFixtureInfo.getPlayer().getEmail() + "]: " + isSent);
						System.clearProperty("socksProxyHost");
				        System.clearProperty("socksProxyPort");
					}
				}
		}
		log.debug("Updated Fixtures successfully");
    }
}
