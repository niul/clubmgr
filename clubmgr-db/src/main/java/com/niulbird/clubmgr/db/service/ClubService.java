package com.niulbird.clubmgr.db.service;

import java.util.List;

import com.niulbird.clubmgr.db.model.Club;

public interface ClubService {
	public Club create(Club club);
	public Club delete(Integer id) throws RecordNotFound;
    public List<Club> findAll();
    public Club update(Club club) throws RecordNotFound;
    public Club findById(Integer id);
}
