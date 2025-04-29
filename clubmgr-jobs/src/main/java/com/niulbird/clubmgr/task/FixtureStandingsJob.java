package com.niulbird.clubmgr.task;

import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.niulbird.clubmgr.data.DataManager;
import com.niulbird.clubmgr.data.DataManagerFactory;
import com.niulbird.clubmgr.db.model.TeamSeasonMap;
import com.niulbird.clubmgr.db.service.TeamService;

@Component
public class FixtureStandingsJob {
	private static final Logger log = LoggerFactory.getLogger(FixtureStandingsJob.class);

	@Autowired
	private DataManagerFactory dataManagerFactory;

	@Autowired
	Properties props;
	
	@Autowired
	private TeamService teamService;

	@Scheduled(cron="0 30 */3 * * *")
    public void cacheFixturesStandings() {
		log.debug("Updating Fixtures and Standings");
		List<TeamSeasonMap> teamSeasonMapList = teamService.findScheduledTeamSeasonMap();
		
		for(TeamSeasonMap teamSeasonMap : teamSeasonMapList) {
			log.debug("Team: " + teamSeasonMap.getTeam().getName());
			log.debug("Season: " + teamSeasonMap.getSeason().getName());
			DataManager dataManager = dataManagerFactory.createDataManager(teamSeasonMap);
			dataManager.updateFixtures();
			dataManager.updateStandings();
		}
		
		log.debug("Updated Fixtures successfully");
    }
}
