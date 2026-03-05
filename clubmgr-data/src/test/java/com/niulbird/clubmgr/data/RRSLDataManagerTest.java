package com.niulbird.clubmgr.data;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.niulbird.clubmgr.db.model.Fixture;
import com.niulbird.clubmgr.db.model.Standing;

public class RRSLDataManagerTest extends DataTestCase {
	@Autowired
	private DataManagerFactory dataManagerFactory;

	@Test
	@Disabled
	public void createDataManagerTest() {
		DataManager dataManager = dataManagerFactory.createDataManager("BOMBASTIC_MENS_RRSL", "SUMMER_2024");
		assertTrue(dataManager instanceof RRSLDataManager);
	}

	@Test
	@Disabled
	public void rrslUpdateFixturesTest() {
		DataManager dataManager = dataManagerFactory.createDataManager("BOMBASTIC_MENS_RRSL", "SUMMER_2024");
		List<Fixture> fixtures = dataManager.updateFixtures();
		assertNotNull(fixtures);
	}

	@Test
	@Disabled
	public void rrslUpdateStandingsTest() {
		DataManager dataManager = dataManagerFactory.createDataManager("BOMBASTIC_MENS_RRSL", "SUMMER_2024");
		List<Standing> fixtures = dataManager.updateStandings();
		assertNotNull(fixtures);
	}
}
