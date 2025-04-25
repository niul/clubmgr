package com.niulbird.clubmgr.db.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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
import com.niulbird.clubmgr.db.model.Player;
import com.niulbird.clubmgr.db.model.PlayerFixtureInfo;
import com.niulbird.clubmgr.db.model.Position;
import com.niulbird.clubmgr.db.model.Season;
import com.niulbird.clubmgr.db.model.Status;
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
	@Autowired
	private PositionRepository positionRepository;
	@Autowired
	private PlayerRepository playerRepository;
	@Autowired
	private PlayerFixtureInfoRepository playerFixtureInfoRepository;
	Club club;
	Team team;
	Season season;
	Fixture fixture;
	Position position;
	Player player;
	PlayerFixtureInfo playerFixtureInfo;
	
	@Before
	public void setUp() {
		club = new Club();
		club.setClubKey("FIXTURE_UNIT_TEST");
		club.setName("FIXTURE_UNIT_TEST");
		
		team = new Team();
		team.setTeamKey("FIXTURE_UNIT_TEST");
		team.setName("FIXTURE_UNIT_TEST");
		team.setClub(club);
		Set<Team> teams = new HashSet<Team>();
		teams.add(team);
		
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
		fixture.setActive(true);

		position = new Position();
		position.setDescription("UNIT_TEST");
		position.setKey("UNIT_TEST");
		
		
		player = new Player();
		player.setAddress1("FIXTURE_UNIT_TEST");
		player.setCity("FIXTURE_UNIT_TEST");
		player.setClub(club);
		player.setFirstName("Firstname");
		player.setLastName("FixtureUnitTest");
		player.setEmail("test@test.com");
		player.setEnabled(true);
		player.setPosition(position);
		player.setTeams(teams);
		player.setManager(false);
		
		playerFixtureInfo = new PlayerFixtureInfo();
		playerFixtureInfo.setComment("FIXTURE_UNIT_TEST");
		playerFixtureInfo.setUuid(UUID.randomUUID());
		playerFixtureInfo.setStatus(Status.YES);
		playerFixtureInfo.setPlayer(player);
		playerFixtureInfo.setFixture(fixture);
		playerFixtureInfo.setStarted(false);
		playerFixtureInfo.setSubstitute(false);
		playerFixtureInfo.setYellowCard(false);
		playerFixtureInfo.setRedCard(false);
		
		club = clubRepository.save(club);
		team = teamRepository.save(team);
		season = seasonRepository.save(season);
		fixture = repository.save(fixture);
		position = positionRepository.save(position);
		player = playerRepository.save(player);
		playerFixtureInfo = playerFixtureInfoRepository.save(playerFixtureInfo);
	}
	
	@After
	public void teardown() {
		seasonRepository.delete(seasonRepository.findBySeasonKey("FIXTURE_UNIT_TEST"));
		clubRepository.delete(clubRepository.findByClubKey("FIXTURE_UNIT_TEST"));
		repository.deleteAll(repository.findByTeamTeamKeyAndSeasonSeasonKey("FIXTURE_UNIT_TEST", "FIXTURE_UNIT_TEST"));
	}
	
	@Test
	public void findOneTest() {
		Fixture testFixture = repository.findById(fixture.getFixtureId()).get();
		assertNotNull(testFixture);
		assertNotNull(testFixture.getTeam());
		assertNotNull(testFixture.getSeason());
	}
	

	
	@Test
	public void findTeamDateActiveTest() {
		List<Fixture> fixtures = repository.findByTeamAndDateAndActive(team, new java.sql.Date(new java.util.Date().getTime()), true);
		assertNotNull(fixtures);
	}
	
	@Test
	@Rollback(false)
	public void findSavedFixtureById() {
		assertEquals(fixture, repository.findById(fixture.getFixtureId()).get());
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
	
	@Test
	@Rollback(false)
	public void findFixtureByDateTest() {
		List<Fixture> fixtures = repository.findByDate(Date.valueOf("2017-04-1"));
		assertNotNull(fixtures);
	}
	
	@Test
	@Rollback(false)
	public void findFixtureByDateAndTimeTest() {
		List<Fixture> fixtures = repository.findByDateAndTimeBetween(Date.valueOf("2016-09-17"), Time.valueOf("12:01:00"),  Time.valueOf("14:00:00"));
		assertNotNull(fixtures);
	}
	
	@Test
	public void findOnePlayerFixtureInfoTest() {
		PlayerFixtureInfo playerFixtureInfo = playerFixtureInfoRepository.findByUuid(this.playerFixtureInfo.getUuid());
		assertNotNull(playerFixtureInfo);
		assertNotNull(playerFixtureInfo.getComment());
	}
}
