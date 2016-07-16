package com.niulbird.clubmgr.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.niulbird.clubmgr.db.model.TeamSeasonMap;

@Repository
public interface TeamSeasonMapRepository extends JpaRepository<TeamSeasonMap, Integer> {
	TeamSeasonMap findByTeamTeamKeyAndSeasonSeasonKey(String teamKey, String seasonKey);
	List<TeamSeasonMap> findByScheduled(Boolean scheduled);
}