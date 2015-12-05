package com.niulbird.clubmgr.data.util;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.niulbird.clubmgr.db.model.Fixture;
import com.niulbird.clubmgr.db.model.Standing;
import com.niulbird.clubmgr.db.model.TeamSeasonMap;

public class RRSLUtil {
	// Logger for this class and subclasses
    private final Log logger = LogFactory.getLog(getClass());
    
    private Properties props;
    
    public RRSLUtil(Properties props) {
    	this.props = props;
    }
    
	public List<Fixture> getFixtures(TeamSeasonMap teamSeasonMap, String teamRegExStr) {
		String[] uriOverrides = props.getProperty("vmsl.url.override.values").split(",");
		logger.debug("Found " + uriOverrides.length + " URI Overrides");
		
		List<Fixture> fixtures = new ArrayList<Fixture>();
		try {
			Document doc = Jsoup.connect(teamSeasonMap.getFixturesUri()).get();
			Elements elements = doc.getElementsByClass("webs-table");
			Element  element = elements.get(1);
			
			Elements rows = element.getElementsByTag("tr");
			Date date = null;
			for (int i = 2; i < rows.size(); i++) {
				Element row = rows.get(i);
				Elements columns = row.getElementsByTag("td");
				if (columns.size() > 1) {
					Fixture fixture = new Fixture();
					
					String strDate = columns.get(0).text().replaceAll("[^ a-zA-Z0-9]", "").trim();
					if (!strDate.isEmpty()) {
						Calendar now = Calendar.getInstance();
						date = convertStringToDate(strDate + ", " + now.get(Calendar.YEAR));
					}
					fixture.setDate(date);
					fixture.setHome(columns.get(1).text());
					fixture.setAway(columns.get(2).text());
					
					String[] fieldTimeStr = columns.get(3).getElementsByTag("div").get(0).html().split("<br>");
					if (fieldTimeStr.length == 2) {
						fixture.setField(fieldTimeStr[0].replaceAll("[^ a-zA-Z0-9]", ""));
						fixture.setTime(convertStringToTime(fieldTimeStr[1].replaceAll("[^:a-zA-Z0-9]", "")));
					} else {
						fixture.setField(columns.get(3).getElementsByTag("div").get(0).ownText().replaceAll("[^ a-zA-Z0-9]", ""));
						
						Element e1 = columns.get(3).getElementsByTag("div").get(0);
						Element e2 = e1.getElementsByTag("div").get(1);
						fixture.setTime(convertStringToTime(e2.text().replaceAll("[^:a-zA-Z0-9]", "")));
					}
					
					String[] score = columns.get(4).text().replaceAll("[^-0-9]", "").split("-");
					if (score.length == 2) {
						fixture.setHomeScore(score[0].trim());
						fixture.setAwayScore(score[1].trim());
					}
					
					fixture.setFieldMapUri(props.getProperty("field.url." + fixture.getField().replaceAll(" ", "")));
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
			Elements elements = doc.getElementsByClass("webs-table");
			Element  element = elements.get(0);
			
			Elements rows = element.getElementsByTag("tr");
			for (int i = 2; i < rows.size(); i++) {
				Element row = rows.get(i);
				Elements columns = row.getElementsByTag("td");
				if (columns.size() > 1) {
					Standing standing = new Standing();
					standing.setSeason(teamSeasonMap.getSeason());
					standing.setTeam(teamSeasonMap.getTeam());
					standing.setPosition(new Integer(i-1));
					standing.setTeamName(columns.get(0).text());
					standing.setWins(new Integer(columns.get(1).text().replaceAll("[^0-9]", "")));
					standing.setTies(new Integer(columns.get(2).text().replaceAll("[^0-9]", "")));
					standing.setLosses(new Integer(columns.get(3).text().replaceAll("[^0-9]", "")));
					standing.setGoalsFor(new Integer(columns.get(4).text().replaceAll("[^0-9]", "")));
					standing.setGoalsAgainst(new Integer(columns.get(5).text().trim().replaceAll("[^0-9]", "")));
					standing.setPoints(new Integer(columns.get(6).text().replaceAll("[^0-9]", "")));
					standing.setGamesPlayed(standing.getWins() + standing.getTies() + standing.getLosses());
					
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
		SimpleDateFormat sdf = new SimpleDateFormat("h:mma");
		long ms = 0;
		try {
			ms = sdf.parse(time).getTime();
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
		t = new Time(ms);
		return t;
	}
	
	private Date convertStringToDate(String date) {
		Date d = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MMMMM d, yyyy");
		long ms = 0;
		try {
			ms = sdf.parse(date.replaceAll("(?:st|nd|rd|th)", "")).getTime();
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
		d = new Date(ms);
		return d;
	}
}
