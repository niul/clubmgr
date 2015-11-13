package com.niulbird.clubmgr.data;

import java.util.Properties;

import com.niulbird.clubmgr.db.model.TeamSeasonMap;
import com.niulbird.clubmgr.db.service.TeamService;

public abstract class DataManager implements DataManagerInterface {
	
	protected TeamService teamService;
	protected TeamSeasonMap teamSeasonMap;
	protected String teamRegExStr;
	protected Properties props;
	
	protected DataManager(TeamService teamService, TeamSeasonMap teamSeasonMap, 
			String teamRegExStr, Properties props) {
		this.teamService = teamService;
		this.teamSeasonMap = teamSeasonMap;
		this.teamRegExStr = teamRegExStr;
		this.props = props;
	}
}
