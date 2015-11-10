package com.niulbird.clubmgr.db.model;

import java.util.Date;

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
@Table(name = "standings")
public final class Standing {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "standing_id", nullable = false)
    private Integer standingId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "team_id")
    private Team team;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "season_id")
    private Season season;

	private Integer position;
	
	@Column(name = "team")
	private String teamName;
	
	private Integer gamesPlayed;
	private Integer	wins;
	private Integer	ties;
	private Integer	losses;
	private Integer	goalsFor;
	private Integer	goalsAgainst;
	private Integer	points;

	private Date created;

	public Standing() {
		created = new Date();
	}
	
	public Integer getStandingId() {
		return standingId;
	}

	public void setStandingId(Integer standingId) {
		this.standingId = standingId;
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

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public Integer getGamesPlayed() {
		return gamesPlayed;
	}

	public void setGamesPlayed(Integer gamesPlayed) {
		this.gamesPlayed = gamesPlayed;
	}

	public Integer getWins() {
		return wins;
	}

	public void setWins(Integer wins) {
		this.wins = wins;
	}

	public Integer getTies() {
		return ties;
	}

	public void setTies(Integer ties) {
		this.ties = ties;
	}

	public Integer getLosses() {
		return losses;
	}

	public void setLosses(Integer losses) {
		this.losses = losses;
	}

	public Integer getGoalsFor() {
		return goalsFor;
	}

	public void setGoalsFor(Integer goalsFor) {
		this.goalsFor = goalsFor;
	}

	public Integer getGoalsAgainst() {
		return goalsAgainst;
	}

	public void setGoalsAgainst(Integer goalsAgainst) {
		this.goalsAgainst = goalsAgainst;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	
}
