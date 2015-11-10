package com.niulbird.clubmgr.data;

import com.niulbird.clubmgr.db.model.TeamSeasonMap;
import com.niulbird.clubmgr.db.service.TeamService;

public abstract class DataManager implements DataManagerInterface {
	
	protected TeamService teamService;
	protected TeamSeasonMap teamSeasonMap;
	protected String teamRegExStr;
	
	protected DataManager(TeamService teamService, TeamSeasonMap teamSeasonMap, String teamRegExStr) {
		this.teamService = teamService;
		this.teamSeasonMap = teamSeasonMap;
		this.teamRegExStr = teamRegExStr;
	}
}
