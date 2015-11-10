package com.niulbird.clubmgr.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.niulbird.clubmgr.db.model.Club;

@Repository
public interface ClubRepository extends JpaRepository<Club, Integer> {
	 Club findByClubKey(String clubKey);
}