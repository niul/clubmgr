package com.niulbird.clubmgr.db.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "player_fixture_info")
public final class PlayerFixtureInfo {
    private Integer playerFixtureInfoId;

	private Player player;
	private Fixture fixture;
	private UUID uuid;
	
	@Enumerated(EnumType.STRING)
	private Status status;

	private String comment;
	private Date created;
	private Date viewed;
	private Boolean started;
	private Boolean substitute;
	
	private Integer assists;
	private Integer goals;
	private Boolean yellowCard;
	private Boolean redCard;
	private Integer rating;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "player_fixture_info_id", nullable = false)
	public Integer getPlayerFixtureInfoId() {
		return playerFixtureInfoId;
	}

	public void setPlayerFixtureInfoId(Integer playerFixtureInfoId) {
		this.playerFixtureInfoId = playerFixtureInfoId;
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
	
	public UUID getUuid() {
		return uuid;
	}
	
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getViewed() {
		return viewed;
	}

	public void setViewed(Date viewed) {
		this.viewed = viewed;
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