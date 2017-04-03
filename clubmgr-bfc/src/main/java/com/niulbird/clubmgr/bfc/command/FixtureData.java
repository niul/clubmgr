package com.niulbird.clubmgr.bfc.command;

public class FixtureData {
	private String uuid;
	private String player;
	private String comment;
	
	public FixtureData() {
	}
	
	public FixtureData(String uuid, String player, String comment) {
		this.uuid = uuid;
		this.player = player;
		this.comment = comment;
	}
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getPlayer() {
		return player;
	}
	public void setPlayer(String player) {
		this.player = player;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
