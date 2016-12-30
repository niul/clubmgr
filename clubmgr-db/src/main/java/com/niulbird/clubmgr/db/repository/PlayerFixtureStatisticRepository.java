package com.niulbird.clubmgr.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.niulbird.clubmgr.db.model.Fixture;
import com.niulbird.clubmgr.db.model.Player;
import com.niulbird.clubmgr.db.model.PlayerFixtureStatistic;

@Repository
public interface PlayerFixtureStatisticRepository extends JpaRepository<PlayerFixtureStatistic, Integer> {
	PlayerFixtureStatistic findByFixtureAndPlayer(Fixture fixture, Player player);
}