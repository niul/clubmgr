package com.niulbird.clubmgr.db.model;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "fixtures")
public final class Fixture {
    private Integer fixtureId;

	private UUID uuid;
	
    private Team team;
	
    private Season season;

	private String home;
	private String away;
	private String homeScore;
	private String awayScore;
	private String field;
	private String fieldMapUri;
	
	private java.sql.Date date;
	private Time time;
	private boolean active;
	private Date created;
	
	private List<PlayerFixtureInfo> playerFixtureInfo;
	
	public Fixture () {
		created = new Date();
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fixture_id", nullable = false)
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

	@ManyToOne
	@JoinColumn(name = "team_id")
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}

	@ManyToOne
	@JoinColumn(name = "season_id")
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
	

	@Column(name = "home_score")
	public String getHomeScore() {
		return homeScore;
	}
	
	public void setHomeScore(String homeScore) {
		this.homeScore = homeScore;
	}

	@Column(name = "away_score")
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

	@Column(name = "field_map_uri")
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
	
	public boolean getActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public Date getCreated() {
		return created;
	}
	
	public void setCreated(Date created) {
		this.created = created;
	}

	@OneToMany(mappedBy = "fixture")
	public List<PlayerFixtureInfo> getPlayerFixtureInfo() {
		return playerFixtureInfo;
	}

	public void setPlayerFixtureInfo(List<PlayerFixtureInfo> playerFixtureInfo) {
		this.playerFixtureInfo = playerFixtureInfo;
	}
}
