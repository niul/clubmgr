package com.niulbird.clubmgr.db.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.niulbird.clubmgr.db.TestApplication;
import com.niulbird.clubmgr.db.model.Club;
import com.niulbird.clubmgr.db.model.Team;

@SpringBootTest(classes = TestApplication.class)
@ActiveProfiles("test")
@Transactional
public class TeamRepositoryTest {
	@Autowired
	private TeamRepository repository;
	@Autowired
	private ClubRepository clubRepository;
	Club club;
	Team team;

	@BeforeEach
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

	@AfterEach
	public void teardown() {
		repository.deleteAll();
		clubRepository.deleteAll();
	}

	@Test
	public void findSavedTeamById() {
		Team testTeam = repository.save(team);
		assertThat(repository.findById(team.getTeamId())).isPresent();
		assertEquals(testTeam.getTeamId(), repository.findById(team.getTeamId()).get().getTeamId());
	}

	@Test
	public void findTeamByKeyTest() {
		Team testTeam = repository.findByTeamKey("TEAM_UNIT_TEST");
		assertNotNull(testTeam);
	}
}
