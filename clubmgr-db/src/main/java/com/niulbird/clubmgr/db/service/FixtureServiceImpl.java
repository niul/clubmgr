package com.niulbird.clubmgr.db.service;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.niulbird.clubmgr.db.model.Fixture;
import com.niulbird.clubmgr.db.repository.FixtureRepository;

@Service
@Transactional
public class FixtureServiceImpl implements FixtureService {
	
	@Autowired
	private FixtureRepository fixtureRepository;


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
}