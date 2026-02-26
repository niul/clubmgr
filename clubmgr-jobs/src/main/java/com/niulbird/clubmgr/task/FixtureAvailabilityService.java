package com.niulbird.clubmgr.task;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.niulbird.clubmgr.db.model.Fixture;
import com.niulbird.clubmgr.db.model.TeamSeasonMap;
import com.niulbird.clubmgr.db.service.FixtureService;

@Service
public class FixtureAvailabilityService {
	private static final Logger log = LoggerFactory.getLogger(FixtureAvailabilityService.class);

	@Autowired
	private FixtureService fixtureService;
	
	@Autowired
	private ScheduledFixtureProcessor scheduledFixtureProcessor;

	@Transactional
	public void send(TeamSeasonMap teamSeasonMap, String[] daysBefore) {
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
			log.debug("Finding fixtures for TEAM [" + teamSeasonMap.getTeam().getTeamKey() + "] and DATE [" + date +"]");
			List<Fixture> fixtures = fixtureService.findFixturesByTeamAndDateAndActive(teamSeasonMap.getTeam(), date, true);

			for (Fixture fixture : fixtures) {
				scheduledFixtureProcessor.queueFixtureForEmail(fixture.getUuid().toString(), today);
			}
		}
	}

	private Date getDaysFromCurrentDate(int numberOfDays) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, numberOfDays);

		return new Date(cal.getTimeInMillis());
	}
}
