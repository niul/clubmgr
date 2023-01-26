package com.niulbird.clubmgr.db.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "clubs")
public final class Club {
    private Integer clubId;
	
	private UUID uuid;
	private String name;
	
	private String clubKey;
	
	private String domain;
	private Date created;
	
	private Set<Team> teams = new HashSet<>();
	
	private Set<Player> players = new HashSet<>();

	public Club () {
		created = new Date();
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "club_id")
	public Integer getClubId() {
		return clubId;
	}

	public void setClubId(Integer clubId) {
		this.clubId = clubId;
	}
	
	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "club_key", nullable = false)
	public String getClubKey() {
		return clubKey;
	}

	public void setClubKey(String clubKey) {
		this.clubKey = clubKey;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@OrderBy("teamId")
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "club", cascade = CascadeType.ALL)
	public Set<Team> getTeams() {
		return teams;
	}

	public void setTeams(Set<Team> teams) {
		this.teams = teams;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "club", cascade = CascadeType.ALL)
	public Set<Player> getPlayers() {
		return players;
	}

	public void setPlayers(Set<Player> players) {
		this.players = players;
	}
}