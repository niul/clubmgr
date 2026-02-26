package com.niulbird.clubmgr.email.service;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.niulbird.clubmgr.db.model.Fixture;
import com.niulbird.clubmgr.db.model.PlayerFixtureInfo;
import com.niulbird.clubmgr.email.util.MailUtil;
import com.niulbird.clubmgr.util.freemarker.MessageResolverMethod;

import freemarker.template.Configuration;

/**
 * Processes player emails from a queue at a scheduled rate.
 * This prevents SMTP server overload by spacing out individual player email sends.
 * 
 * Does NOT depend on EmailService to avoid circular dependencies.
 */
@Service
public class ScheduledPlayerEmailProcessor {
	private static final Logger log = LoggerFactory.getLogger(ScheduledPlayerEmailProcessor.class);

	@Value("${email.player.delay.ms:3000}")
	private long playerEmailDelayMs;

	@Autowired
	private Properties props;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private Configuration freeMarkerConfiguration;

	@Autowired
	private JavaMailSender mailSender;

	// Queue to hold pending player emails
	private final Queue<PlayerEmailTask> playerEmailQueue = new ConcurrentLinkedQueue<>();

	/**
	 * Queue a player email for sending.
	 */
	public void queuePlayerEmail(PlayerFixtureInfo playerFixtureInfo, Fixture fixture, boolean today) {
		playerEmailQueue.offer(new PlayerEmailTask(playerFixtureInfo, fixture, today));
		log.debug("Queued player email: player={}, fixture={}, queueSize={}", 
			playerFixtureInfo.getPlayer().getLastName(), fixture.getFixtureId(), playerEmailQueue.size());
	}

	/**
	 * Processes one player email from the queue at a scheduled interval.
	 * Runs every configured email.player.delay.ms milliseconds.
	 */
	@Scheduled(fixedRateString = "${email.player.delay.ms:3000}")
	public void processNextPlayerEmail() {
		PlayerEmailTask task = playerEmailQueue.poll();
		if (task != null) {
			try {
				log.debug("Processing queued player email: player={}, fixture={}", 
					task.playerFixtureInfo.getPlayer().getLastName(), task.fixture.getFixtureId());
				sendPlayerEmail(task.playerFixtureInfo, task.fixture, task.today);
			} catch (Exception e) {
				log.error("Error processing queued player email: " + e.getMessage(), e);
			}
		}
	}

	/**
	 * Sends the actual email to a player for a fixture.
	 * This logic is extracted from EmailService to avoid circular dependencies.
	 */
	private void sendPlayerEmail(PlayerFixtureInfo playerFixtureInfo, Fixture fixture, boolean today) {
		MailUtil mailUtil = new MailUtil();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("EEE, MMM d");
		SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a");

		String subject = messageSource.getMessage("email.fixture.subject", null, null);
		if (today) {
			subject = messageSource.getMessage("email.fixture-final.subject", null, null);
		}

		String body = new String();
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("lang", messageSource);
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
		} catch (Exception e) {
			log.error("Error generating Fixture Email Template: " + e.getMessage(), e);
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
	}

	/**
	 * Simple data class to hold player email task information.
	 */
	private static class PlayerEmailTask {
		final PlayerFixtureInfo playerFixtureInfo;
		final Fixture fixture;
		final boolean today;

		PlayerEmailTask(PlayerFixtureInfo playerFixtureInfo, Fixture fixture, boolean today) {
			this.playerFixtureInfo = playerFixtureInfo;
			this.fixture = fixture;
			this.today = today;
		}
	}

	/**
	 * Returns the current queue size (useful for monitoring).
	 */
	public int getQueueSize() {
		return playerEmailQueue.size();
	}
}
