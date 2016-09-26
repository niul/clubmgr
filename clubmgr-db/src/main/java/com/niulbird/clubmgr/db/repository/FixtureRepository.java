package com.niulbird.clubmgr.db.repository;

import java.sql.Date;
import java.sql.Time;
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
	List<Fixture> findByDate(Date date);
	List<Fixture> findByDateAndTimeBetween(Date date, Time start, Time end);
	List<Fixture> findByTeamAndSeasonAndHomeAndAway(Team team, Season season, String home, String away);
	Fixture findByTeamAndSeasonAndHomeAndAwayAndDate(Team team, Season season, String home, String away, Date date);
}