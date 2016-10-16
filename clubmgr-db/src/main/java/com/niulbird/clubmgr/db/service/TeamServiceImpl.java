package com.niulbird.clubmgr.db.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.niulbird.clubmgr.db.model.Fixture;
import com.niulbird.clubmgr.db.model.Season;
import com.niulbird.clubmgr.db.model.Standing;
import com.niulbird.clubmgr.db.model.Team;
import com.niulbird.clubmgr.db.model.TeamSeasonMap;
import com.niulbird.clubmgr.db.repository.FixtureRepository;
import com.niulbird.clubmgr.db.repository.StandingRepository;
import com.niulbird.clubmgr.db.repository.TeamRepository;
import com.niulbird.clubmgr.db.repository.TeamSeasonMapRepository;

@Service
@Transactional
public class TeamServiceImpl implements TeamService {
	
	@Autowired
	private TeamRepository teamRepository;
	
	@Autowired
	private TeamSeasonMapRepository teamSeasonMapRepository;
	
	@Autowired
	private FixtureRepository fixtureRepository;
	
	@Autowired
	private StandingRepository standingRepository;
	
	@Override
	@Transactional
	public Team create(Team team) {
		return teamRepository.save(team);
	}

	@Override
	@Transactional
	public Team findById(Integer id) {
		return teamRepository.findOne(id);
	}

	@Override
	@Transactional
	public Team findByUuid(String uuid) {
		return teamRepository.findByUuid(UUID.fromString(uuid));
	}

	@Override
	@Transactional(rollbackFor=RecordNotFound.class)
	public Team delete(Integer id) throws RecordNotFound {
		Team deletedTeam = teamRepository.findOne(id);

		if (deletedTeam == null)
			throw new RecordNotFound();

		teamRepository.delete(deletedTeam);
		return deletedTeam;
	}

	@Override
	@Transactional
	public List<Team> findAll() {
		return teamRepository.findAll();
	}

	@Override
	@Transactional(rollbackFor=RecordNotFound.class)
	public Team update(Team team) throws RecordNotFound {
		Team updatedTeam = teamRepository.findOne(team.getTeamId());

		if (updatedTeam == null)
			throw new RecordNotFound();

		updatedTeam.setName(team.getName());
		updatedTeam.setClub(team.getClub());
		updatedTeam.setTeamKey(team.getTeamKey());
		updatedTeam.setCreated(team.getCreated());
		
		return updatedTeam;
	}

	@Override
	public TeamSeasonMap findTeamSeasonMap(String teamKey, String seasonKey) {
		return teamSeasonMapRepository.findByTeamTeamKeyAndSeasonSeasonKey(teamKey, seasonKey);
	}
	
	@Override
	public List<TeamSeasonMap> findScheduledTeamSeason() {
		return teamSeasonMapRepository.findByScheduled(true);
	}

	@Override
	public List<Fixture> findFixtures(Team team, Season season) {
		return fixtureRepository.findByTeamAndSeasonOrderByDateAsc(team, season);
	}

	@Override
	public List<Standing> findStandings(Team team, Season season) {
		return standingRepository.findByTeamAndSeason(team, season);
	}

	@Override
	public List<Fixture> createFixtures(List<Fixture> fixtures) {
		return fixtureRepository.save(fixtures);	
	}

	@Override
	public List<Standing> createStandings(List<Standing> standings) {
		return standingRepository.save(standings);	
	}


	@Override
	public List<Fixture> updateFixtures(Team team, Season season, List<Fixture> fixtures) {
		List<Fixture> allDbFixtures = new ArrayList<Fixture>();
		for (Fixture fixture : fixtures) {
			List<Fixture> dbFixtures = fixtureRepository.findByTeamAndSeasonAndHomeAndAway(fixture.getTeam(), fixture.getSeason(), fixture.getHome(), fixture.getAway());
			Fixture dbFixture;
			if (dbFixtures == null) {
				fixtureRepository.save(fixture);
				allDbFixtures.add(fixture);
			} else {
				if (dbFixtures.size() > 1) {
					dbFixture = fixtureRepository.findByTeamAndSeasonAndHomeAndAwayAndDate(fixture.getTeam(), fixture.getSeason(), fixture.getHome(), fixture.getAway(), fixture.getDate());
				} else {
					dbFixture = dbFixtures.get(0);
				}
				dbFixture.setAwayScore(fixture.getAwayScore());
				dbFixture.setDate(fixture.getDate());
				dbFixture.setField(fixture.getField());
				dbFixture.setFieldMapUri(fixture.getFieldMapUri());
				dbFixture.setHomeScore(fixture.getHomeScore());
				dbFixture.setActive(fixture.getActive());
				fixtureRepository.save(dbFixture);
				allDbFixtures.add(dbFixture);
			}
		}
		return allDbFixtures;
	}

	@Override
	public List<Standing> updateStandings(Team team, Season season, List<Standing> standings) {
		standingRepository.deleteByTeamAndSeason(team, season);
		return standingRepository.save(standings);	
	}
}