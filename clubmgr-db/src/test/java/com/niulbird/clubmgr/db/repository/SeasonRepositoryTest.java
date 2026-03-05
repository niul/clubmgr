package com.niulbird.clubmgr.db.repository;

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
import com.niulbird.clubmgr.db.model.Season;

@SpringBootTest(classes = TestApplication.class)
@ActiveProfiles("test")
@Transactional
public class SeasonRepositoryTest {
	@Autowired
	private SeasonRepository repository;
	Season season;

	@BeforeEach
	public void setUp() {
		season = new Season();
		season.setSeasonKey("SEASON_UNIT_TEST");
		season.setName("SEASON_UNIT_TEST");
		repository.save(season);
	}

	@AfterEach
	public void teardown() {
		repository.delete(season);
	}

	@Test
	public void findSavedSeasonById() {
		assertEquals(season.getSeasonId(), repository.findById(season.getSeasonId()).get().getSeasonId());
	}

	@Test
	public void findSeasonByKeyTest() {
		Season testSeason = repository.findBySeasonKey("SEASON_UNIT_TEST");
		assertNotNull(testSeason);
	}
}
