package com.niulbird.clubmgr.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.niulbird.clubmgr.db.model.Fixture;
import com.niulbird.clubmgr.db.model.Season;
import com.niulbird.clubmgr.db.model.Team;

@Repository
public interface FixtureRepository extends JpaRepository<Fixture, Integer> {
	List<Fixture> findByTeamAndSeasonOrderByDateAsc(Team team, Season season);
	List<Fixture> findByTeamTeamKeyAndSeasonSeasonKey(String teamTeamKey, String seasonSeasonKey);
	List<Fixture> deleteByTeamAndSeason(Team team, Season season);
}