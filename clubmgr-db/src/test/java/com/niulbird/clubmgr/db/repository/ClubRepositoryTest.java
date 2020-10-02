package com.niulbird.clubmgr.db.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.niulbird.clubmgr.db.model.Club;
import com.niulbird.clubmgr.db.repository.ClubRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Transactional
public class ClubRepositoryTest {
	@Autowired
	private ClubRepository repository;
	Club club;
	
	@Before
	public void setUp() {
		club = new Club();
		club.setUuid(UUID.randomUUID());
		club.setClubKey("UNIT_TEST");
		club.setName("UNIT_TEST");
		
		club = repository.save(club);
	}
	
	@After 
	public void teardown() {
		repository.delete(club);
	}
	
	@Test
	public void findSavedClubById() {
		assertEquals(club, repository.findById(club.getClubId()).get());
	}
	
	@Test
	public void findByClubByKeyTest() {
		Club testClub = repository.findByClubKey("UNIT_TEST");
		assertNotNull(testClub);
	}
}
