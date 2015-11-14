package com.niulbird.clubmgr.bfc;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.niulbird.clubmgr.bfc.wordpress.WordPressDao;
import com.niulbird.clubmgr.data.DataManager;
import com.niulbird.clubmgr.data.DataManagerFactory;

@Service
public class TaskScheduler {
	private static final Logger log = Logger.getLogger(TaskScheduler.class);

	@Autowired
	private DataManagerFactory dataManagerFactory;
	
	@Autowired
	private WordPressDao wordPressDao;
	
	@Scheduled(cron="0 0 12 * * *")
    public void cachePosts() {
		log.debug("Getting Cached Wordpress Posts");
		wordPressDao.getAllPosts();
		log.debug("Got Cached Wordpress Posts successfully");
    }
	
	@Scheduled(cron="0 30 */3 * * *")
    public void cacheFixturesStandings() {
		log.debug("Updating Fixtures");
		DataManager dataManager = dataManagerFactory.createDataManager("BOMBASTIC_MENS_A", "WINTER_2015", "Bombastic");
		dataManager.updateFixtures();
		dataManager.updateStandings();
		dataManager = dataManagerFactory.createDataManager("BOMBASTIC_MENS_B", "WINTER_2015", "Bombastic");
		dataManager.updateFixtures();
		dataManager.updateStandings();
		dataManager = dataManagerFactory.createDataManager("BOMBASTIC_MENS_CLASSICS", "WINTER_2015", "Bombastic");
		dataManager.updateFixtures();
		dataManager.updateStandings();
		dataManager = dataManagerFactory.createDataManager("BOMBASTIC_MENS_JURASSIC", "WINTER_2015", "Bombastic");
		dataManager.updateFixtures();
		dataManager.updateStandings();
		dataManager = dataManagerFactory.createDataManager("BOMBASTIC_WOMENS", "WINTER_2015", "Bombastic");
		dataManager.updateFixtures();
		dataManager.updateStandings();
		log.debug("Updated Fixtures successfully");
    }
}