package com.niulbird.clubmgr.db.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.niulbird.clubmgr.db.TestApplication;
import com.niulbird.clubmgr.db.model.Club;

@SpringBootTest(classes = TestApplication.class)
@ActiveProfiles("test")
@Transactional
public class ClubRepositoryTest {
	@Autowired
	private ClubRepository repository;
	Club club;

	@BeforeEach
	public void setUp() {
		club = new Club();
		club.setUuid(UUID.randomUUID());
		club.setClubKey("UNIT_TEST");
		club.setName("UNIT_TEST");

		club = repository.save(club);
	}

	@AfterEach
	public void teardown() {
		repository.delete(club);
	}

	@Test
	public void findSavedClubById() {
		assertEquals(club.getClubId(), repository.findById(club.getClubId()).get().getClubId());
	}

	@Test
	public void findByClubByKeyTest() {
		Club testClub = repository.findByClubKey("UNIT_TEST");
		assertNotNull(testClub);
	}
}
