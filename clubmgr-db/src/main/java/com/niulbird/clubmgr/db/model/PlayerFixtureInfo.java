package com.niulbird.clubmgr.db.model;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

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

	@ManyToOne
	@JoinColumn(name = "player_id")
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	@ManyToOne
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

	public Boolean getStarted() {
		return started;
	}

	public void setStarted(Boolean started) {
		this.started = started;
	}

	public Boolean getSubstitute() {
		return substitute;
	}

	public void setSubstitute(Boolean substitute) {
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