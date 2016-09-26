package com.niulbird.clubmgr.db.model;

import java.sql.Time;
import java.util.Date;
import java.util.UUID;

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
@Table(name = "fixtures")
public final class Fixture {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fixture_id", nullable = false)
    private Integer fixtureId;

	private UUID uuid;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "team_id")
    private Team team;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "season_id")
    private Season season;

	private String home;
	private String away;
	
	@Column(name = "home_score")
	private String homeScore;

	@Column(name = "away_score")
	
	private String awayScore;
	private String field;
	
	@Column(name = "field_map_uri")
	private String fieldMapUri;
	
	private java.sql.Date date;
	private Time time;
	private Date created;
	
	public Fixture () {
		created = new Date();
	}
	
	public Integer getFixtureId() {
		return fixtureId;
	}
	public void setFixtureId(Integer fixtureId) {
		this.fixtureId = fixtureId;
	}
	public UUID getUuid() {
		return uuid;
	}
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	public Season getSeason() {
		return season;
	}
	public void setSeason(Season season) {
		this.season = season;
	}
	public String getHome() {
		return home;
	}
	public void setHome(String home) {
		this.home = home;
	}
	public String getAway() {
		return away;
	}
	public void setAway(String away) {
		this.away = away;
	}
	public String getHomeScore() {
		return homeScore;
	}
	public void setHomeScore(String homeScore) {
		this.homeScore = homeScore;
	}
	public String getAwayScore() {
		return awayScore;
	}
	public void setAwayScore(String awayScore) {
		this.awayScore = awayScore;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getFieldMapUri() {
		return fieldMapUri;
	}

	public void setFieldMapUri(String fieldMapUri) {
		this.fieldMapUri = fieldMapUri;
	}

	public java.sql.Date getDate() {
		return date;
	}
	public void setDate(java.sql.Date date) {
		this.date = date;
	}
	public Time getTime() {
		return time;
	}
	public void setTime(Time time) {
		this.time = time;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	
	
}
