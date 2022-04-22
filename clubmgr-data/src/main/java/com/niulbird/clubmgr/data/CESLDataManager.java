package com.niulbird.clubmgr.data;

import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.cache.annotation.Cacheable;

import com.niulbird.clubmgr.data.util.CESLUtil;
import com.niulbird.clubmgr.db.model.Fixture;
import com.niulbird.clubmgr.db.model.Standing;
import com.niulbird.clubmgr.db.model.TeamSeasonMap;
import com.niulbird.clubmgr.db.service.TeamService;

public class CESLDataManager extends DataManager {
	private static Logger logger = LogManager.getLogger();
	private CESLUtil ceslUtil;
	
	protected CESLDataManager(TeamService teamService, TeamSeasonMap teamSeasonMap, Properties props) {
		super(teamService, teamSeasonMap, props);
		ceslUtil = new CESLUtil(props);
	}

	@Override
	@Cacheable(value = "ceslFixturesCache")	
	public List<Fixture> updateFixtures() {
		List<Fixture> fixtures = ceslUtil.getFixtures(teamSeasonMap);
		logger.debug("Got fixtures for " + teamSeasonMap.getTeam().getName() + ": " + fixtures.size());
		
		if (fixtures != null && fixtures.size() != 0) {
			teamService.updateFixtures(teamSeasonMap.getTeam(), teamSeasonMap.getSeason(), fixtures);
		}
		return fixtures;
	}

	@Override
	@Cacheable(value = "ceslStandingsCache")	
	public List<Standing> updateStandings() {
		List<Standing> standings = ceslUtil.getStandings(teamSeasonMap);
		logger.debug("Got standings for " + teamSeasonMap.getTeam().getName() + ": " + standings.size());

		if (standings != null && standings.size() != 0) {
			teamService.updateStandings(teamSeasonMap.getTeam(), teamSeasonMap.getSeason(), standings);
		}
		return standings;
	}
}
