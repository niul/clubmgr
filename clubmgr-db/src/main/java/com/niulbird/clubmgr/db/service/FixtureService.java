package com.niulbird.clubmgr.db.service;

import java.sql.Date;
import java.util.List;

import com.niulbird.clubmgr.db.model.Fixture;
import com.niulbird.clubmgr.db.model.PlayerFixtureInfo;

public interface FixtureService {
    public List<Fixture> findFixturesByDate(Date date);
    public List<Fixture> findFixturesByDateTime(Date date, int offsetStartHour, int offsetEndHour);
	public List<Fixture> findFixturesByTeamAndDateAndActive(String teamKey, Date date, Boolean active);
    public Fixture findFixtureByUuid(String uuid);
    public List<PlayerFixtureInfo> findPlayerInfoByFixture(Fixture fixture, String teamUuid, String seasonKey);
    public void updateFixtureReport(Fixture fixture, List<PlayerFixtureInfo> playerFixtureInfoList);
	public List<PlayerFixtureInfo> findPlayerInfoByFixture(Fixture fixture);
	void updatePlayerInfo(PlayerFixtureInfo playerFixtureInfo);
	void updatePlayerInfo(List<PlayerFixtureInfo> playerFixtureInfoList);
	public PlayerFixtureInfo create(PlayerFixtureInfo playerFixtureInfo);
	public PlayerFixtureInfo delete(Integer id) throws RecordNotFound;
    public List<PlayerFixtureInfo> findAll();
    public List<PlayerFixtureInfo> findByFixture(Fixture fixture);
	public PlayerFixtureInfo findByUuid(String uuid);
}