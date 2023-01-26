package com.niulbird.clubmgr.db.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "teams")
public final class Team {
    private Integer teamId;

	private UUID uuid;
	
    private Club club;
	
	private String name;
	
	private String teamKey;
	
	private Date created;

	private Set<Player> players = new HashSet<>();
	
	public Team() {
		created = new Date();
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "team_id", nullable = false)
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "club_id")
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

	@Column(name = "team_key", nullable = false)
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


	//@ManyToMany(fetch = FetchType.EAGER)
    //@JoinTable(name = "player_teams", joinColumns = @JoinColumn(name = "team_id"), inverseJoinColumns = @JoinColumn(name = "player_id"))
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "teams")
    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }
    
    public String toString() {
    	return uuid.toString();
    }
}
