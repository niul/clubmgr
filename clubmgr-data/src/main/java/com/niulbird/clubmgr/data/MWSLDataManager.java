package com.niulbird.clubmgr.data;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.niulbird.clubmgr.data.util.MWSLUtil;
import com.niulbird.clubmgr.db.model.Fixture;
import com.niulbird.clubmgr.db.model.Standing;
import com.niulbird.clubmgr.db.model.TeamSeasonMap;
import com.niulbird.clubmgr.db.service.TeamService;

public class MWSLDataManager extends DataManager {
	private MWSLUtil mwslUtil;

	protected MWSLDataManager(TeamService teamService, TeamSeasonMap teamSeasonMap, String teamRegExStr) {
		super(teamService, teamSeasonMap, teamRegExStr);
		mwslUtil = new MWSLUtil();
	}

	@Override
	@Cacheable(value = "mwslFixturesCache")	
	public List<Fixture> updateFixtures() {
		List<Fixture> fixtures = mwslUtil.getFixtures(teamSeasonMap, teamRegExStr);
		teamService.updateFixtures(teamSeasonMap.getTeam(), teamSeasonMap.getSeason(), fixtures);
		return fixtures;
	}

	@Override
	@Cacheable(value = "mwslStandingsCache")	
	public List<Standing> updateStandings() {
		List<Standing> standings = mwslUtil.getStandings(teamSeasonMap, teamRegExStr);
		teamService.updateStandings(teamSeasonMap.getTeam(), teamSeasonMap.getSeason(), standings);
		return standings;
	}
}
