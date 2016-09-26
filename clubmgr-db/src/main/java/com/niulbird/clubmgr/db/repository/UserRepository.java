package com.niulbird.clubmgr.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.niulbird.clubmgr.db.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	 User findByUsername(String username);
	 User findByUsernameAndClubDomain(String username, String domain);
}