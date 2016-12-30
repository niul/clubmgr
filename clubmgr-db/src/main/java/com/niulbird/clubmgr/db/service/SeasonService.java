package com.niulbird.clubmgr.db.service;

import java.util.List;

import com.niulbird.clubmgr.db.model.Season;

public interface SeasonService {
	public List<Season> findAll();
	public Season findBySeasonKey(String seasonKey);
}
