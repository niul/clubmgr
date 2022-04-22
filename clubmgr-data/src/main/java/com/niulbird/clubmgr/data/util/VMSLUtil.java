		package com.niulbird.clubmgr.data.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

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

public class VMSLUtil extends BaseUtil {
    private final Log logger = LogFactory.getLog(getClass());
    
    private final static String TIME_FORMAT = "EEE MM/d/yyyy h:mma";
    private final static String DATE_FORMAT = "EEE MM/d/yyyy h:mma";
    
	private static final String VMSL_URI = "https://vmslsoccer.com";
    
    public VMSLUtil(Properties props) {
    	this.props = props;
    }
    
	public List<Fixture> getFixtures(TeamSeasonMap teamSeasonMap) {
		List<Fixture> fixtures = new ArrayList<Fixture>();
		try {
			Document doc = Jsoup.connect(teamSeasonMap.getFixturesUri()).timeout(Integer.parseInt(props.getProperty("jsoup.timeout"))).get();
			Elements elements = doc.getElementsByTag("table");

			for (Element element : elements) {
			
			Elements rows = element.getElementsByTag("tr");
				if (rows != null && rows.size() > 0) {
					Elements headerRow = rows.get(0).getElementsByTag("td");
					if (headerRow != null && headerRow.size() > 1
							&& headerRow.get(0).text().equalsIgnoreCase("Date")) {
						for (int i = 1; i < rows.size(); i++) {
							Element row = rows.get(i);
							Elements columns = row.getElementsByTag("td");
							if (columns.size() > 1) {
								Fixture fixture = new Fixture();
								fixture.setUuid(UUID.randomUUID());
								fixture.setDate(convertStringToDate(columns.get(0).text(), DATE_FORMAT));
								fixture.setTime(convertStringToTime(columns.get(0).text(), TIME_FORMAT));
								fixture.setHome(columns.get(1).text());
								String[] score = columns.get(2).text().split("-");
								if (score.length == 2) {
									fixture.setHomeScore(score[0].trim());
									fixture.setAwayScore(score[1].trim());
								}
								fixture.setAway(columns.get(3).text());
								fixture.setField(columns.get(4).text());
								Elements fieldLink = columns.get(4).getElementsByTag("a");
								if (fieldLink.size() > 0) {
									String vmslFieldLink = VMSL_URI + fieldLink.get(0).attr("href");
									String fieldMapUri = null;
									try {
										Document fieldDoc = Jsoup.connect(vmslFieldLink).get();
										Elements fieldElements = fieldDoc.getElementsByTag("table");
										Element fieldElement = fieldElements.get(2); // 3rd table with no ID or Class
										Elements fieldRows = fieldElement.getElementsByTag("tr");

										for (Element fieldRow : fieldRows) {
											Elements rowElements = fieldRow.getElementsByTag("td");
											if (rowElements.get(0).text().equalsIgnoreCase("Map")) {
												Elements mapElements = rowElements.get(2).getElementsByTag("a");
												if (mapElements.size() > 0) {
													fieldMapUri = StringUtils.chomp(mapElements.get(0).attr("href"))
															.replaceAll("[\n\r]", "").trim();
												}
											}
										}
									} catch (IOException ioe) {
										logger.error("Error getting field URL: " + ioe, ioe);
									}

									if (fieldMapUri == null) {
										fieldMapUri = vmslFieldLink;
									}
									fixture.setFieldMapUri(fieldMapUri);
								}
								fixture.setSeason(teamSeasonMap.getSeason());
								fixture.setTeam(teamSeasonMap.getTeam());

								// Check if fixture is cancelled.
								if (row.attr("class").equalsIgnoreCase("dimmedstrikethrough")) {
									fixture.setActive(false);
								} else {
									fixture.setActive(true);
								}
								
								if ((fixture.getHome().contains(teamSeasonMap.getNameRegex())
										|| fixture.getAway().contains(teamSeasonMap.getNameRegex()))
										&& !fixture.getField().equalsIgnoreCase("BYE")) {
									fixtures.add(fixture);
									logger.debug("Added Fixture: " + i + "\tHome: " + fixture.getHome() + "\t"
											+ fixture.getHomeScore() + ":" + fixture.getAwayScore() + " \tAway: "
											+ fixture.getAway() + "\tDate: " + fixture.getDate() + "\tTime: "
											+ fixture.getTime());
								}
							}
						}
					}
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
			Document doc = Jsoup.connect(teamSeasonMap.getFixturesUri()).timeout(Integer.parseInt(props.getProperty("jsoup.timeout"))).get();
			Elements elements = doc.getElementsByTag("table");
			
			for (Element element : elements) {

				Elements rows = element.getElementsByTag("tr");
				if (rows != null && rows.size() > 0) {
					Elements headerRow = rows.get(0).getElementsByTag("td");
					if (headerRow != null && headerRow.size() > 0
							&& headerRow.get(0).text().equalsIgnoreCase("Team")) {

						for (int i = 1; i < rows.size(); i++) {
							Element row = rows.get(i);
							Elements columns = row.getElementsByTag("td");
							if (columns.size() > 1) {
								Standing standing = new Standing();
								standing.setUuid(UUID.randomUUID());
								standing.setSeason(teamSeasonMap.getSeason());
								standing.setTeam(teamSeasonMap.getTeam());
								standing.setPosition(Integer.valueOf(i));
								standing.setTeamName(columns.get(0).text());
								standing.setGamesPlayed(getStripedInt(columns.get(1).text()));
								standing.setWins(getStripedInt(columns.get(2).text()));
								standing.setTies(getStripedInt(columns.get(3).text()));
								standing.setLosses(getStripedInt(columns.get(4).text()));
								standing.setGoalsFor(getStripedInt(columns.get(5).text()));
								standing.setGoalsAgainst(getStripedInt(columns.get(6).text()));
								standing.setPoints(getStripedInt(columns.get(8).text()));
								standings.add(standing);
								logger.debug("Adding Standing: " + "Team: " + standing.getTeamName() + "\tPosition: "
										+ standing.getPosition() + "\tPoints: " + standing.getPoints());
							}
						}
					}
				}
			}
		} catch (IOException e) {
			logger.error("Error getting Fixtures: " + e.getMessage(), e);
		}
		return standings;
	}
}
