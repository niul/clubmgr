package com.niulbird.clubmgr.db.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
import com.niulbird.clubmgr.db.model.Standing;
import com.niulbird.clubmgr.db.model.Team;

@SpringBootTest(classes = TestApplication.class)
@ActiveProfiles("test")
@Transactional
public class StandingRepositoryTest {
	@Autowired
	private StandingRepository repository;
	@Autowired
	private ClubRepository clubRepository;
	@Autowired
	private TeamRepository teamRepository;
	@Autowired
	private SeasonRepository seasonRepository;
	Club club;
	Team team;
	Season season;
	Standing standing;
	
	@BeforeEach
	public void setUp() {
		club = new Club();
		club.setClubKey("STANDING_UNIT_TEST");
		club.setName("STANDING_UNIT_TEST");
		
		team = new Team();
		team.setTeamKey("STANDING_UNIT_TEST");
		team.setName("STANDING_UNIT_TEST");
		team.setClub(club);
		
		season = new Season();
		season.setSeasonKey("STANDING_UNIT_TEST");
		season.setName("STANDING_UNIT_TEST");
		
		standing = new Standing();
		standing.setTeam(team);
		standing.setSeason(season);
		standing.setTeamName("STANDING_UNIT_TEST");
		standing.setPosition(1);
		standing.setGamesPlayed(1);
		standing.setWins(1);
		standing.setTies(1);
		standing.setLosses(1);
		standing.setGoalsFor(1);
		standing.setGoalsAgainst(1);
		standing.setPoints(1);
		
		club = clubRepository.save(club);
		team = teamRepository.save(team);
		season = seasonRepository.save(season);
		standing = repository.save(standing);
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
		Standing testStanding = repository.findById(standing.getStandingId()).get();
		assertNotNull(testStanding);
		assertNotNull(testStanding.getTeam());
		assertNotNull(testStanding.getSeason());
	}
	
	@Test
	public void findSavedStandingById() {
		assertEquals(standing.getStandingId(), repository.findById(standing.getStandingId()).get().getStandingId());
	}
	
	@Test
	public void findFixtureByTeamAndSeasonTest() {
		Team testTeam = teamRepository.findByTeamKey("STANDING_UNIT_TEST");
		Season testSeason = seasonRepository.findBySeasonKey("STANDING_UNIT_TEST");
		List<Standing> standings = repository.findByTeamAndSeason(testTeam, testSeason);
		assertNotNull(standings);
	}
	
	@Test
	public void findFixtureByTeamKeyNameTest() {
		List<Standing> standings = repository.findByTeamTeamKeyAndSeasonSeasonKey("STANDING_UNIT_TEST", "STANDING_UNIT_TEST");
		assertNotNull(standings);
	}
}
