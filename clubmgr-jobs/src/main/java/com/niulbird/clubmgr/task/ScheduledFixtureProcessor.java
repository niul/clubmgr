package com.niulbird.clubmgr.task;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.niulbird.clubmgr.email.service.EmailService;

/**
 * Processes fixture emails from a queue at a scheduled rate.
 * This prevents SMTP server overload by spacing out fixture email sends.
 */
@Service
public class ScheduledFixtureProcessor {
	private static final Logger log = LoggerFactory.getLogger(ScheduledFixtureProcessor.class);

	@Value("${fixture.availability.delay.ms:30000}")
	private long fixtureDelayMs;

	@Autowired
	private EmailService emailService;

	// Queue to hold pending fixture emails
	private final Queue<FixtureEmailTask> fixtureQueue = new ConcurrentLinkedQueue<>();

	/**
	 * Queue a fixture for email processing.
	 */
	public void queueFixtureForEmail(String fixtureUuid, boolean today) {
		fixtureQueue.offer(new FixtureEmailTask(fixtureUuid, today));
		log.debug("Queued fixture for email: uuid={}, today={}, queueSize={}", 
			fixtureUuid, today, fixtureQueue.size());
	}

	/**
	 * Processes one fixture from the queue at a scheduled interval.
	 * Runs every configured fixture.availability.delay.ms milliseconds.
	 */
	@Scheduled(fixedRateString = "${fixture.availability.delay.ms:30000}")
	public void processNextFixture() {
		FixtureEmailTask task = fixtureQueue.poll();
		if (task != null) {
			try {
				log.debug("Processing queued fixture: uuid={}", task.fixtureUuid);
				emailService.sendFixtureEmail(task.fixtureUuid, task.today);
			} catch (Exception e) {
				log.error("Error processing queued fixture: " + e.getMessage(), e);
			}
		}
	}

	/**
	 * Simple data class to hold fixture email task information.
	 */
	private static class FixtureEmailTask {
		final String fixtureUuid;
		final boolean today;

		FixtureEmailTask(String fixtureUuid, boolean today) {
			this.fixtureUuid = fixtureUuid;
			this.today = today;
		}
	}

	/**
	 * Returns the current queue size (useful for monitoring).
	 */
	public int getQueueSize() {
		return fixtureQueue.size();
	}
}
