package com.niulbird.clubmgr.db.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.niulbird.clubmgr.db.model.Fixture;
import com.niulbird.clubmgr.db.model.Player;
import com.niulbird.clubmgr.db.model.PlayerFixtureInfo;
import com.niulbird.clubmgr.db.model.Status;
import com.niulbird.clubmgr.db.model.Team;
import com.niulbird.clubmgr.db.model.TeamSeasonMap;
import com.niulbird.clubmgr.db.repository.FixtureRepository;
import com.niulbird.clubmgr.db.repository.PlayerFixtureInfoRepository;
import com.niulbird.clubmgr.db.repository.PlayerRepository;
import com.niulbird.clubmgr.db.repository.TeamRepository;
import com.niulbird.clubmgr.db.repository.TeamSeasonMapRepository;

@Service
@Transactional
public class FixtureServiceImpl implements FixtureService {

    private static final Logger log = LoggerFactory.getLogger(FixtureServiceImpl.class);
    
	@Autowired
	private FixtureRepository fixtureRepository;
	
	@Autowired
	private PlayerRepository playerRepository;

	@Autowired
	private PlayerFixtureInfoRepository playerFixtureInfoRepository;
	
	@Autowired
	private TeamRepository teamRepository;
	
	@Autowired
	private TeamSeasonMapRepository teamSeasonMapRepository;

	@Override
	public List<Fixture> findFixturesByDate(Date date) {
		return fixtureRepository.findByDate(date);
	}
	
	@Override
	public List<Fixture> findFixturesByTeamAndDateAndActive(Team team, Date date, Boolean active) {
		return fixtureRepository.findByTeamAndDateAndActive(team, date, active);
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
	public List<PlayerFixtureInfo> findPlayerInfoByFixture(Fixture fixture,
			String teamUuid, String seasonKey) {
		List<PlayerFixtureInfo> playerFixtureStatisticList = playerFixtureInfoRepository.findByFixtureOrderByPlayerFirstNameAscPlayerLastNameAsc(fixture);
		
		if (playerFixtureStatisticList.size() == 0) {
			TeamSeasonMap teamSeasonMap = teamSeasonMapRepository.findByTeamTeamKeyAndSeasonSeasonKey(teamRepository.findByUuid(UUID.fromString(teamUuid)).getTeamKey(), seasonKey);
			Set<Player> players = teamSeasonMap.getPlayers();
			for (Player player : players) {
				PlayerFixtureInfo playerFixtureStatistic = new PlayerFixtureInfo();
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
	public void updateFixtureReport(Fixture fixture, List<PlayerFixtureInfo> playerFixtureStatisticList) {
		Fixture dbFixture = fixtureRepository.findByUuid(fixture.getUuid());
		
		for (PlayerFixtureInfo playerFixtureStatistic : playerFixtureStatisticList) {
			Player dbPlayer = playerRepository.findByUuid(playerFixtureStatistic.getPlayer().getUuid());
					
			playerFixtureStatistic.setFixture(dbFixture);
			playerFixtureStatistic.setPlayer(dbPlayer);
			
			PlayerFixtureInfo dbPlayerFixtureStatistic = playerFixtureInfoRepository.findByFixtureAndPlayer(dbFixture, dbPlayer);
			
			if (dbPlayerFixtureStatistic == null) {
				playerFixtureInfoRepository.save(playerFixtureStatistic);
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

	@Override
	public List<PlayerFixtureInfo> findPlayerInfoByFixture(Fixture fixture) {
		List<PlayerFixtureInfo> playerFixtureInfoesList = fixture.getPlayerFixtureInfo();
		
		if (playerFixtureInfoesList.size() == 0) {
			TeamSeasonMap teamSeasonMap = teamSeasonMapRepository.findByTeamAndSeason(fixture.getTeam(), fixture.getSeason());
			Set<Player> players = teamSeasonMap.getPlayers();
			for (Player player : players) {
				PlayerFixtureInfo playerFixtureInfo = new PlayerFixtureInfo();
				playerFixtureInfo.setFixture(fixture);
				playerFixtureInfo.setPlayer(player);
				playerFixtureInfo.setFixture(fixture);
				playerFixtureInfo.setStatus(Status.PENDING);
				playerFixtureInfo.setUuid(UUID.randomUUID());
				playerFixtureInfo.setCreated(new java.sql.Time(new java.util.Date().getTime()));
				playerFixtureInfo.setStarted(false);
				playerFixtureInfo.setSubstitute(false);
				playerFixtureInfo.setYellowCard(false);
				playerFixtureInfo.setRedCard(false);
				playerFixtureInfoesList.add(playerFixtureInfo);
			}
			playerFixtureInfoRepository.saveAll(playerFixtureInfoesList);
		}
		
		return playerFixtureInfoesList;
	}

	@Override
	@Transactional
	public void updatePlayerInfo(List<PlayerFixtureInfo> playerFixtureInfoList) {
		for (PlayerFixtureInfo playerFixtureInfo : playerFixtureInfoList) {
			log.debug("Saving: [" + playerFixtureInfo.getPlayerFixtureInfoId() + "][" + playerFixtureInfo.getPlayer().getFirstName() 
					+ " " + playerFixtureInfo.getPlayer().getLastName() + "]");
			playerFixtureInfoRepository.save(playerFixtureInfo);
		}
	}

	@Override
	@Transactional
	public void updatePlayerInfo(PlayerFixtureInfo playerFixtureInfo) {
		log.debug("Saving: [" + playerFixtureInfo.getPlayerFixtureInfoId() + "][" + playerFixtureInfo.getPlayer().getFirstName() 
					+ " " + playerFixtureInfo.getPlayer().getLastName() + "]");
		playerFixtureInfoRepository.save(playerFixtureInfo);
	}
	
	@Override
	@Transactional
	public PlayerFixtureInfo create(PlayerFixtureInfo playerFixtureStatus) {
		return playerFixtureInfoRepository.save(playerFixtureStatus);
	}

	@Override
	@Transactional(rollbackFor=RecordNotFound.class)
	public PlayerFixtureInfo findByUuid(String uuid) {
		return playerFixtureInfoRepository.findByUuid(UUID.fromString(uuid));
	}

	@Override
	@Transactional(rollbackFor=RecordNotFound.class)
	public PlayerFixtureInfo delete(Integer id) throws RecordNotFound {
		PlayerFixtureInfo deletedPlayer = playerFixtureInfoRepository.findById(id).get();

		if (deletedPlayer == null)
			throw new RecordNotFound();

		playerFixtureInfoRepository.delete(deletedPlayer);
		return deletedPlayer;
	}

	@Override
	@Transactional
	public List<PlayerFixtureInfo> findAll() {
		return playerFixtureInfoRepository.findAll();
	}

	@Override
	@Transactional
	public List<PlayerFixtureInfo> findByFixture(Fixture fixture) {
		return playerFixtureInfoRepository.findByFixtureOrderByPlayerFirstNameAscPlayerLastNameAsc(fixture);
	}
}