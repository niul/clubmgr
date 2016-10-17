package com.niulbird.clubmgr.bfc.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.niulbird.clubmgr.bfc.command.ContactData;
import com.niulbird.clubmgr.data.DataManager;
import com.niulbird.clubmgr.data.DataManagerFactory;
import com.niulbird.clubmgr.db.model.Fixture;
import com.niulbird.clubmgr.db.model.Standing;
import com.niulbird.clubmgr.db.model.TeamSeasonMap;
import com.niulbird.clubmgr.db.service.TeamService;
import com.niulbird.clubmgr.util.wordpress.WordPressDao;
import com.niulbird.clubmgr.util.wordpress.dao.Post;

@Controller
public class TeamController extends BaseController {
	private static final Logger log = Logger.getLogger(TeamController.class);

	private static final String FIXTURES = "fixtures";
	private static final String PAGE = "page";
	private static final String STANDINGS = "standings";
	private static final String STANDINGS_FIXTURES = "standings_fixtures";
	private static final String TEAMSEASONMAP = "teamseasonmap";
	private static final String TITLE = "title";
	
	@Autowired
	private WordPressDao wordPressDao;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private DataManagerFactory dataManagerFactory;
	
	@RequestMapping(value = "/fixtures.html")
	public ModelAndView fixtures(@RequestParam(value = "team") String teamKey,
			@RequestParam(value = "season") String seasonKey) {
		log.debug("Getting Fixtures for " + teamKey + "/" + seasonKey);
		TeamSeasonMap teamSeasonMap = teamService.findTeamSeasonMap(teamKey, seasonKey);
		List<Fixture> fixtures = teamService.findFixtures(teamSeasonMap.getTeam(), teamSeasonMap.getSeason());
		
		ModelAndView mav = setView(FIXTURES);
		mav.addObject(TITLE, teamSeasonMap.getTeam().getName() + " - " + teamSeasonMap.getSeason().getName());
		mav.addObject(TEAMSEASONMAP, teamSeasonMap);
		mav.addObject(FIXTURES, fixtures);
		
		return mav;
	}
	
	@RequestMapping(value = "/standings.html")
	public ModelAndView standings(@RequestParam(value = "team") String teamKey,
			@RequestParam(value = "season") String seasonKey) {
		log.debug("Getting Standings for " + teamKey + "/" + seasonKey);
		TeamSeasonMap teamSeasonMap = teamService.findTeamSeasonMap(teamKey, seasonKey);
		List<Standing> standings = teamService.findStandings(teamSeasonMap.getTeam(), teamSeasonMap.getSeason());
		
		ModelAndView mav = setView(STANDINGS);
		mav.addObject(TITLE, teamSeasonMap.getTeam().getName() + " - " + teamSeasonMap.getSeason().getName());
		mav.addObject(TEAMSEASONMAP, teamSeasonMap);
		mav.addObject(STANDINGS, standings);
		
		return mav;
	}
	
	@RequestMapping(value = "/season/{team}/{season}")
	public ModelAndView teamSeason(@PathVariable(value = "team") String teamKey,
			@PathVariable(value = "season") String seasonKey) {
		log.debug("Getting Standings for " + teamKey + "/" + seasonKey);
		TeamSeasonMap teamSeasonMap = teamService.findTeamSeasonMap(teamKey, seasonKey);
		List<Standing> standings = teamService.findStandings(teamSeasonMap.getTeam(), teamSeasonMap.getSeason());
		
		if (standings.size() == 0) {
				log.debug("Updating Standings: [" + teamKey + "|" + seasonKey +"]");
				DataManager dataManager = dataManagerFactory.createDataManager(teamSeasonMap, "Bombastic");
				standings = dataManager.updateStandings();
		}
		
		List<Fixture> fixtures = teamService.findFixtures(teamSeasonMap.getTeam(), teamSeasonMap.getSeason());
		
		if (fixtures.size() == 0) {
					log.debug("Updating Fixtures: [" + teamKey + "|" + seasonKey +"]");
				DataManager dataManager = dataManagerFactory.createDataManager(teamSeasonMap, "Bombastic");
				fixtures = dataManager.updateFixtures();
		}

		
		ModelAndView mav = setView(STANDINGS_FIXTURES);
		mav.addObject(TITLE, teamSeasonMap.getTeam().getName() + " - " + teamSeasonMap.getSeason().getName());
		mav.addObject(TEAMSEASONMAP, teamSeasonMap);
		mav.addObject(STANDINGS, standings);
		mav.addObject(FIXTURES, fixtures);
		
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
