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

import com.niulbird.clubmgr.db.model.Season;
import com.niulbird.clubmgr.db.repository.SeasonRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Transactional
public class SeasonRepositoryTest {
	@Autowired
	private SeasonRepository repository;
	Season season;
	
	@Before
	public void setUp() {
		season = new Season();
		season.setSeasonKey("SEASON_UNIT_TEST");
		season.setName("SEASON_UNIT_TEST");
		repository.save(season);
	}
	
	@After
	public void teardown() {
		repository.delete(season);
	}
	
	@Test
	public void findSavedSeasonById() {
		assertEquals(season, repository.findOne(season.getSeasonId()));
	}
	
	@Test
	public void findSeasonByKeyTest() {
		Season testSeason = repository.findBySeasonKey("SEASON_UNIT_TEST");
		assertNotNull(testSeason);
	}
}
