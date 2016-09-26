package com.niulbird.clubmgr.db.model;

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
@Table(name = "teams")
public final class Team {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "team_id", nullable = false)
    private Integer teamId;

	private UUID uuid;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "club_id")
    private Club club;
	
	private String name;
	
	@Column(name = "team_key", nullable = false)
	private String teamKey;
	
	private Date created;
	
	public Team() {
		created = new Date();
	}

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}
	
	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public Club getClub() {
		return club;
	}

	public void setClub(Club club) {
		this.club = club;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTeamKey() {
		return teamKey;
	}

	public void setTeamKey(String teamKey) {
		this.teamKey = teamKey;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
}
