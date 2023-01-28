package com.niulbird.clubmgr.db.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "team_season_map")
public final class TeamSeasonMap {
    private Integer teamSeasonMapId;
	
    private Team team;
	
    private Season season;
	
	private String dataKey;
	
	private String fixturesUri;
	
	private String standingsUri;
	
	private Date created;
	private String description;
	private Boolean scheduled;
	
	private String nameRegex;
	
	private Boolean email;

   	private Set<Player> players = new HashSet<>();

	public TeamSeasonMap () {
		created = new Date();
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "team_season_map_id")
	public Integer getTeamSeasonMapId() {
		return teamSeasonMapId;
	}

	public void setTeamSeasonMapId(Integer teamSeasonMapId) {
		this.teamSeasonMapId = teamSeasonMapId;
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

	@Column(name = "data_key")
	public String getDataKey() {
		return dataKey;
	}

	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}

	@Column(name = "fixtures_uri")
	public String getFixturesUri() {
		return fixturesUri;
	}

	public void setFixturesUri(String fixturesUri) {
		this.fixturesUri = fixturesUri;
	}

	@Column(name = "standings_uri")
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

	@Column(name = "name_regex")
	public String getNameRegex() {
		return nameRegex;
	}

	public void setNameRegex(String nameRegex) {
		this.nameRegex = nameRegex;
	}

	public Boolean getEmail() {
		return email;
	}

	public void setEmail(Boolean email) {
		this.email = email;
	}

	
	@ManyToMany(mappedBy = "teamSeasonMaps", cascade = CascadeType.PERSIST)
    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }
}