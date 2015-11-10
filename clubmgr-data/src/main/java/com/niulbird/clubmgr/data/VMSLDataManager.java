package com.niulbird.clubmgr.data;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.niulbird.clubmgr.data.util.VMSLUtil;
import com.niulbird.clubmgr.db.model.Fixture;
import com.niulbird.clubmgr.db.model.Standing;
import com.niulbird.clubmgr.db.model.TeamSeasonMap;
import com.niulbird.clubmgr.db.service.TeamService;

public class VMSLDataManager extends DataManager {
	private VMSLUtil vmslUtil;
	
	protected VMSLDataManager(TeamService teamService, TeamSeasonMap teamSeasonMap, String teamRegExStr) {
		super(teamService, teamSeasonMap, teamRegExStr);
		vmslUtil = new VMSLUtil();
	}

	@Override
	@Cacheable(value = "vmslFixturesCache")	
	public List<Fixture> updateFixtures() {
		System.out.println("*********VMSLFixtures(): " + teamSeasonMap.getTeam());
		List<Fixture> fixtures = vmslUtil.getFixtures(teamSeasonMap, teamRegExStr);
		teamService.updateFixtures(teamSeasonMap.getTeam(), teamSeasonMap.getSeason(), fixtures);
		return fixtures;
	}

	@Override
	@Cacheable(value = "vmslStandingsCache")	
	public List<Standing> updateStandings() {
		List<Standing> standings = vmslUtil.getStandings(teamSeasonMap, teamRegExStr);
		teamService.updateStandings(teamSeasonMap.getTeam(), teamSeasonMap.getSeason(), standings);
		return standings;
	}
}
