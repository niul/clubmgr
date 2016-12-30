package com.niulbird.clubmgr.db.service;

import java.sql.Date;
import java.util.List;

import com.niulbird.clubmgr.db.model.Fixture;
import com.niulbird.clubmgr.db.model.PlayerFixtureStatistic;

public interface FixtureService {
    public List<Fixture> findFixturesByDate(Date date);
    public List<Fixture> findFixturesByDateTime(Date date, int offsetStartHour, int offsetEndHour);
    public Fixture findFixtureByUuid(String uuid);
    public List<PlayerFixtureStatistic> findPlayerStatisticsByFixture(Fixture fixture, String teamUuid, String seasonKey);
    public void updateFixtureReport(Fixture fixture, List<PlayerFixtureStatistic> playerFixtureStatisticList);
}