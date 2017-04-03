package com.niulbird.clubmgr.bfc.command;

import java.util.List;

import com.niulbird.clubmgr.db.model.Fixture;
import com.niulbird.clubmgr.db.model.PlayerFixtureInfo;

public class PlayerFixtureInfoList {
	private List<PlayerFixtureInfo> playerFixtureInfoList;
	private Fixture fixture;
	
	public List<PlayerFixtureInfo> getPlayerFixtureInfoList() {
		return playerFixtureInfoList;
	}
	
	public void setPlayerFixtureInfoList(List<PlayerFixtureInfo> playerFixtureInfoList) {
		this.playerFixtureInfoList = playerFixtureInfoList;
	}

	public Fixture getFixture() {
		return fixture;
	}

	public void setFixture(Fixture fixture) {
		this.fixture = fixture;
	}
}
