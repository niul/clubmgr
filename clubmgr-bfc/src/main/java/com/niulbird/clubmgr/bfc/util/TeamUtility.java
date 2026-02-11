package com.niulbird.clubmgr.bfc.util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.niulbird.clubmgr.db.dto.FixtureStatisticDTO;
import com.niulbird.clubmgr.db.model.Fixture;
import com.niulbird.clubmgr.db.model.Player;
import com.niulbird.clubmgr.db.model.PlayerFixtureInfo;

public class TeamUtility {
	private static final Logger log = LoggerFactory.getLogger(TeamUtility.class);
	
	public static ArrayList<FixtureStatisticDTO> getFixtureStatistics(List<Fixture> fixtures, String teamRegex) {
		ArrayList<FixtureStatisticDTO> fixtureStatisticDTOList = new ArrayList<FixtureStatisticDTO>();
		
		for (Fixture fixture : fixtures) {
			boolean home = true;
			Map<Player, Integer> goals = new HashMap<Player, Integer>();
			Map<Player, Integer> assists = new HashMap<Player, Integer>();
			Map<Player, Boolean> yellowCards = new HashMap<Player, Boolean>();
			Map<Player, Boolean> redCards = new HashMap<Player, Boolean>();
			
			if (fixture.getAway().contains(teamRegex)) {
				home = false;
			}
			
			for (PlayerFixtureInfo playerFixtureInfo : fixture.getPlayerFixtureInfo()) {
				if (playerFixtureInfo.getGoals() != null && playerFixtureInfo.getGoals() > 0 ) {
					goals.put(playerFixtureInfo.getPlayer(), playerFixtureInfo.getGoals());
					log.debug("Added Goals[" + playerFixtureInfo.getGoals() + "][" + playerFixtureInfo.getPlayer().getFirstName() +
							" " + playerFixtureInfo.getPlayer().getLastName() + "][" + fixture.getFixtureId() + "]");
				}
				if (playerFixtureInfo.getAssists() != null && playerFixtureInfo.getAssists() > 0 ) {
					assists.put(playerFixtureInfo.getPlayer(), playerFixtureInfo.getAssists());
					log.debug("Added Assists[" + playerFixtureInfo.getAssists() + "][" + playerFixtureInfo.getPlayer().getFirstName() +
							" " + playerFixtureInfo.getPlayer().getLastName() + "][" + fixture.getFixtureId() + "]");
				}
				if (playerFixtureInfo.getYellowCard()) {
					yellowCards.put(playerFixtureInfo.getPlayer(), playerFixtureInfo.getYellowCard());
					log.debug("Added Yellow Cards[" + playerFixtureInfo.getYellowCard() + "][" + playerFixtureInfo.getPlayer().getFirstName() +
							" " + playerFixtureInfo.getPlayer().getLastName() + "][" + fixture.getFixtureId() + "]");
				}
				if (playerFixtureInfo.getRedCard()) {
					redCards.put(playerFixtureInfo.getPlayer(), playerFixtureInfo.getRedCard());
					log.debug("Added Red Cards[" + playerFixtureInfo.getRedCard() + "][" + playerFixtureInfo.getPlayer().getFirstName() +
							" " + playerFixtureInfo.getPlayer().getLastName() + "][" + fixture.getFixtureId() + "]");
				}
			}
			fixtureStatisticDTOList.add(new FixtureStatisticDTO(fixture, home, goals, assists, yellowCards, redCards));
		}
		return fixtureStatisticDTOList;
	}
}
