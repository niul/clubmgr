package com.niulbird.clubmgr.bfc.command;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.niulbird.clubmgr.db.model.Fixture;
import com.niulbird.clubmgr.db.model.PlayerFixtureStatistic;

@Component
public class PlayerFixtureStatisticListValidator implements Validator {
	private static final Logger log = Logger.getLogger(PlayerFixtureStatisticListValidator.class);

	@Autowired
	Properties props;
	
	public boolean supports(Class<?> clazz) {
        return PlayerFixtureStatisticList.class.isAssignableFrom(clazz);
    }
	
	public void validate(Object target, Errors errors) {
		int goalsFor = 0;
		int goalsRecorded = 0;
		
		PlayerFixtureStatisticList playerFixtureStatisticList = (PlayerFixtureStatisticList) target;
		
		Fixture fixture = playerFixtureStatisticList.getFixture();
		String teamRegex = props.getProperty("team.regex");
		if (fixture.getHome().contains(teamRegex)) {
			goalsFor = Integer.parseInt(fixture.getHomeScore());
		} else {
			goalsFor = Integer.parseInt(fixture.getAwayScore());
		}
    
		for (int i = 0; i < playerFixtureStatisticList.getPlayerFixtureStatisticList().size(); i++) {
			PlayerFixtureStatistic playerFixtureStatistic = playerFixtureStatisticList.getPlayerFixtureStatisticList().get(i);
			if (playerFixtureStatistic.getStarted() && playerFixtureStatistic.getSubstitute()) {
				errors.rejectValue("playerFixtureStatisticList['" + i + "']", "error.report.start_sub");
				log.debug("Error with player starting and subbing in same game.");
			}
			
			if (playerFixtureStatistic.getYellowCard() && playerFixtureStatistic.getRedCard()) {
				errors.rejectValue("playerFixtureStatisticList['" + i + "']", "error.report.yellow_red");
				log.debug("Error with player getting both yellow and red in same game.");
			}
			if (playerFixtureStatistic.getGoals() != null) {
				goalsRecorded += playerFixtureStatistic.getGoals();
			}
		}
		
		if (goalsRecorded != goalsFor) {
			errors.rejectValue("playerFixtureStatisticList", "error.goals_mismatch");
			log.debug("Error with report not matching goals scored.");
		}
    }
}