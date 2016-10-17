package com.niulbird.clubmgr.db.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.niulbird.clubmgr.db.model.PasswordReset;

@Repository
public interface PasswordResetRepository extends JpaRepository<PasswordReset, Integer> {
	PasswordReset findByResetKey(UUID resetKey);
}