package com.niulbird.clubmgr.db.service;

import java.util.List;

import com.niulbird.clubmgr.db.model.Club;
import com.niulbird.clubmgr.db.model.Player;
import com.niulbird.clubmgr.db.model.Team;

public interface PlayerService {
	public Player create(Player player);
	public Player delete(Integer id) throws RecordNotFound;
    public List<Player> findAll();
    public Player update(Player player) throws RecordNotFound;
    public Player findById(Integer id);
	public Player findByUuid(String uuid);
    public Player findByClubAndEmail(Club club, String email);
	public List<Player> findByClub(Club club);
	public List<Player> findByTeam(Team team);
}