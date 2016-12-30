package com.niulbird.clubmgr.db.service;

import java.util.List;

import com.niulbird.clubmgr.db.model.Position;

public interface PositionService {
	public List<Position> findAll();
	public Position findByKey(String positionKey);
}
