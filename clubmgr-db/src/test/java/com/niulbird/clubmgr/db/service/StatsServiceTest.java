package com.niulbird.clubmgr.db.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class StatsServiceTest {
	@Autowired
	private StatsService statsService;
	
	@BeforeEach
	public void setUp() {
	}
	
	@AfterEach 
	public void teardown() {
	}
	
	@Test
	public void findSavedClubById() {
		Object o = statsService.getTeamSeasonStats("BOMBASTIC_MENS_A", "WINTER_2026");
		assertNotNull(o);
	}
}
