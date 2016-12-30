package com.niulbird.clubmgr.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.niulbird.clubmgr.db.model.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {
	 Position findByKey(String key);
}