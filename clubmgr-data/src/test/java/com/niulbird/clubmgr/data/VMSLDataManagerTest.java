package com.niulbird.clubmgr.data;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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

import com.niulbird.clubmgr.db.model.Fixture;
import com.niulbird.clubmgr.db.model.Standing;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Transactional
public class VMSLDataManagerTest {
	@Autowired
	private DataManagerFactory dataManagerFactory;
	
	@Before
	public void setUp() {
	}
	
	@After 
	public void teardown() {
	}

	@Test
	public void createDataManagerTest() {
		DataManager dataManager = dataManagerFactory.createDataManager("BOMBASTIC_MENS_A", "WINTER_2024");
		assertTrue(dataManager instanceof VMSLDataManager);
	}

	@Test
	@Rollback(false)
	public void vmslUpdateFixturesTest() {
		DataManager dataManager = dataManagerFactory.createDataManager("BOMBASTIC_MENS_A", "WINTER_2024");
		List<Fixture> fixtures = dataManager.updateFixtures();
		dataManager = dataManagerFactory.createDataManager("BOMBASTIC_MENS_B", "WINTER_2024");
		fixtures = dataManager.updateFixtures();
		dataManager = dataManagerFactory.createDataManager("BOMBASTIC_MENS_CLASSICS", "WINTER_2024");
		fixtures = dataManager.updateFixtures();
		assertNotNull(fixtures);
	}

	@Test
	@Rollback(false)
	public void vmslUpdateStandingsTest() {
		DataManager dataManager = dataManagerFactory.createDataManager("BOMBASTIC_MENS_A", "WINTER_2024");
		List<Standing> fixtures = dataManager.updateStandings();
		dataManager = dataManagerFactory.createDataManager("BOMBASTIC_MENS_B", "WINTER_2024");
		fixtures = dataManager.updateStandings();
		dataManager = dataManagerFactory.createDataManager("BOMBASTIC_MENS_CLASSICS", "WINTER_2024");
		fixtures = dataManager.updateStandings();
		dataManager = dataManagerFactory.createDataManager("BOMBASTIC_MENS_JURASSIC", "WINTER_2024");
		fixtures = dataManager.updateStandings();
		assertNotNull(fixtures);
	}
}
