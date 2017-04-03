package com.niulbird.clubmgr.db.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.niulbird.clubmgr.db.model.Fixture;
import com.niulbird.clubmgr.db.model.Player;
import com.niulbird.clubmgr.db.model.PlayerFixtureInfo;

@Repository
public interface PlayerFixtureInfoRepository extends JpaRepository<PlayerFixtureInfo, Integer> {
	PlayerFixtureInfo findByFixtureAndPlayer(Fixture fixture, Player player);
	List<PlayerFixtureInfo> findByFixtureOrderByPlayerFirstNameAscPlayerLastNameAsc(Fixture fixture);
	PlayerFixtureInfo findByUuid(UUID uuid);
}