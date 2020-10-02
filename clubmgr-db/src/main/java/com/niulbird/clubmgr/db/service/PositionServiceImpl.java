package com.niulbird.clubmgr.db.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.niulbird.clubmgr.db.model.Position;
import com.niulbird.clubmgr.db.repository.PositionRepository;

@Service
@Transactional
public class PositionServiceImpl implements PositionService {
	@Autowired
	PositionRepository positionRepository;

	@Override
	@Transactional
	public List<Position> findAll() {
		return positionRepository.findAll(Sort.by(Direction.ASC, "positionId"));
	}

	@Override
	public Position findByKey(String key) {
		return positionRepository.findByKey(key);
	}
}
