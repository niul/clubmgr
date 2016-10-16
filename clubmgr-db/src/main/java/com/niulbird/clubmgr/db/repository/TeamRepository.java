package com.niulbird.clubmgr.db.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.niulbird.clubmgr.db.model.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {
	 Team findByTeamKey(String teamKey);
	 Team findByUuid(UUID uuid);
}