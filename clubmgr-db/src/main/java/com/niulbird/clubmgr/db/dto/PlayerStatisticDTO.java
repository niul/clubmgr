package com.niulbird.clubmgr.db.dto;

public class PlayerStatisticDTO {
	private String firstName;
	private String lastName;
	private int started;
	private int substitute;
	private int yellowCards;
	private int redCards;
	private int assists;
	private int goals;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getStarted() {
		return started;
	}
	public void setStarted(int started) {
		this.started = started;
	}
	public int getSubstitute() {
		return substitute;
	}
	public void setSubstitute(int substitute) {
		this.substitute = substitute;
	}
	public int getYellowCards() {
		return yellowCards;
	}
	public void setYellowCards(int yellowCards) {
		this.yellowCards = yellowCards;
	}
	public int getRedCards() {
		return redCards;
	}
	public void setRedCards(int redCards) {
		this.redCards = redCards;
	}
	public int getAssists() {
		return assists;
	}
	public void setAssists(int assists) {
		this.assists = assists;
	}
	public int getGoals() {
		return goals;
	}
	public void setGoals(int goals) {
		this.goals = goals;
	}
}
