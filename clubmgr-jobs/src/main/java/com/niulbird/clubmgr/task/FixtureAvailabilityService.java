package com.niulbird.clubmgr.task;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.niulbird.clubmgr.db.model.Fixture;
import com.niulbird.clubmgr.db.service.FixtureService;
import com.niulbird.clubmgr.email.service.EmailService;

@Service
public class FixtureAvailabilityService {
	private static final Logger log = Logger.getLogger(FixtureAvailabilityService.class);

	private static final int SECOND = 1000;

	@Autowired
	private FixtureService fixtureService;
	
	@Autowired
	private EmailService emailService;

	@Transactional
	public void send(String teamKey, String[] daysBefore) {
		for (int i = 0; i <= daysBefore.length; i++) {
			Date date = null;
			boolean today = false;
			if (i == daysBefore.length) {
				today = true;
			}
			
			if (today) {
				date = getDaysFromCurrentDate(0);
			} else {
				date = getDaysFromCurrentDate(Integer.parseInt(daysBefore[i]));
			}
			log.debug("Finding fixtures for TEAM [" + teamKey + "] and DATE [" + date +"]");
			List<Fixture> fixtures = fixtureService.findFixturesByTeamAndDateAndActive(teamKey, date, true);

			for (Fixture fixture : fixtures) {
				emailService.sendFixtureEmail(fixture.getUuid().toString(), today);
				
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
