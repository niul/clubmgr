package com.niulbird.clubmgr.bfc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.niulbird.clubmgr.bfc.command.ContactData;
import com.niulbird.clubmgr.bfc.util.TeamUtility;
import com.niulbird.clubmgr.data.DataManager;
import com.niulbird.clubmgr.data.DataManagerFactory;
import com.niulbird.clubmgr.db.dto.FixtureStatisticDTO;
import com.niulbird.clubmgr.db.dto.PlayerStatisticDTO;
import com.niulbird.clubmgr.db.model.Fixture;
import com.niulbird.clubmgr.db.model.Standing;
import com.niulbird.clubmgr.db.model.TeamSeasonMap;
import com.niulbird.clubmgr.db.service.StatsService;
import com.niulbird.clubmgr.db.service.TeamService;
import com.niulbird.clubmgr.util.wordpress.WordPressDao;
import com.niulbird.clubmgr.util.wordpress.dao.Post;

@Controller
public class TeamController extends BaseController {
	private static final Logger log = LoggerFactory.getLogger(TeamController.class);

	private static final String FIXTURE_STATISTICS = "fixture_statistics";
	private static final String FIXTURES = "fixtures";
	private static final String PAGE = "page";
	private static final String PLAYER_STATS = "player_stats";
	private static final String SEASON_DETAILS = "season_details";
	private static final String STANDINGS = "standings";
	private static final String STANDINGS_FIXTURES = "standings_fixtures";
	private static final String TEAMSEASONMAP = "teamseasonmap";
	private static final String TITLE = "title";
	
	@Autowired
	private Properties props;
	
	@Autowired
	private WordPressDao wordPressDao;

	@Autowired
	private StatsService statsService;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private DataManagerFactory dataManagerFactory;
	
	@Transactional
	@RequestMapping(value = "/seasonDetail/{team}/{season}")
	public ModelAndView teamSeasonDetails(@PathVariable(value = "team") String teamKey,
			@PathVariable(value = "season") String seasonKey,
			HttpServletRequest request) {
		log.debug("Getting Standings: " + request.getRequestURL());
		TeamSeasonMap teamSeasonMap = teamService.findTeamSeasonMap(teamKey, seasonKey);
		List<Standing> standings = teamService.findStandings(teamSeasonMap.getTeam(), teamSeasonMap.getSeason());
		
		if (standings.size() == 0) {
				log.debug("Updating Standings: [" + teamKey + "|" + seasonKey +"]");
				DataManager dataManager = dataManagerFactory.createDataManager(teamSeasonMap);
				standings = dataManager.updateStandings();
		}
		
		List<Fixture> fixtures = teamService.findFixtures(teamSeasonMap.getTeam(), teamSeasonMap.getSeason());
		List<FixtureStatisticDTO> fixtureStatisticsList = new ArrayList<FixtureStatisticDTO>();
		
		if (fixtures.size() == 0) {
					log.debug("Updating Fixtures: [" + teamKey + "|" + seasonKey +"]");
				DataManager dataManager = dataManagerFactory.createDataManager(teamSeasonMap);
				fixtures = dataManager.updateFixtures();
		} else {
			fixtureStatisticsList = TeamUtility.getFixtureStatistics(fixtures, props.getProperty("team.regex"));
		}
		
		List<PlayerStatisticDTO> playerStatistics = statsService.getTeamSeasonStats(teamSeasonMap.getTeam().getTeamKey(), teamSeasonMap.getSeason().getSeasonKey());

		
		ModelAndView mav = setView(SEASON_DETAILS);
		mav.addObject(TITLE, teamSeasonMap.getTeam().getName() + " - " + teamSeasonMap.getSeason().getName());
		mav.addObject(TEAMSEASONMAP, teamSeasonMap);
		mav.addObject(STANDINGS, standings);
		mav.addObject(FIXTURE_STATISTICS, fixtureStatisticsList);
		mav.addObject(PLAYER_STATS, playerStatistics);
		
		log.debug("Got " + standings.size() + " Standings and " + fixtures.size() + " Fixtures");
		
		return mav;
	}
	
	@RequestMapping(value = "/season/{team}/{season}")
	public ModelAndView teamSeason(@PathVariable(value = "team") String teamKey,
			@PathVariable(value = "season") String seasonKey,
			HttpServletRequest request) {
		log.debug("Getting Standings: " + request.getRequestURL());
		TeamSeasonMap teamSeasonMap = teamService.findTeamSeasonMap(teamKey, seasonKey);
		List<Standing> standings = teamService.findStandings(teamSeasonMap.getTeam(), teamSeasonMap.getSeason());
		
		if (standings.size() == 0) {
				log.debug("Updating Standings: [" + teamKey + "|" + seasonKey +"]");
				DataManager dataManager = dataManagerFactory.createDataManager(teamSeasonMap);
				standings = dataManager.updateStandings();
		}
		
		List<Fixture> fixtures = teamService.findFixtures(teamSeasonMap.getTeam(), teamSeasonMap.getSeason());
		
		if (fixtures.size() == 0) {
					log.debug("Updating Fixtures: [" + teamKey + "|" + seasonKey +"]");
				DataManager dataManager = dataManagerFactory.createDataManager(teamSeasonMap);
				fixtures = dataManager.updateFixtures();
		}

		
		ModelAndView mav = setView(STANDINGS_FIXTURES);
		mav.addObject(TITLE, teamSeasonMap.getTeam().getName() + " - " + teamSeasonMap.getSeason().getName());
		mav.addObject(TEAMSEASONMAP, teamSeasonMap);
		mav.addObject(STANDINGS, standings);
		mav.addObject(FIXTURES, fixtures);
		
		log.debug("Got " + standings.size() + " Standings and " + fixtures.size() + " Fixtures");
		
		return mav;
	}
	
	@RequestMapping(value = "/fixtures/{team}/{season}")
	public ModelAndView teamFixture(@PathVariable(value = "team") String teamKey,
			@PathVariable(value = "season") String seasonKey,
			HttpServletRequest request) {
		log.debug("Getting Standings: " + request.getRequestURL());
		TeamSeasonMap teamSeasonMap = teamService.findTeamSeasonMap(teamKey, seasonKey);
		
		List<Fixture> fixtures = teamService.findFixtures(teamSeasonMap.getTeam(), teamSeasonMap.getSeason());
		
		ModelAndView mav = setView(FIXTURES);
		mav.addObject(TITLE, teamSeasonMap.getTeam().getName() + " - " + teamSeasonMap.getSeason().getName());
		mav.addObject(TEAMSEASONMAP, teamSeasonMap);
		mav.addObject(FIXTURES, fixtures);
		
		log.debug("Got " + fixtures.size() + " Fixtures");
		
		return mav;
	}
	
	private ModelAndView setView(String pageName) {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName(pageName);
		mav.addObject(PAGE, pageName);
		mav.addObject("contactData", new ContactData());
		
		ArrayList<Post> posts = wordPressDao.getAllPosts();
		mav.addObject("posts", posts);
		mav.addObject("footerPosts", posts.subList(0,
				(posts.size() < Integer.parseInt(numFooterPosts)) ? posts.size()
						: Integer.parseInt(numFooterPosts)));

		log.debug("Setting view: " + pageName);
		
		return mav;
	}
}
