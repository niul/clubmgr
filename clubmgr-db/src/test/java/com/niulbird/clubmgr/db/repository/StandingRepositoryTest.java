package com.niulbird.clubmgr.db.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
import com.niulbird.clubmgr.db.model.Standing;
import com.niulbird.clubmgr.db.model.Team;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
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
	
	@Before
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
	
	@After
	public void teardown() {
		seasonRepository.delete(seasonRepository.findBySeasonKey("STANDING_UNIT_TEST"));
		clubRepository.delete(clubRepository.findByClubKey("STANDING_UNIT_TEST"));
	}
	
	@Test
	public void findOneTest() {
		Standing testStanding = repository.findById(standing.getStandingId()).get();
		assertNotNull(testStanding);
		assertNotNull(testStanding.getTeam());
		assertNotNull(testStanding.getSeason());
	}
	
	@Test
	@Rollback(false)
	public void findSavedStandingById() {
		assertEquals(standing, repository.findById(standing.getStandingId()).get());
	}
	
	@Test
	@Rollback(false)
	public void findFixtureByTeamAndSeasonTest() {
		Team testTeam = teamRepository.findByTeamKey("STANDING_UNIT_TEST");
		Season testSeason = seasonRepository.findBySeasonKey("STANDING_UNIT_TEST");
		List<Standing> standings = repository.findByTeamAndSeason(testTeam, testSeason);
		assertNotNull(standings);
	}
	
	@Test
	@Rollback(false)
	public void findFixtureByTeamKeyNameTest() {
		List<Standing> standings = repository.findByTeamTeamKeyAndSeasonSeasonKey("STANDING_UNIT_TEST", "STANDING_UNIT_TEST");
		assertNotNull(standings);
	}
}
