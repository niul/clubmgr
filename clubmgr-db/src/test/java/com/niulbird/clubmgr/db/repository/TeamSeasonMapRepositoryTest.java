package com.niulbird.clubmgr.db.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.niulbird.clubmgr.db.model.Club;
import com.niulbird.clubmgr.db.model.Season;
import com.niulbird.clubmgr.db.model.Team;
import com.niulbird.clubmgr.db.model.TeamSeasonMap;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Transactional
public class TeamSeasonMapRepositoryTest {
	@Autowired
	private TeamSeasonMapRepository repository;
	@Autowired
	private ClubRepository clubRepository;
	@Autowired
	private TeamRepository teamRepository;
	@Autowired
	private SeasonRepository seasonRepository;
	Club club;
	Team team;
	Season season;
	TeamSeasonMap teamSeasonMap;

	@Before
	public void setUp() {
		club = new Club();
		club.setClubKey("TEAM_SEASON_MAP_UNIT_TEST");
		club.setName("TEAM_SEASON_MAP_UNIT_TEST");

		team = new Team();
		team.setTeamKey("TEAM_SEASON_MAP_UNIT_TEST");
		team.setName("TEAM_SEASON_MAP_UNIT_TEST");
		team.setClub(club);

		season = new Season();
		season.setSeasonKey("TEAM_SEASON_MAP_UNIT_TEST");
		season.setName("TEAM_SEASON_MAP_UNIT_TEST");

		teamSeasonMap = new TeamSeasonMap();
		teamSeasonMap.setCreated(new Date());
		teamSeasonMap.setFixturesUri("TEAM_SEASON_MAP_UNIT_TEST");
		teamSeasonMap.setSeason(season);
		teamSeasonMap.setTeam(team);
		teamSeasonMap.setStandingsUri("TEAM_SEASON_MAP_UNIT_TEST");
		teamSeasonMap.setDataKey("UNIT_TEST");
		teamSeasonMap.setDescription("TEAM_SEASON_MAP_UNIT_TEST");
		teamSeasonMap.setScheduled(true);

		club = clubRepository.save(club);
		team = teamRepository.save(team);
		season = seasonRepository.save(season);
		teamSeasonMap = repository.save(teamSeasonMap);
	}

	@After
	public void teardown() {
		seasonRepository.delete(seasonRepository.findBySeasonKey("TEAM_SEASON_MAP_UNIT_TEST"));
		clubRepository.delete(clubRepository.findByClubKey("TEAM_SEASON_MAP_UNIT_TEST"));
	}

	@Test
	public void findOneTest() {
		TeamSeasonMap testTeamSeasonMap = repository.findById(teamSeasonMap.getTeamSeasonMapId()).get();
		assertNotNull(testTeamSeasonMap);
		assertNotNull(testTeamSeasonMap.getTeam());
	}

	@Test
	@Rollback(false)
	public void findSavedTeamById() {
		assertEquals(teamSeasonMap, repository.findById(teamSeasonMap.getTeamSeasonMapId()).get());
	}

	@Test
	@Rollback(false)
	public void findByTeamTeamKeyAndSeasonSeasonKeyTest() {
		TeamSeasonMap testTeamSeasonMap = repository.findByTeamTeamKeyAndSeasonSeasonKey("TEAM_SEASON_MAP_UNIT_TEST", "TEAM_SEASON_MAP_UNIT_TEST");
		assertNotNull(testTeamSeasonMap);
		assertNotNull(testTeamSeasonMap.getTeam());
	}

	@Test
	@Rollback(false)
	public void findByScheduledTest() {
		List<TeamSeasonMap> testTeamSeasonMapList = repository.findByScheduled(true);
		assertNotNull(testTeamSeasonMapList);
		assertTrue(testTeamSeasonMapList.size() > 0);
	}
}
