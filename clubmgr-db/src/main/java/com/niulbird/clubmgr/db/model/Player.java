package com.niulbird.clubmgr.db.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;


@Entity
@Table(name = "players")
public final class Player {
    private Integer playerId;
	private UUID uuid;
    private Club club;

	@NotNull @Size(min=2, max=40) @Pattern(regexp="[A-Z][a-z]+( [A-Z][a-z]+)?")
	private String firstName;
	
	@NotNull @Size(min=2, max=40) @Pattern(regexp="[A-Z]['A-Za-z]+( [A-Z][a-z]+)?")
	private String lastName;
	
	@NotNull @Email
	private String email;
	
	@Pattern(regexp="(^$|[0-9]{10})")
	private String phone;
	private Date dob;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String zip;
	private String country;
	private Boolean manager;
	private Boolean enabled;
	private Date created;

	private List<Team> teams;
	private List<TeamSeasonMap> teamSeasonMaps;
	private Position position;

	public Player () {
		created = new Date();
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "player_id")
	public Integer getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
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

	@Column(name = "first_name", nullable = false)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "last_name", nullable = false)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getAddress1() {
		return address1;
	}
	
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public boolean getManager() {
		return manager;
	}

	public void setManager(boolean manager) {
		this.manager = manager;
	}

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "player_teams", joinColumns = @JoinColumn(name = "player_id"), inverseJoinColumns = @JoinColumn(name = "team_id"))
    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "player_team_season_map", joinColumns = @JoinColumn(name = "player_id"), inverseJoinColumns = @JoinColumn(name = "team_season_map_id"))
    public List<TeamSeasonMap> getTeamSeasonMaps() {
        return teamSeasonMaps;
    }

    public void setTeamSeasonMaps(List<TeamSeasonMap> teamSeasonMaps) {
        this.teamSeasonMaps = teamSeasonMaps;
    }

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "position_id")
	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
}