package com.niulbird.clubmgr.db.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "seasons")
public final class Season {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "season_id")
    private Integer seasonId;
	
	private String name;
	
	@Column(name = "season_key", nullable = false)
	private String seasonKey;
	
	private Date created;

	public Season () {
		created = new Date();
	}
	
	public Integer getSeasonId() {
		return seasonId;
	}

	public void setSeasonId(Integer seasonId) {
		this.seasonId = seasonId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSeasonKey() {
		return seasonKey;
	}

	public void setSeasonKey(String seasonKey) {
		this.seasonKey = seasonKey;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
}