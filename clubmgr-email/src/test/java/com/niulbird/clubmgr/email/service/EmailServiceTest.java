package com.niulbird.clubmgr.email.service;

import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.niulbird.clubmgr.db.model.Fixture;
import com.niulbird.clubmgr.db.model.Player;
import com.niulbird.clubmgr.db.model.PlayerFixtureInfo;
import com.niulbird.clubmgr.db.model.Status;
import com.niulbird.clubmgr.email.BaseTestCase;

public class EmailServiceTest extends BaseTestCase {
	@Autowired
	private EmailService emailService;

//	@Test
//	public void sendEmailTest() {
//
//		emailService.sendFixtureEmail("5afa1b20-3e45-4dae-a313-9cceb6647c48", false);
//
//		Assert.assertTrue(true);
//	}

	@Test
	public void sendAvailabilityUpdateTest() {
		Map<String, Object> map = new HashMap<String, Object>();

		PlayerFixtureInfo playerFixtureInfo = new PlayerFixtureInfo();
		playerFixtureInfo.setUuid(UUID.randomUUID());
		playerFixtureInfo.setStatus(Status.YES);
		Player player = new Player();
		player.setFirstName("Test");
		player.setLastName("Test");
		playerFixtureInfo.setPlayer(player);
		
		Fixture fixture = new Fixture();
		fixture.setAway("Bombastic FC B");
		fixture.setHome("Bombastic FC A");
		fixture.setDate(Date.valueOf("2022-01-01"));
		fixture.setTime(Time.valueOf("12:00:00"));
		fixture.setUuid(UUID.randomUUID());
		playerFixtureInfo.setFixture(fixture);
		
		map.put("playerFixtureInfo", playerFixtureInfo);
		map.put("oldStatus", Status.NO);
		emailService.sendAvailabilityUpdateEmail(map);

		Assert.assertTrue(true);
	}
}
