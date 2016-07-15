package com.niulbird.clubmgr.db.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.niulbird.clubmgr.db.model.Club;
import com.niulbird.clubmgr.db.model.Team;
import com.niulbird.clubmgr.db.repository.ClubRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Transactional
public class TeamRepositoryTest {
	@Autowired
	private TeamRepository repository;
	@Autowired
	private ClubRepository clubRepository;
	Club club;
	Team team;
	
	@Before
	public void setUp() {
		club = new Club();
		club.setClubKey("TEAM_UNIT_TEST");
		club.setName("TEAM_UNIT_TEST");
		clubRepository.save(club);
		
		team = new Team();
		team.setTeamKey("TEAM_UNIT_TEST");
		team.setName("TEAM_UNIT_TEST");
		team.setClub(club);
		repository.save(team);
	}
	
	@After
	public void teardown() {
		clubRepository.delete(team.getClub());
	}
	
	@Test
	public void findSavedTeamById() {
		Team testTeam = repository.save(team);
		assertEquals(testTeam, repository.findOne(team.getTeamId()));
	}
	
	@Test
	public void findTeamByKeyTest() {
		Team testTeam = repository.findByTeamKey("TEAM_UNIT_TEST");
		assertNotNull(testTeam);
	}
}
