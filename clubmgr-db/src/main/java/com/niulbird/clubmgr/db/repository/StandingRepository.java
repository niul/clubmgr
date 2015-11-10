package com.niulbird.clubmgr.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.niulbird.clubmgr.db.model.Season;
import com.niulbird.clubmgr.db.model.Standing;
import com.niulbird.clubmgr.db.model.Team;

@Repository
public interface StandingRepository extends JpaRepository<Standing, Integer> {
	List<Standing> findByTeamAndSeason(Team team, Season season);
	List<Standing> findByTeamTeamKeyAndSeasonSeasonKey(String teamTeamKey, String seasonSeasonKey);
	List<Standing> deleteByTeamAndSeason(Team team, Season season);
}