		package com.niulbird.clubmgr.data.util;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.niulbird.clubmgr.db.model.Fixture;
import com.niulbird.clubmgr.db.model.Standing;
import com.niulbird.clubmgr.db.model.TeamSeasonMap;

public class MWSLUtil {
    private final Log logger = LogFactory.getLog(getClass());
    
	private static final String MWSL_URI = "http://mwsl.com";
	    
	public List<Fixture> getFixtures(TeamSeasonMap teamSeasonMap, String teamRegExStr) {
		List<Fixture> fixtures = new ArrayList<Fixture>();
		try {
			Document doc = Jsoup.connect(teamSeasonMap.getFixturesUri()).get();
			Elements elements = doc.getElementsByTag("table");
			int fixturesIndex = 13;
			
			if (teamSeasonMap.getFixturesUri().contains("filesuffix=_old")) {
				fixturesIndex = 12;
			}
			Element  element = elements.get(fixturesIndex); // nth table with no ID or Class
			
			Elements rows = element.getElementsByTag("tr");
			for (int i = 0; i < rows.size(); i++) {
				Element row = rows.get(i);
				Elements columns = row.getElementsByTag("td");
				if (columns.size() > 1) {
					Fixture fixture = new Fixture();
					fixture.setDate(convertStringToDate(columns.get(2).text()));
					fixture.setTime(convertStringToTime(columns.get(3).text()));
					fixture.setHome(columns.get(3).text());
					String[] score = columns.get(4).text().split("-");
					if (score.length == 2) {
						fixture.setHomeScore(score[0].trim());
						fixture.setAwayScore(score[1].trim());
					}
					fixture.setAway(columns.get(5).text());
					fixture.setField(columns.get(6).text());
					Elements fieldLink = columns.get(6).getElementsByTag("a");
					if (fieldLink.size() > 0) {
						String mwslFieldLink = MWSL_URI + fieldLink.get(0).attr("href");
						String fieldMapUri = null;
						try {
							Document fieldDoc = Jsoup.connect(mwslFieldLink).get();
							Elements fieldElements = fieldDoc.getElementsByTag("table");
							Element  fieldElement = fieldElements.get(5); // 5th table with no ID or Class
							Elements fieldRows = fieldElement.getElementsByTag("tr");
							
							for (Element fieldRow : fieldRows) {
								Elements rowElements = fieldRow.getElementsByTag("td");
								if (rowElements.get(0).text().equalsIgnoreCase("Map")) {
									Elements mapElements = rowElements.get(2).getElementsByTag("a");
									fieldMapUri = StringUtils.chomp(mapElements.get(0).attr("href")).replaceAll("[\n\r]", "").trim();
								}
							}
						} catch (IOException ioe) {
							logger.error("Error getting field URL: " + ioe, ioe);
						}
						
						if (fieldMapUri == null) {
							fieldMapUri = mwslFieldLink;
						}
						fixture.setFieldMapUri(fieldMapUri);
					}
					fixture.setSeason(teamSeasonMap.getSeason());
					fixture.setTeam(teamSeasonMap.getTeam());
					
					if (fixture.getHome().contains(teamRegExStr) || fixture.getAway().contains(teamRegExStr)) {
						fixtures.add(fixture);
						logger.debug("Added Fixture: " + i + "\tHome: " + fixture.getHome() + "\t" + fixture.getHomeScore() + ":" + fixture.getAwayScore() + " \tAway: " + fixture.getAway() + "\tDate: " + fixture.getDate() + "\tTime: " + fixture.getTime());
					}
				}
			}
		} catch (IOException e) {
			logger.error("Error getting Fixtures: " + e.getMessage(), e);
		}

		return fixtures;
	}
	
	public List<Standing> getStandings(TeamSeasonMap teamSeasonMap, String teamRegExStr) {
		List<Standing> standings = new ArrayList<Standing>();
		try {
			Document doc = Jsoup.connect(teamSeasonMap.getFixturesUri()).get();
			Elements elements = doc.getElementsByTag("table");
			int fixturesIndex = 12;
			
			if (teamSeasonMap.getFixturesUri().contains("filesuffix=_old")) {
				fixturesIndex = 11;
			}
			Element  element = elements.get(fixturesIndex); // 12th table with no ID or Class
			
			Elements rows = element.getElementsByTag("tr");
			for (int i = 1; i < rows.size(); i++) {
				Element row = rows.get(i);
				Elements columns = row.getElementsByTag("td");
				if (columns.size() > 1) {
					Standing standing = new Standing();
					standing.setSeason(teamSeasonMap.getSeason());
					standing.setTeam(teamSeasonMap.getTeam());
					standing.setPosition(Integer.valueOf(i));
					standing.setTeamName(columns.get(0).text());
					standing.setGamesPlayed(new Integer(columns.get(1).text()));
					standing.setWins(new Integer(columns.get(2).text()));
					standing.setTies(new Integer(columns.get(3).text()));
					standing.setLosses(new Integer(columns.get(4).text()));
					standing.setGoalsFor(new Integer(columns.get(5).text()));
					standing.setGoalsAgainst(new Integer(columns.get(6).text()));		
					standing.setPoints(new Integer(columns.get(8).text()));
					standings.add(standing);
					logger.debug("Adding Standing: " + "Team: " + standing.getTeamName() + "\tPosition: " + standing.getPosition() + "\tPoints: " + standing.getPoints());
				}
			}
		} catch (IOException e) {
			logger.error("Error getting Fixtures: " + e.getMessage(), e);
		}
		return standings;
	}
	
	private Time convertStringToTime(String time) {
		Time t = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MM/d/yyyy h:mma");
		long ms = 0;
		try {
			ms = sdf.parse(time).getTime();
		} catch (ParseException e) {
			logger.error("Cannot convert time: " + e.getMessage(), e);
			return null;
		}
		t = new Time(ms);
		return t;
	}
	
	private Date convertStringToDate(String date) {
		Date d = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MM/d/yyyy h:mma");
		long ms = 0;
		try {
			ms = sdf.parse(date).getTime();
		} catch (ParseException e) {
			logger.error("Cannot convert date: " + e.getMessage(), e);
			return null;
		}
		d = new Date(ms);
		return d;
	}
}
