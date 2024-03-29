package com.niulbird.clubmgr.task;

import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.niulbird.clubmgr.db.model.TeamSeasonMap;
import com.niulbird.clubmgr.db.service.TeamService;

@Service
public class FixtureAvailabilityJob {
	private static final Logger log = LogManager.getLogger(FixtureAvailabilityJob.class);

	@Autowired
	Properties props;

	@Autowired
	FixtureAvailabilityService fixtureAvailabilityService;

	@Autowired
	TeamService teamService;

	@Scheduled(cron = "0 15 14 * * *")
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
