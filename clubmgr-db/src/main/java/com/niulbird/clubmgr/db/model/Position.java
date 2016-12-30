package com.niulbird.clubmgr.db.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "positions")
public final class Position {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "position_id")
    private Integer positionId;
	
	@Column(name = "position_key")
	private String key;
	
	@Column(name = "position_desc")
	private String description;
	
	private Date created;

	public Position () {
		created = new Date();
	}
	
	public Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	
	public String toString() {
		return key;
	}
}