package com.niulbird.clubmgr.db.dto;

import java.util.Map;

import com.niulbird.clubmgr.db.model.Fixture;
import com.niulbird.clubmgr.db.model.Player;

public class FixtureStatisticDTO {
	private Fixture fixture;
	private boolean home;
	private Map<Player, Integer> goals;
	private Map<Player, Integer> assists;
	private Map<Player, Boolean> yellowCards;
	private Map<Player, Boolean> redCards;
	
	public FixtureStatisticDTO(Fixture fixture, boolean home, Map<Player, Integer> goals, Map<Player, Integer> assists,
			Map<Player, Boolean> yellowCards, Map<Player, Boolean> redCards) {
		this.fixture = fixture;
		this.home = home;
		this.goals = goals;
		this.assists = assists;
		this.yellowCards = yellowCards;
		this.redCards = redCards;
	}
	
	
	public Fixture getFixture() {
		return fixture;
	}
	public void setFixture(Fixture fixture) {
		this.fixture = fixture;
	}
	public boolean getHome() {
		return home;
	}
	public void setHome(boolean home) {
		this.home = home;
	}
	public Map<Player, Integer> getGoals() {
		return goals;
	}
	public void setGoals(Map<Player, Integer> goals) {
		this.goals = goals;
	}
	public Map<Player, Integer> getAssists() {
		return assists;
	}
	public void setAssists(Map<Player, Integer> assists) {
		this.assists = assists;
	}
	public Map<Player, Boolean> getYellowCards() {
		return yellowCards;
	}
	public void setYellowCards(Map<Player, Boolean> yellowCards) {
		this.yellowCards = yellowCards;
	}
	public Map<Player, Boolean> getRedCards() {
		return redCards;
	}
	public void setRedCards(Map<Player, Boolean> redCards) {
		this.redCards = redCards;
	}
	
	
}
