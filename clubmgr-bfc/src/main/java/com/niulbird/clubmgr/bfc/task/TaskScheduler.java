package com.niulbird.clubmgr.bfc.task;

import java.util.Properties;

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
	
	@Autowired
	private Properties props;
	
	@Scheduled(cron="0 0 12 * * *")
    public void cachePosts() {
		log.debug("Getting Cached Wordpress Posts");
		wordPressDao.getAllPosts();
		log.debug("Got Cached Wordpress Posts successfully");
    }

	@Scheduled(cron="0 30 */3 * * *")
    public void cacheFixturesStandings() {
		log.debug("Updating Fixtures");
		log.debug("Props: " + props.getProperty("task.fixtures.list"));
		String[] configs = props.getProperty("task.fixtures.list").split("\\|");
		
		for(String config : configs) {
			log.debug("Config: " + config);
			String[] parts = config.split(",");
			DataManager dataManager = dataManagerFactory.createDataManager(parts[0], parts[1], parts[2]);
			dataManager.updateFixtures();
			dataManager.updateStandings();
		}
		
		log.debug("Updated Fixtures successfully");
    }
}