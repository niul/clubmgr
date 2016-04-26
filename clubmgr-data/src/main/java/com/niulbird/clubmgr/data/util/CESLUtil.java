package com.niulbird.clubmgr.data.util;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

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

public class CESLUtil extends BaseUtil {
	// Logger for this class and subclasses
    private final Log logger = LogFactory.getLog(getClass());
    
    private final static String TIME_FORMAT = "hhmma";
    private final static String DATE_FORMAT = "d MMMMM, yyyy";
    
    private Properties props;
    
    public CESLUtil(Properties props) {
    	this.props = props;
    }
    
	public List<Fixture> getFixtures(TeamSeasonMap teamSeasonMap, String teamRegExStr) {
		Date date = null;
		List<Fixture> fixtures = new ArrayList<Fixture>();
		try {
			Document doc = Jsoup.connect(teamSeasonMap.getFixturesUri()).timeout(Integer.parseInt(props.getProperty("jsoup.timeout"))).get();
			Elements elements = doc.getElementsByTag("table");
			Element  element = elements.get(5); // Fifth table without a class or ID.
			
			Elements rows = element.getElementsByTag("tr");
			for (int i = 1; i < rows.size() - 1; i++) {
				Element row = rows.get(i);
				Elements columns = row.getElementsByTag("td");
				if (columns.size() == 7 && !columns.get(0).text().replaceAll("[^a-zA-Z0-9]", "").trim().isEmpty()) {
					Fixture fixture = new Fixture();
					
					String strDate = columns.get(0).text().substring(columns.get(0).text().indexOf(" ")).replaceAll("-", " ").replaceAll("[^ a-zA-Z0-9]", "").trim();
					if (!strDate.isEmpty()) {
						Calendar now = Calendar.getInstance();
						date = convertStringToDate(strDate + ", " + now.get(Calendar.YEAR), DATE_FORMAT);
					}
					fixture.setDate(date);
					fixture.setTime(convertStringToTime(columns.get(1).text().trim(), TIME_FORMAT));
					fixture.setField(columns.get(2).text().trim());
					fixture.setHome(columns.get(3).text().trim());
					fixture.setAway(columns.get(4).text().trim());
					fixture.setHomeScore(columns.get(5).text().trim().split(" ")[0]);
					fixture.setAwayScore(columns.get(6).text().trim().split(" ")[0]);
					
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
			Document doc = Jsoup.connect(teamSeasonMap.getStandingsUri()).timeout(Integer.parseInt(props.getProperty("jsoup.timeout"))).get();
			Elements elements = doc.getElementsByTag("table");
			Element  element = elements.get(4); // Fifth table without a class or ID.
			
			Elements rows = element.getElementsByTag("tr");
			for (int i = 1; i < rows.size() - 1; i++) {
				Element row = rows.get(i);
				Elements columns = row.getElementsByTag("td");
				if (columns.size() > 1) {
					Standing standing = new Standing();
					standing.setSeason(teamSeasonMap.getSeason());
					standing.setTeam(teamSeasonMap.getTeam());
					standing.setPosition(i);
					standing.setTeamName(columns.get(0).text().replaceAll("\u00A0", ""));
					
					try {
						standing.setGamesPlayed(getStripedInt(columns.get(1).text()));
						standing.setWins(getStripedInt(columns.get(2).text()));
						standing.setTies(getStripedInt(columns.get(3).text()));
						standing.setLosses(getStripedInt(columns.get(4).text()));
						standing.setPoints(getStripedInt(columns.get(6).text()));
						standing.setGoalsFor(getStripedInt(columns.get(7).text()));
						standing.setGoalsAgainst(getStripedInt(columns.get(8).text()));
						
						if (StringUtils.isNotBlank(standing.getTeamName())) {
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
