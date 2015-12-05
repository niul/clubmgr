package com.niulbird.clubmgr.data;

import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;

import com.niulbird.clubmgr.data.util.RRSLUtil;
import com.niulbird.clubmgr.db.model.Fixture;
import com.niulbird.clubmgr.db.model.Standing;
import com.niulbird.clubmgr.db.model.TeamSeasonMap;
import com.niulbird.clubmgr.db.service.TeamService;

public class RRSLDataManager extends DataManager {
	private static Logger logger = Logger.getLogger(RRSLDataManager.class);
	private RRSLUtil rrslUtil;
	
	protected RRSLDataManager(TeamService teamService, TeamSeasonMap teamSeasonMap, 
			String teamRegExStr, Properties props) {
		super(teamService, teamSeasonMap, teamRegExStr, props);
		rrslUtil = new RRSLUtil(props);
	}

	@Override
	@Cacheable(value = "rrslFixturesCache")	
	public List<Fixture> updateFixtures() {
		List<Fixture> fixtures = rrslUtil.getFixtures(teamSeasonMap, teamRegExStr);
		logger.debug("Got fixtures for " + teamSeasonMap.getTeam().getName() + ": " + fixtures.size());
		
		if (fixtures != null && fixtures.size() != 0) {
			teamService.updateFixtures(teamSeasonMap.getTeam(), teamSeasonMap.getSeason(), fixtures);
		}
		return fixtures;
	}

	@Override
	@Cacheable(value = "rrslStandingsCache")	
	public List<Standing> updateStandings() {
		List<Standing> standings = rrslUtil.getStandings(teamSeasonMap, teamRegExStr);
		logger.debug("Got standings for " + teamSeasonMap.getTeam().getName() + ": " + standings.size());

		if (standings != null && standings.size() != 0) {
			teamService.updateStandings(teamSeasonMap.getTeam(), teamSeasonMap.getSeason(), standings);
		}
		return standings;
	}
}
