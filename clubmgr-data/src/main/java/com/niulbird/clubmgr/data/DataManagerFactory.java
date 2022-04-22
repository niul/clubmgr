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

	public DataManager createDataManager(TeamSeasonMap teamSeasonMap) {
		
		if (teamSeasonMap.getDataKey().equals("MWSL")) {
			return new MWSLDataManager(teamService, teamSeasonMap, props);
		} else if (teamSeasonMap.getDataKey().equals("RRSL")) {
			return new RRSLDataManager(teamService, teamSeasonMap, props);
		} else if (teamSeasonMap.getDataKey().equals("CESL")) {
			return new CESLDataManager(teamService, teamSeasonMap, props);
		} else if (teamSeasonMap.getDataKey().equals("BCMSL")) {
			return new BCMSLDataManager(teamService, teamSeasonMap, props);
		} else {
			return new VMSLDataManager(teamService, teamSeasonMap, props);
		}
	}
	
	public DataManager createDataManager(String teamKey, String seasonKey) {
		return createDataManager(teamService.findTeamSeasonMap(teamKey, seasonKey));
	}
}
