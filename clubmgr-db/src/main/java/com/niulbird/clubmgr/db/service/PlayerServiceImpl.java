package com.niulbird.clubmgr.db.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.niulbird.clubmgr.db.model.Club;
import com.niulbird.clubmgr.db.model.Player;
import com.niulbird.clubmgr.db.model.Team;
import com.niulbird.clubmgr.db.repository.PlayerRepository;

@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {
	
	@Autowired
	private PlayerRepository playerRepository;
	
	@Override
	@Transactional
	public Player create(Player player) {
		return playerRepository.save(player);
	}

	@Override
	@Transactional
	public Player findById(Integer id) {
		return playerRepository.findById(id).get();
	}

	@Override
	@Transactional
	public Player findByUuid(String uuid) {
		return playerRepository.findByUuid(UUID.fromString(uuid));
	}

	@Override
	@Transactional(rollbackFor=RecordNotFound.class)
	public Player delete(Integer id) throws RecordNotFound {
		Player deletedPlayer = playerRepository.findById(id).get();

		if (deletedPlayer == null)
			throw new RecordNotFound();

		playerRepository.delete(deletedPlayer);
		return deletedPlayer;
	}

	@Override
	@Transactional
	public List<Player> findAll() {
		return playerRepository.findAll();
	}

	@Override
	@Transactional(rollbackFor=RecordNotFound.class)
	public Player update(Player player) throws RecordNotFound {
		Player updatedPlayer = playerRepository.findByUuid(player.getUuid());

		if (updatedPlayer == null)
			throw new RecordNotFound();
		
		
		updatedPlayer.setFirstName(player.getFirstName());
		updatedPlayer.setLastName(player.getLastName());
		updatedPlayer.setEmail(player.getEmail());
		updatedPlayer.setPhone(player.getPhone());
		updatedPlayer.setEnabled(player.getEnabled());
		updatedPlayer.setPosition(player.getPosition());
		updatedPlayer.setTeams(player.getTeams());
		updatedPlayer.setTeamSeasonMaps(player.getTeamSeasonMaps());
		playerRepository.save(updatedPlayer);
		
		return updatedPlayer;
	}

	@Override
	public Player findByClubAndEmail(Club club, String email) {
		return playerRepository.findByClubAndEmail(club, email);
	}

	@Override
	@Transactional
	public List<Player> findByClub(Club club) {
		return playerRepository.findByClubAndEnabledOrderByFirstNameAsc(club, true);
	}

	@Override
	@Transactional
	public List<Player> findByTeam(Team team) {
		return playerRepository.findByTeamsAndEnabledOrderByFirstNameAsc(team, true);
	}
}