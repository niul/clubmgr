package com.niulbird.clubmgr.db.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.niulbird.clubmgr.db.TestApplication;
import com.niulbird.clubmgr.db.model.Club;
import com.niulbird.clubmgr.db.model.Season;
import com.niulbird.clubmgr.db.model.Team;
import com.niulbird.clubmgr.db.model.TeamSeasonMap;

@SpringBootTest(classes = TestApplication.class)
@ActiveProfiles("test")
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

	@BeforeEach
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

	@AfterEach
	public void teardown() {
		repository.deleteAll();
		seasonRepository.deleteAll();
		teamRepository.deleteAll();
		clubRepository.deleteAll();
	}

	@Test
	public void findOneTest() {
		TeamSeasonMap testTeamSeasonMap = repository.findById(teamSeasonMap.getTeamSeasonMapId()).get();
		assertNotNull(testTeamSeasonMap);
		assertNotNull(testTeamSeasonMap.getTeam());
	}

	@Test
	public void findSavedTeamById() {
		assertEquals(teamSeasonMap.getTeamSeasonMapId(), repository.findById(teamSeasonMap.getTeamSeasonMapId()).get().getTeamSeasonMapId());
	}

	@Test
	public void findByTeamTeamKeyAndSeasonSeasonKeyTest() {
		TeamSeasonMap testTeamSeasonMap = repository.findByTeamTeamKeyAndSeasonSeasonKey("TEAM_SEASON_MAP_UNIT_TEST", "TEAM_SEASON_MAP_UNIT_TEST");
		assertNotNull(testTeamSeasonMap);
		assertNotNull(testTeamSeasonMap.getTeam());
	}

	@Test
	public void findByScheduledTest() {
		List<TeamSeasonMap> testTeamSeasonMapList = repository.findByScheduled(true);
		assertNotNull(testTeamSeasonMapList);
		assertTrue(testTeamSeasonMapList.size() > 0);
	}
}
