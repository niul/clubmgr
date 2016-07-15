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
import com.niulbird.clubmgr.db.model.Fixture;
import com.niulbird.clubmgr.db.model.Season;
import com.niulbird.clubmgr.db.model.Team;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Transactional
public class FixtureRepositoryTest {
	@Autowired
	private FixtureRepository repository;
	@Autowired
	private ClubRepository clubRepository;
	@Autowired
	private TeamRepository teamRepository;
	@Autowired
	private SeasonRepository seasonRepository;
	Club club;
	Team team;
	Season season;
	Fixture fixture;
	
	@Before
	public void setUp() {
		club = new Club();
		club.setClubKey("FIXTURE_UNIT_TEST");
		club.setName("FIXTURE_UNIT_TEST");
		
		team = new Team();
		team.setTeamKey("FIXTURE_UNIT_TEST");
		team.setName("FIXTURE_UNIT_TEST");
		team.setClub(club);
		
		season = new Season();
		season.setSeasonKey("FIXTURE_UNIT_TEST");
		season.setName("FIXTURE_UNIT_TEST");
		
		fixture = new Fixture();
		fixture.setTeam(team);
		fixture.setSeason(season);
		fixture.setAway("FIXTURE_UNIT_TEST");
		fixture.setDate(new java.sql.Date(new java.util.Date().getTime()));
		fixture.setField("FIXTURE_UNIT_TEST");
		fixture.setHome("FIXTURE_UNIT_TEST");
		fixture.setTime(new java.sql.Time(new java.util.Date().getTime()));
		
		club = clubRepository.save(club);
		team = teamRepository.save(team);
		season = seasonRepository.save(season);
		fixture = repository.save(fixture);
	}
	
	@After
	public void teardown() {
		seasonRepository.delete(seasonRepository.findBySeasonKey("FIXTURE_UNIT_TEST"));
		clubRepository.delete(clubRepository.findByClubKey("FIXTURE_UNIT_TEST"));
	}
	
	@Test
	public void findOneTest() {
		Fixture testFixture = repository.findOne(fixture.getFixtureId());
		assertNotNull(testFixture);
		assertNotNull(testFixture.getTeam());
		assertNotNull(testFixture.getSeason());
	}
	
	@Test
	@Rollback(false)
	public void findSavedFixtureById() {
		assertEquals(fixture, repository.findOne(fixture.getFixtureId()));
	}
	
	@Test
	@Rollback(false)
	public void findFixtureByTeamAndSeasonTest() {
		Team testTeam = teamRepository.findByTeamKey("FIXTURE_UNIT_TEST");
		Season testSeason = seasonRepository.findBySeasonKey("FIXTURE_UNIT_TEST");
		List<Fixture> fixtures = repository.findByTeamAndSeasonOrderByDateAsc(testTeam, testSeason);
		assertNotNull(fixtures);
	}
	
	@Test
	@Rollback(false)
	public void findFixtureByTeamKeyNameTest() {
		List<Fixture> fixtures = repository.findByTeamTeamKeyAndSeasonSeasonKey("FIXTURE_UNIT_TEST", "FIXTURE_UNIT_TEST");
		assertNotNull(fixtures);
	}
}
