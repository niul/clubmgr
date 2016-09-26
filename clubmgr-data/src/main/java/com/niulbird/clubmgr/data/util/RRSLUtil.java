package com.niulbird.clubmgr.data.util;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.niulbird.clubmgr.db.model.Fixture;
import com.niulbird.clubmgr.db.model.Standing;
import com.niulbird.clubmgr.db.model.TeamSeasonMap;

public class RRSLUtil extends BaseUtil {
	// Logger for this class and subclasses
    private final Log logger = LogFactory.getLog(getClass());
    
    private final static String TIME_FORMAT = "h:mma";
    private final static String DATE_FORMAT = "MMMMM d, yyyy";
    
    public RRSLUtil(Properties props) {
    	this.props = props;
    }
    
	public List<Fixture> getFixtures(TeamSeasonMap teamSeasonMap, String teamRegExStr) {
		
		List<Fixture> fixtures = new ArrayList<Fixture>();
		try {
			Document doc = Jsoup.connect(teamSeasonMap.getFixturesUri()).timeout(Integer.parseInt(props.getProperty("jsoup.timeout"))).get();
			Elements elements = doc.getElementsByClass("webs-table");
			Element  element = elements.get(1);
			
			Elements rows = element.getElementsByTag("tr");
			Date date = null;
			for (int i = 2; i < rows.size(); i++) {
				Element row = rows.get(i);
				Elements columns = row.getElementsByTag("td");
				if (columns.size() > 1) {
					Fixture fixture = new Fixture();
					fixture.setUuid(UUID.randomUUID());
					
					String strDate = columns.get(0).text().replaceAll("[^ a-zA-Z0-9]", "").trim();
					if (!strDate.isEmpty()) {
						Calendar now = Calendar.getInstance();
						date = convertStringToDate(strDate.replaceAll("(?:st|nd|rd|th)", "") + ", " + now.get(Calendar.YEAR), DATE_FORMAT);
					}
					fixture.setDate(date);
					fixture.setHome(columns.get(1).text());
					fixture.setAway(columns.get(2).text());
					
					String[] fieldTimeStr = columns.get(3).getElementsByTag("div").get(0).html().replaceAll("\\<.*?>", " ").replaceAll("  ", " ").split(" ");
					String field = new String();
					String time = new String();
					String hour = new String();
					
					for (String s : fieldTimeStr) {
						if (!StringUtil.isBlank(s) && !s.equalsIgnoreCase(" ") && !s.equalsIgnoreCase("\n")) {
							if (Character.isDigit(s.charAt(0))) {
								if (s.length() == 1) { 
									hour = s;
								} else {
									time = s.substring(0, 6);
								}
							} else if (s.charAt(0) == ':') {
								time = hour.concat(s.substring(0, 5));
							} else {
								field = field.concat(s.replace("&nbsp;", "") + " ");
							}
						}
					}
					fixture.setField(field);
					fixture.setTime(convertStringToTime(time, TIME_FORMAT));
					
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
			Document doc = Jsoup.connect(teamSeasonMap.getFixturesUri()).timeout(Integer.parseInt(props.getProperty("jsoup.timeout"))).get();
			Elements elements = doc.getElementsByClass("webs-table");
			Element  element = elements.get(0);
			
			Elements rows = element.getElementsByTag("tr");
			for (int i = 2; i < rows.size(); i++) {
				Element row = rows.get(i);
				Elements columns = row.getElementsByTag("td");
				if (columns.size() > 1) {
					Standing standing = new Standing();
					standing.setUuid(UUID.randomUUID());
					standing.setSeason(teamSeasonMap.getSeason());
					standing.setTeam(teamSeasonMap.getTeam());
					standing.setPosition(new Integer(i-1));
					standing.setTeamName(columns.get(0).text());
					try {
						standing.setWins(getStripedInt(columns.get(1).text()));
						standing.setTies(getStripedInt(columns.get(2).text()));
						standing.setLosses(getStripedInt(columns.get(3).text()));
						standing.setGoalsFor(getStripedInt(columns.get(4).text()));
						standing.setGoalsAgainst(getStripedInt(columns.get(5).text()));
						standing.setPoints(getStripedInt(columns.get(6).text()));
						standing.setGamesPlayed(standing.getWins() + standing.getTies() + standing.getLosses());
					
						standings.add(standing);
						logger.debug("Adding Standing: " + "Team: " + standing.getTeamName() + "\tPosition: " + standing.getPosition() + "\tPoints: " + standing.getPoints());
					} catch (NumberFormatException nfe) {
						logger.error("Number Format issue: " + nfe.getMessage());
					}
				}
			}
		} catch (IOException e) {
			logger.error("Error getting Fixtures: " + e.getMessage(), e);
		}
		return standings;
	}
}
