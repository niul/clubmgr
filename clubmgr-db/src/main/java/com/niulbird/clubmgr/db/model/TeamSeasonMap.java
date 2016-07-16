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
@Table(name = "team_season_map")
public final class TeamSeasonMap {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "team_season_map_id")
    private Integer teamSeasonMapId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "team_id")
    private Team team;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "season_id")
    private Season season;
	
	@Column(name = "data_key")
	private String dataKey;
	
	@Column(name = "fixtures_uri")
	private String fixturesUri;
	
	@Column(name = "standings_uri")
	private String standingsUri;
	
	private Date created;
	private String description;
	private Boolean scheduled;
	

	public TeamSeasonMap () {
		created = new Date();
	}
	
	public Integer getTeamSeasonMapId() {
		return teamSeasonMapId;
	}

	public void setTeamSeasonMapId(Integer teamSeasonMapId) {
		this.teamSeasonMapId = teamSeasonMapId;
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

	public String getDataKey() {
		return dataKey;
	}

	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}

	public String getFixturesUri() {
		return fixturesUri;
	}

	public void setFixturesUri(String fixturesUri) {
		this.fixturesUri = fixturesUri;
	}

	public String getStandingsUri() {
		return standingsUri;
	}

	public void setStandingsUri(String standingsUri) {
		this.standingsUri = standingsUri;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getScheduled() {
		return scheduled;
	}

	public void setScheduled(Boolean scheduled) {
		this.scheduled = scheduled;
	}
}