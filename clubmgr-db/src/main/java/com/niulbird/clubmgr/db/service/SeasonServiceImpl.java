package com.niulbird.clubmgr.db.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.niulbird.clubmgr.db.model.Season;
import com.niulbird.clubmgr.db.repository.SeasonRepository;

@Service
@Transactional
public class SeasonServiceImpl implements SeasonService {
	@Autowired
	SeasonRepository seasonRepository;

	@Override
	@Transactional
	public List<Season> findAll() {
		return seasonRepository.findAll(Sort.by(Direction.DESC, "seasonId"));
	}

	@Override
	public Season findBySeasonKey(String seasonKey) {
		return seasonRepository.findBySeasonKey(seasonKey);
	}
}
