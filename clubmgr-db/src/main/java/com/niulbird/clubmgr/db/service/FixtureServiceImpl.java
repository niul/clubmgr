package com.niulbird.clubmgr.db.service;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.niulbird.clubmgr.db.model.Fixture;
import com.niulbird.clubmgr.db.model.Player;
import com.niulbird.clubmgr.db.model.PlayerFixtureStatistic;
import com.niulbird.clubmgr.db.model.TeamSeasonMap;
import com.niulbird.clubmgr.db.repository.FixtureRepository;
import com.niulbird.clubmgr.db.repository.PlayerFixtureStatisticRepository;
import com.niulbird.clubmgr.db.repository.PlayerRepository;
import com.niulbird.clubmgr.db.repository.TeamRepository;
import com.niulbird.clubmgr.db.repository.TeamSeasonMapRepository;

@Service
@Transactional
public class FixtureServiceImpl implements FixtureService {
	
	@Autowired
	private FixtureRepository fixtureRepository;
	
	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private PlayerFixtureStatisticRepository playerFixtureStatisticRepository;
	
	@Autowired
	private TeamRepository teamRepository;
	
	@Autowired
	private TeamSeasonMapRepository teamSeasonMapRepository;


	@Override
	public List<Fixture> findFixturesByDate(Date date) {
		return fixtureRepository.findByDate(date);
	}

	@Override
	public List<Fixture> findFixturesByDateTime(Date date, int offsetStartHour, int offsetEndHour) {
		Calendar startCal = Calendar.getInstance();
		Calendar endCal = Calendar.getInstance();
		startCal.add(Calendar.HOUR, offsetStartHour);
		startCal.add(Calendar.HOUR, offsetEndHour);
		
		return fixtureRepository.findByDateAndTimeBetween(date, 
				new Time(startCal.getTime().getTime()), 
				new Time(endCal.getTime().getTime()));
	}

	@Override
	public Fixture findFixtureByUuid(String uuid) {
		return fixtureRepository.findByUuid(UUID.fromString(uuid));
	}

	@Override
	public List<PlayerFixtureStatistic> findPlayerStatisticsByFixture(Fixture fixture,
			String teamUuid, String seasonKey) {
		List<PlayerFixtureStatistic> playerFixtureStatisticList = fixture.getPlayerFixtureStatistics();
		
		if (playerFixtureStatisticList.size() == 0) {
			TeamSeasonMap teamSeasonMap = teamSeasonMapRepository.findByTeamTeamKeyAndSeasonSeasonKey(teamRepository.findByUuid(UUID.fromString(teamUuid)).getTeamKey(), seasonKey);
			List<Player> players = teamSeasonMap.getPlayers();
			for (Player player : players) {
				PlayerFixtureStatistic playerFixtureStatistic = new PlayerFixtureStatistic();
				playerFixtureStatistic.setPlayer(player);
				playerFixtureStatistic.setFixture(fixture);
				playerFixtureStatistic.setStarted(false);
				playerFixtureStatistic.setSubstitute(false);
				playerFixtureStatisticList.add(playerFixtureStatistic);
			}
		}
		
		return playerFixtureStatisticList;
	}

	@Override
	public void updateFixtureReport(Fixture fixture, List<PlayerFixtureStatistic> playerFixtureStatisticList) {
		Fixture dbFixture = fixtureRepository.findByUuid(fixture.getUuid());
		
		for (PlayerFixtureStatistic playerFixtureStatistic : playerFixtureStatisticList) {
			Player dbPlayer = playerRepository.findByUuid(playerFixtureStatistic.getPlayer().getUuid());
					
			playerFixtureStatistic.setFixture(dbFixture);
			playerFixtureStatistic.setPlayer(dbPlayer);
			
			PlayerFixtureStatistic dbPlayerFixtureStatistic = playerFixtureStatisticRepository.findByFixtureAndPlayer(dbFixture, dbPlayer);
			
			if (dbPlayerFixtureStatistic == null) {
				playerFixtureStatisticRepository.save(playerFixtureStatistic);
			} else {
				dbPlayerFixtureStatistic.setAssists(playerFixtureStatistic.getAssists());
				dbPlayerFixtureStatistic.setGoals(playerFixtureStatistic.getGoals());
				dbPlayerFixtureStatistic.setRating(playerFixtureStatistic.getRating());
				dbPlayerFixtureStatistic.setRedCard(playerFixtureStatistic.getRedCard());
				dbPlayerFixtureStatistic.setStarted(playerFixtureStatistic.getStarted());
				dbPlayerFixtureStatistic.setSubstitute(playerFixtureStatistic.getSubstitute());
				dbPlayerFixtureStatistic.setYellowCard(playerFixtureStatistic.getYellowCard());
			}
		}
		
	}
}