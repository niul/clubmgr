package com.niulbird.clubmgr.db.service;

import org.junit.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Transactional
public class StatsServiceTest {
	@Autowired
	private StatsService statsService;
	
	@Before
	public void setUp() {
	}
	
	@After 
	public void teardown() {
	}
	
	@Test
	public void findSavedClubById() {
		Object o = statsService.getTeamSeasonStats("BOMBASTIC_MENS_A", "WINTER_2016");
		Assert.assertNotNull(o);
	}
}
