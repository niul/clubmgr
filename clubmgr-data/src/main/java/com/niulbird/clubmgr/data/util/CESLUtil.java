package com.niulbird.clubmgr.data.util;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.niulbird.clubmgr.db.model.Fixture;
import com.niulbird.clubmgr.db.model.Standing;
import com.niulbird.clubmgr.db.model.TeamSeasonMap;

public class CESLUtil extends BaseUtil {
	// Logger for this class and subclasses
    private final Log logger = LogFactory.getLog(getClass());
    
    private final static String TIME_FORMAT = "hhmma";
    private final static String DATE_FORMAT = "d-MMMMM-yyyy";
    
    private Properties props;
    
    public CESLUtil(Properties props) {
    	this.props = props;
    }
    
	public List<Fixture> getFixtures(TeamSeasonMap teamSeasonMap) {
		Date date = null;
		List<Fixture> fixtures = new ArrayList<Fixture>();
		try {
			Document doc = Jsoup.connect(teamSeasonMap.getFixturesUri()).timeout(Integer.parseInt(props.getProperty("jsoup.timeout"))).get();
			Elements elements = doc.getElementsByTag("table");
			Element  element = elements.get(1); // 2nd Table Element
			
			Elements rows = element.getElementsByTag("tr");
			for (int i = 1; i < rows.size(); i++) {
				Element row = rows.get(i);
				Elements columns = row.getElementsByTag("td");

				Fixture fixture = new Fixture();
				fixture.setUuid(UUID.randomUUID());
				fixture.setActive(true);
					
				String strDate = columns.get(1).text().substring(columns.get(1).text().indexOf(" "));
				if (!strDate.isEmpty()) {
					date = convertStringToDate(strDate, DATE_FORMAT);
				}
				fixture.setDate(date);
				fixture.setTime(convertStringToTime(columns.get(2).text().trim(), TIME_FORMAT));
				fixture.setField(columns.get(3).text().trim());
				
				String homeColumn = columns.get(4).text().trim();
				Matcher matcher = Pattern.compile("[^0-9]*([0-9]+).*").matcher(homeColumn);
				matcher.find();
				if (matcher.matches()) {
					int start = matcher.start(1);
					fixture.setHome(homeColumn.substring(0, start - 1).replaceAll("\u00a0", "").trim());
					fixture.setHomeScore(homeColumn.substring(start, start + 1));
				} else {
					fixture.setHome(homeColumn);
				}
				String awayColumn = columns.get(5).text().trim();
				matcher = Pattern.compile("[^0-9]*([0-9]+).*").matcher(awayColumn);
				matcher.find();
				if (matcher.matches()) {
					int start = matcher.start(1);
					fixture.setAway(awayColumn.substring(0, start - 1).replaceAll("\u00a0", "").trim());
					fixture.setAwayScore(awayColumn.substring(start, start + 1));
				} else {
					fixture.setAway(awayColumn);
				}
					
				fixture.setFieldMapUri(props.getProperty("field.url." + fixture.getField().replaceAll(" ", "")));
				fixture.setSeason(teamSeasonMap.getSeason());
				fixture.setTeam(teamSeasonMap.getTeam());

				if (fixture.getHome().contains(teamSeasonMap.getNameRegex()) || fixture.getAway().contains(teamSeasonMap.getNameRegex())) {
					fixtures.add(fixture);
					logger.debug("Added Fixture: " + i + "\tHome: " + fixture.getHome() + "\t" + fixture.getHomeScore() + ":" + fixture.getAwayScore() + " \tAway: " + fixture.getAway() + "\tDate: " + fixture.getDate() + "\tTime: " + fixture.getTime() + "\tField: " + fixture.getFieldMapUri());
				}
			}
		} catch (IOException e) {
			logger.error("Error getting Fixtures: " + e.getMessage(), e);
		}

		return fixtures;
	}
	
	public List<Standing> getStandings(TeamSeasonMap teamSeasonMap) {
		List<Standing> standings = new ArrayList<Standing>();
		try {
			Document doc = Jsoup.connect(teamSeasonMap.getStandingsUri()).timeout(Integer.parseInt(props.getProperty("jsoup.timeout"))).get();
			Elements elements = doc.getElementsByTag("table");
			Element  element = elements.get(1); // 2nd Table Element
			
			Elements rows = element.getElementsByTag("tr");
			for (int i = 1; i < rows.size(); i++) {
				Element row = rows.get(i);
				Elements columns = row.getElementsByTag("td");
				if (columns.size() > 1) {
					Standing standing = new Standing();
					standing.setUuid(UUID.randomUUID());
					standing.setSeason(teamSeasonMap.getSeason());
					standing.setTeam(teamSeasonMap.getTeam());
					standing.setPosition(i);
					standing.setTeamName(columns.get(0).text());
					
					try {
						standing.setGamesPlayed(getStripedInt(columns.get(1).text().split("\\s+")[0]));
						standing.setWins(getStripedInt(columns.get(2).text()));
						standing.setTies(getStripedInt(columns.get(3).text()));
						standing.setLosses(getStripedInt(columns.get(4).text()));
						standing.setPoints(getStripedInt(columns.get(5).text()));
						standing.setGoalsFor(getStripedInt(columns.get(6).text()));
						standing.setGoalsAgainst(getStripedInt(columns.get(7).text()));
						
						if (!standing.getTeamName().isBlank()) {
							standings.add(standing);
							logger.debug("Adding Standing: " + "Team: " + standing.getTeamName() + "\tPosition: " + standing.getPosition() + "\tPoints: " + standing.getPoints());
						}
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
