package com.niulbird.clubmgr;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.MessageSource;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.niulbird.clubmgr.db.model.Club;
import com.niulbird.clubmgr.db.model.Fixture;
import com.niulbird.clubmgr.db.model.Player;
import com.niulbird.clubmgr.db.model.PlayerFixtureInfo;
import com.niulbird.clubmgr.db.model.Position;
import com.niulbird.clubmgr.db.model.Season;
import com.niulbird.clubmgr.db.model.Status;
import com.niulbird.clubmgr.db.model.Team;
import com.niulbird.clubmgr.util.freemarker.MessageResolverMethod;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

import org.junit.Assert;
import org.junit.Before;

@EnableCaching
@EnableAutoConfiguration(exclude = { FreeMarkerAutoConfiguration.class })
public class FixtureAvailabilityJobTest extends BaseTestCase {

	@Autowired
	private Configuration freeMarkerConfiguration;

	@Autowired
	private MessageSource messageSource;

	Club club;
	Team team;
	Season season;
	Fixture fixture;
	Position position;
	Player player;
	PlayerFixtureInfo playerFixtureInfo;

	@Before
	public void setUp() {
		club = new Club();
		club.setClubKey("FIXTURE_UNIT_TEST");
		club.setName("FIXTURE_UNIT_TEST");

		team = new Team();
		team.setTeamKey("FIXTURE_UNIT_TEST");
		team.setName("FIXTURE_UNIT_TEST");
		team.setClub(club);
		Set<Team> teams = new HashSet<Team>();
		teams.add(team);

		season = new Season();
		season.setSeasonKey("FIXTURE_UNIT_TEST");
		season.setName("FIXTURE_UNIT_TEST");

		fixture = new Fixture();
		fixture.setUuid(UUID.randomUUID());
		fixture.setTeam(team);
		fixture.setSeason(season);
		fixture.setAway("FIXTURE_UNIT_TEST");
		fixture.setDate(new java.sql.Date(new java.util.Date().getTime()));
		fixture.setField("FIXTURE_UNIT_TEST");
		fixture.setFieldMapUri("http://FIXTURE_UNIT_TEST");
		fixture.setHome("FIXTURE_UNIT_TEST");
		fixture.setTime(new java.sql.Time(new java.util.Date().getTime()));

		position = new Position();
		position.setDescription("UNIT_TEST");
		position.setKey("UNIT_TEST");

		player = new Player();
		player.setAddress1("FIXTURE_UNIT_TEST");
		player.setCity("FIXTURE_UNIT_TEST");
		player.setClub(club);
		player.setFirstName("FIXTURE_UNIT_TEST");
		player.setLastName("FIXTURE_UNIT_TEST");
		player.setEmail("test@test.com");
		player.setEnabled(true);
		player.setPosition(position);
		player.setTeams(teams);

		playerFixtureInfo = new PlayerFixtureInfo();
		playerFixtureInfo.setComment("FIXTURE_UNIT_TEST");
		playerFixtureInfo.setUuid(UUID.randomUUID());
		playerFixtureInfo.setStatus(Status.YES);
		playerFixtureInfo.setPlayer(player);
		playerFixtureInfo.setFixture(fixture);
		playerFixtureInfo.setStarted(false);
		playerFixtureInfo.setSubstitute(false);
		playerFixtureInfo.setYellowCard(false);
		playerFixtureInfo.setRedCard(false);
	}

	@Test
	public void fixtureEmail() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lang", messageSource);
		String body = new String();
		try {
			map.put("msg", new MessageResolverMethod(messageSource, null));
			map.put("fixture", fixture);
			map.put("playerFixtureInfo", playerFixtureInfo);
			body = FreeMarkerTemplateUtils.processTemplateIntoString(freeMarkerConfiguration.getTemplate("fixture.ftl"),
					map);
		} catch (IOException | TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.debug("Body: " + body);
		Assert.assertTrue(body.length() > 0);
	}
}
