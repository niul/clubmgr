package com.niulbird.clubmgr.data.util;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
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
    private final static String DATE_FORMAT = "dd-MMM-yyyy";
    
    public RRSLUtil(Properties props) {
    	this.props = props;
    }
    
	public List<Fixture> getFixtures(TeamSeasonMap teamSeasonMap, String teamRegExStr) {
		
		List<Fixture> fixtures = new ArrayList<Fixture>();
		try {
			Document doc = Jsoup.connect(teamSeasonMap.getFixturesUri()).timeout(Integer.parseInt(props.getProperty("jsoup.timeout"))).get();
			Elements rows = doc.getElementsByTag("tr");
			Date date = null;
			for (int i = 6; i < rows.size() - 1; i++) {
				Element row = rows.get(i);
				Elements columns = row.getElementsByTag("td");
				if (columns.size() > 1) {
					Fixture fixture = new Fixture();
					fixture.setUuid(UUID.randomUUID());
					fixture.setActive(true);
					
					String strDate = columns.get(1).text().trim().split("\\s+")[1];
					if (!strDate.isEmpty()) {
						date = convertStringToDate(strDate, DATE_FORMAT);
					}
					fixture.setDate(date);
					
					String time = columns.get(2).text().trim().split("\\s+")[0] + "M";
					fixture.setTime(convertStringToTime(time, TIME_FORMAT));
					
					fixture.setHome(columns.get(3).text().split("\\s\\(")[0].trim());
					fixture.setHomeScore(columns.get(4).text().trim());
					fixture.setAway(columns.get(5).text().split("\\s\\(")[0].trim());
					fixture.setAwayScore(columns.get(6).text().trim());
					fixture.setField(columns.get(7).text().trim());
					fixture.setFieldMapUri(props.getProperty("field.url." + fixture.getField().replaceAll(" ", "")));
					
					fixture.setSeason(teamSeasonMap.getSeason());
					fixture.setTeam(teamSeasonMap.getTeam());

					logger.debug("Added Fixture: " + i + "\tHome: " + fixture.getHome() + "\t" + fixture.getHomeScore() + ":" + fixture.getAwayScore() + " \tAway: " + fixture.getAway() + "\tDate: " + fixture.getDate() + "\tTime: " + fixture.getTime());
					fixtures.add(fixture);
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
			Document doc = Jsoup.connect(teamSeasonMap.getStandingsUri()).timeout(Integer.parseInt(props.getProperty("jsoup.timeout"))).get();
			Element element = doc.select("table#table1").first();
			
			Elements rows = element.getElementsByTag("tr");
			for (int i = 1; i < rows.size(); i++) {
				Element row = rows.get(i);
				Elements columns = row.getElementsByTag("td");
				if (columns.size() > 1) {
					Standing standing = new Standing();
					standing.setUuid(UUID.randomUUID());
					standing.setSeason(teamSeasonMap.getSeason());
					standing.setTeam(teamSeasonMap.getTeam());
					standing.setPosition(new Integer(i));
					standing.setTeamName(columns.get(0).text());
					try {
						standing.setGamesPlayed(getStripedInt(columns.get(1).text()));
						standing.setWins(getStripedInt(columns.get(2).text()));
						standing.setLosses(getStripedInt(columns.get(3).text()));
						standing.setTies(getStripedInt(columns.get(4).text()));
						standing.setPoints(getStripedInt(columns.get(5).text()));
						standing.setGoalsFor(getStripedInt(columns.get(6).text()));
						standing.setGoalsAgainst(getStripedInt(columns.get(7).text()));
					
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
