package com.niulbird.clubmgr.task;

import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.niulbird.clubmgr.db.model.TeamSeasonMap;
import com.niulbird.clubmgr.db.service.TeamService;

@Component
public class FixtureAvailabilityJob {
	private static final Logger log = LoggerFactory.getLogger(FixtureAvailabilityJob.class);

	@Autowired
	Properties props;

	@Autowired
	FixtureAvailabilityService fixtureAvailabilityService;

	@Autowired
	TeamService teamService;

	@Scheduled(cron = "0 15 6 * * *")
	public void sendFixturePlayerStatus() {
		log.debug("Getting Fixtures to send Player Status Email.");

		List<TeamSeasonMap> teamSeasonMapList = teamService.findScheduledEmailTeamSeasonMap();

        String[] daysBefore = props.getProperty("fixture.days.before").split("\\s*,\\s*");
        for (TeamSeasonMap teamSeasonMap : teamSeasonMapList) {
        	log.debug("Finding Fixtures for " + teamSeasonMap.getTeam().getName() + " for " + teamSeasonMap.getSeason().getName());
        	fixtureAvailabilityService.send(teamSeasonMap, daysBefore);
        }

		log.debug("Sent Player Status Email successfully");
	}
}
