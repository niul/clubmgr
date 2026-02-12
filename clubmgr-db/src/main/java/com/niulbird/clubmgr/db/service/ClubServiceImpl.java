package com.niulbird.clubmgr.db.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.niulbird.clubmgr.db.model.Club;
import com.niulbird.clubmgr.db.repository.ClubRepository;

@Service
public class ClubServiceImpl implements ClubService {

	@Autowired
	private ClubRepository clubRepository;
	

	@Override
	@Transactional
	public Club create(Club shop) {
		Club createdClub = shop;
		return clubRepository.save(createdClub);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Club> findById(Integer id) {
		return clubRepository.findById(id);
	}

	@Override
	@Transactional(rollbackFor=RecordNotFound.class)
	public Club delete(Integer id) throws RecordNotFound {
		Club deletedClub = clubRepository.findById(id).get();

		if (deletedClub == null)
			throw new RecordNotFound();

		clubRepository.delete(deletedClub);
		return deletedClub;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Club> findAll() {
		return clubRepository.findAll();
	}

	@Override
	@Transactional(rollbackFor=RecordNotFound.class)
	public Club update(Club club) throws RecordNotFound {
		Club updatedClub = clubRepository.findById(club.getClubId()).get();

		if (updatedClub == null)
			throw new RecordNotFound();

		updatedClub.setName(club.getName());
		updatedClub.setClubKey(club.getClubKey());
		updatedClub.setCreated(club.getCreated());
		
		return updatedClub;
	}

}