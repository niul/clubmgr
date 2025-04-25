package com.niulbird.clubmgr.bfc.command;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;

import com.niulbird.clubmgr.db.model.Team;
import com.niulbird.clubmgr.db.service.TeamService;

final public class StringToTeamConverter implements Converter<String, Team> {
	@Lazy
	@Autowired
	TeamService teamService;
	
	@Override
	public Team convert(String uuid) {
		return teamService.findByUuid(uuid);
	}
}