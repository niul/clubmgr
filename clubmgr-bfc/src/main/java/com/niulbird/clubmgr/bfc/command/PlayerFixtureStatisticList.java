package com.niulbird.clubmgr.bfc.command;

import java.util.List;

import com.niulbird.clubmgr.db.model.Fixture;
import com.niulbird.clubmgr.db.model.PlayerFixtureStatistic;

public class PlayerFixtureStatisticList {
	private List<PlayerFixtureStatistic> playerFixtureStatisticList;
	private Fixture fixture;
	
	public List<PlayerFixtureStatistic> getPlayerFixtureStatisticList() {
		return playerFixtureStatisticList;
	}
	
	public void setPlayerFixtureStatisticList(List<PlayerFixtureStatistic> playerFixtureStatisticList) {
		this.playerFixtureStatisticList = playerFixtureStatisticList;
	}

	public Fixture getFixture() {
		return fixture;
	}

	public void setFixture(Fixture fixture) {
		this.fixture = fixture;
	}
}
