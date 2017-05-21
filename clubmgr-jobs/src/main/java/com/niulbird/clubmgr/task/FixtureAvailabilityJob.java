package com.niulbird.clubmgr.task;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.niulbird.clubmgr.db.model.Fixture;
import com.niulbird.clubmgr.db.service.FixtureService;
import com.niulbird.clubmgr.email.service.EmailService;

@Service
public class FixtureAvailabilityJob {
	private static final Logger log = Logger.getLogger(FixtureAvailabilityJob.class);
	
	private static final int SECOND = 1000;

	@Autowired
	Properties props;

	@Autowired
	private FixtureService fixtureService;
	
	@Autowired
	private EmailService emailService;

	@Transactional
	@Scheduled(cron = "0 15 10 * * *")
	public void sendFixturePlayerStatus() {
    	Pattern pattern = Pattern.compile(".*\\.\\s*(.*)");
		log.debug("Getting Fixtures to send Player Status Email.");
    	
		Set<String> set = props.stringPropertyNames();

        for(String name : set){
            if(name.startsWith("fixture.days.before")) {
            	Matcher m = pattern.matcher(name);

            	if (m.find()) {
            	    String teamKey = m.group(1);
                	String[] daysBefore = props.getProperty(name).split("\\s*,\\s*");
                	send(teamKey, daysBefore);
            	}
            }
        }
		log.debug("Updated Fixtures successfully");
	}
	
	private void send(String teamKey, String[] daysBefore) {
		for (int i = 0; i < daysBefore.length; i++) {
			Date date = getDaysFromCurrentDate(Integer.parseInt(daysBefore[i]));
			log.debug("Finding fixtures for TEAM [" + teamKey + "] and DATE [" + date +"]");
			List<Fixture> fixtures = fixtureService.findFixturesByTeamAndDate(teamKey, date);

			for (Fixture fixture : fixtures) {
				emailService.sendFixtureEmail(fixture.getUuid().toString());
				
				// Add a little bit of time between fixtures not to overload SMTP server.
				try {
					Thread.sleep(30 * SECOND);
				} catch (InterruptedException e) {
					log.error("Sleep interupted: " + e.getMessage());
				}
			}
		}
	}

	private Date getDaysFromCurrentDate(int numberOfDays) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, numberOfDays);

		return new Date(cal.getTimeInMillis());
	}
}
