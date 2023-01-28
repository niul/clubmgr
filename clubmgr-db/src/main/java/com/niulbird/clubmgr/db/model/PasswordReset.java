package com.niulbird.clubmgr.db.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "password_resets")
public final class PasswordReset {
    private Integer passwordResetId;
    private User user;
	private UUID resetKey;
	private Boolean complete;
	private Date created;
	private Date updated;

	public PasswordReset () {
		created = new Date();
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "password_reset_id")
	public Integer getPasswordResetId() {
		return passwordResetId;
	}

	public void setPasswordResetId(Integer passwordResetId) {
		this.passwordResetId = passwordResetId;
	}

	@ManyToOne
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "reset_key", nullable = false)
	public UUID getResetKey() {
		return resetKey;
	}
	
	public void setResetKey(UUID resetKey) {
		this.resetKey = resetKey;
	}

	public boolean getComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}
}