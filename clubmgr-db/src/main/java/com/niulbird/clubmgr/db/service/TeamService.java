package com.niulbird.clubmgr.db.service;

import java.util.List;

import com.niulbird.clubmgr.db.model.Fixture;
import com.niulbird.clubmgr.db.model.Season;
import com.niulbird.clubmgr.db.model.Standing;
import com.niulbird.clubmgr.db.model.Team;
import com.niulbird.clubmgr.db.model.TeamSeasonMap;

public interface TeamService {
	public Team create(Team team);
	public Team delete(Integer id) throws RecordNotFound;
    public List<Team> findAll();
    public Team update(Team team) throws RecordNotFound;
    public Team findById(Integer id);
	public Team findByUuid(String uuid);
	public List<Team> findByUuid(String[] uuid);
    public TeamSeasonMap findTeamSeasonMap(String teamKey, String seasonKey);
    public TeamSeasonMap findTeamSeasonMap(Team team, Season season);
    public List<TeamSeasonMap> findScheduledTeamSeasonMap();
    public List<TeamSeasonMap> findScheduledEmailTeamSeasonMap();
    public List<Fixture> findFixtures(Team team, Season season);
    public List<Standing> findStandings(Team team, Season season);
    public List<Fixture> createFixtures(List<Fixture> fixtures);
    public List<Standing> createStandings(List<Standing> standings);
    public List<Fixture> updateFixtures(Team team, Season season, List<Fixture> fixtures);
    public List<Standing> updateStandings(Team team, Season season, List<Standing> standings);
}