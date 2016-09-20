package com.niulbird.clubmgr.db.service;

import java.sql.Date;
import java.util.List;

import com.niulbird.clubmgr.db.model.Fixture;

public interface FixtureService {
    public List<Fixture> findFixturesByDate(Date date);
    public List<Fixture> findFixturesByDateTime(Date date, int offsetStartHour, int offsetEndHour);
}