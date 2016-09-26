package com.niulbird.clubmgr.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.niulbird.clubmgr.db.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}