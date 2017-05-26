package com.niulbird.clubmgr.db.service;

import java.util.List;

import com.niulbird.clubmgr.db.dto.PlayerStatisticDTO;

public interface StatsService {
	public List<PlayerStatisticDTO> getTeamSeasonStats(String teamKey, String seasonKey);
}
