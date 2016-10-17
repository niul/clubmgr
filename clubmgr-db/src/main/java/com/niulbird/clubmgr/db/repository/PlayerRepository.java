package com.niulbird.clubmgr.db.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.niulbird.clubmgr.db.model.Club;
import com.niulbird.clubmgr.db.model.Player;
import com.niulbird.clubmgr.db.model.Team;


@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {
	 Player findByUuid(UUID uuid);
	 Player findByClubAndEmail(Club club, String email);
	 List<Player> findByClubAndEnabledOrderByFirstNameAsc(Club club, boolean enabled);
	 List<Player> findByTeamsAndEnabledOrderByFirstNameAsc(Team team, boolean enabled);
}