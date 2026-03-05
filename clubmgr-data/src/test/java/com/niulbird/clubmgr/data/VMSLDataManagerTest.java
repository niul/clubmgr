package com.niulbird.clubmgr.data;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.niulbird.clubmgr.db.model.Fixture;
import com.niulbird.clubmgr.db.model.Standing;

public class VMSLDataManagerTest extends DataTestCase {
	@Autowired
	private DataManagerFactory dataManagerFactory;

	@Test
	public void createDataManagerTest() {
		DataManager dataManager = dataManagerFactory.createDataManager("BOMBASTIC_MENS_A", "WINTER_2025");
		assertTrue(dataManager instanceof VMSLDataManager);
	}

	@Test
	public void vmslUpdateFixturesTest() {
		DataManager dataManager = dataManagerFactory.createDataManager("BOMBASTIC_MENS_A", "WINTER_2025");
		List<Fixture> fixtures = dataManager.updateFixtures();
		assertNotNull(fixtures);
	}

	@Test
	public void vmslUpdateStandingsTest() {
		DataManager dataManager = dataManagerFactory.createDataManager("BOMBASTIC_MENS_A", "WINTER_2025");
		List<Standing> fixtures = dataManager.updateStandings();
		assertNotNull(fixtures);
	}
}
