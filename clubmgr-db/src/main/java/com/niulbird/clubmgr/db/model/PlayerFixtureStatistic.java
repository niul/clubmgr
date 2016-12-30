package com.niulbird.clubmgr.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "player_fixture_stats")
public final class PlayerFixtureStatistic {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "player_fixture_stat_id", nullable = false)
    private Integer playerFixtureStatId;

	private Player player;
	private Fixture fixture;
	private Boolean started;
	private Boolean substitute;
	
	private Integer assists;
	private Integer goals;
	private Boolean yellowCard;
	private Boolean redCard;
	private Integer rating;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "player_fixture_stat_id")
	public Integer getPlayerFixtureStatId() {
		return playerFixtureStatId;
	}

	public void setPlayerFixtureStatId(Integer playerFixtureStatId) {
		this.playerFixtureStatId = playerFixtureStatId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "player_id")
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fixture_id")
	public Fixture getFixture() {
		return fixture;
	}

	public void setFixture(Fixture fixture) {
		this.fixture = fixture;
	}

	public boolean getStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

	public boolean getSubstitute() {
		return substitute;
	}

	public void setSubstitute(boolean substitute) {
		this.substitute = substitute;
	}

	public Integer getAssists() {
		return assists;
	}

	public void setAssists(Integer assists) {
		this.assists = assists;
	}

	public Integer getGoals() {
		return goals;
	}

	public void setGoals(Integer goals) {
		this.goals = goals;
	}

	@Column(name = "yellow_card")
	public Boolean getYellowCard() {
		return yellowCard;
	}

	public void setYellowCard(Boolean yellowCard) {
		this.yellowCard = yellowCard;
	}

	@Column(name = "red_card")
	public Boolean getRedCard() {
		return redCard;
	}

	public void setRedCard(Boolean redCard) {
		this.redCard = redCard;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}
}