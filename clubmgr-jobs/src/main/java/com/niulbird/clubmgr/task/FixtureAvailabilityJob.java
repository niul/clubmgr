package com.niulbird.clubmgr.task;

import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class FixtureAvailabilityJob {
	private static final Logger log = LogManager.getLogger(FixtureAvailabilityJob.class);

	@Autowired
	Properties props;

	@Autowired
	FixtureAvailabilityService fixtureAvailabilityService;
	
	@Scheduled(cron = "0 15 14 * * *")
	public void sendFixturePlayerStatus() {
    	Pattern pattern = Pattern.compile(".*\\.\\s*(.*)");
		log.debug("Getting Fixtures to send Player Status Email.");
    	
		Set<String> set = props.stringPropertyNames();

        for(String name : set){
            if(name.startsWith("fixture.days.before")) {
            	Matcher m = pattern.matcher(name);

            	if (m.find()) {
            	    String teamKey = m.group(1);
                	String[] daysBefore = props.getProperty(name).split("\\s*,\\s*");
                	fixtureAvailabilityService.send(teamKey, daysBefore);
            	}
            }
        }
		log.debug("Updated Fixtures successfully");
	}
}
