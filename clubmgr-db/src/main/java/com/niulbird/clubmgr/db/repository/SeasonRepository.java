package com.niulbird.clubmgr.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.niulbird.clubmgr.db.model.Season;

@Repository
public interface SeasonRepository extends JpaRepository<Season, Integer> {
	 Season findBySeasonKey(String seasonKey);
}