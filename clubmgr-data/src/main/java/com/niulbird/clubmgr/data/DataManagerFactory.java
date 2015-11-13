package com.niulbird.clubmgr.data;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.niulbird.clubmgr.db.model.TeamSeasonMap;
import com.niulbird.clubmgr.db.service.TeamService;

@Service
public class DataManagerFactory {
	@Autowired 
	TeamService teamService;
	
	@Autowired
	Properties props;
	
	public DataManager createDataManager(String teamKey, String seasonKey, String teamRegExStr) {
		TeamSeasonMap teamSeasonMap = teamService.findTeamSeasonMap(teamKey, seasonKey);
		
		if (teamSeasonMap.getDataKey().equals("MWSL")) {
			return new MWSLDataManager(teamService, teamSeasonMap, teamRegExStr, props);
		} else {
			return new VMSLDataManager(teamService, teamSeasonMap, teamRegExStr, props);
		}
	}
}
