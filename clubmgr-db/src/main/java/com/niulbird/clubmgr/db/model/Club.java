package com.niulbird.clubmgr.db.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "clubs")
public final class Club {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "club_id")
    private Integer clubId;
	
	private String name;
	
	@Column(name = "club_key", nullable = false)
	private String clubKey;
	
	private Date created;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "club", cascade = CascadeType.ALL)
	private List<Team> teams;

	public Club () {
		created = new Date();
	}
	
	public Integer getClubId() {
		return clubId;
	}

	public void setClubId(Integer clubId) {
		this.clubId = clubId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClubKey() {
		return clubKey;
	}

	public void setClubKey(String clubKey) {
		this.clubKey = clubKey;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public List<Team> getTeams() {
		return teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}
	
	
}