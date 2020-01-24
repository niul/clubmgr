package com.niulbird.clubmgr.bfc.command;

import java.util.Properties;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.niulbird.clubmgr.db.model.Fixture;
import com.niulbird.clubmgr.db.model.PlayerFixtureInfo;

@Component
public class PlayerFixtureInfoListValidator implements Validator {
	private static final Logger log = LogManager.getLogger();

	@Autowired
	Properties props;
	
	public boolean supports(Class<?> clazz) {
        return PlayerFixtureInfoList.class.isAssignableFrom(clazz);
    }
	
	public void validate(Object target, Errors errors) {
		int goalsFor = 0;
		int goalsRecorded = 0;
		
		PlayerFixtureInfoList playerFixtureInfoList = (PlayerFixtureInfoList) target;
		
		Fixture fixture = playerFixtureInfoList.getFixture();
		String teamRegex = props.getProperty("team.regex");
		if (fixture.getHome().contains(teamRegex)) {
			goalsFor = Integer.parseInt(fixture.getHomeScore());
		} else {
			goalsFor = Integer.parseInt(fixture.getAwayScore());
		}
    
		for (int i = 0; i < playerFixtureInfoList.getPlayerFixtureInfoList().size(); i++) {
			PlayerFixtureInfo playerFixtureInfo = playerFixtureInfoList.getPlayerFixtureInfoList().get(i);
			if (playerFixtureInfo.getStarted() && playerFixtureInfo.getSubstitute()) {
				errors.rejectValue("playerFixtureInfoList['" + i + "']", "error.report.start_sub");
				log.debug("Error with player starting and subbing in same game.");
			}
			
			if (playerFixtureInfo.getYellowCard() && playerFixtureInfo.getRedCard()) {
				errors.rejectValue("playerFixtureInfoList['" + i + "']", "error.report.yellow_red");
				log.debug("Error with player getting both yellow and red in same game.");
			}
			if (playerFixtureInfo.getGoals() != null) {
				goalsRecorded += playerFixtureInfo.getGoals();
			}
		}
		
		if (goalsRecorded != goalsFor) {
			//errors.rejectValue("playerFixtureInfoList", "error.goals_mismatch");
			log.debug("Error with report not matching goals scored.");
		}
    }
}