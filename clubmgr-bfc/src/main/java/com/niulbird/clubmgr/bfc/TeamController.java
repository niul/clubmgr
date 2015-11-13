package com.niulbird.clubmgr.bfc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.niulbird.clubmgr.bfc.command.ContactData;
import com.niulbird.clubmgr.bfc.wordpress.WordPressDao;
import com.niulbird.clubmgr.bfc.wordpress.dao.Post;
import com.niulbird.clubmgr.data.DataManager;
import com.niulbird.clubmgr.data.DataManagerFactory;
import com.niulbird.clubmgr.db.model.Fixture;
import com.niulbird.clubmgr.db.model.Standing;
import com.niulbird.clubmgr.db.model.TeamSeasonMap;
import com.niulbird.clubmgr.db.service.TeamService;

@Controller
public class TeamController extends BaseController {
	private static final Logger log = Logger.getLogger(TeamController.class);
	
	private final static long DAY_IN_MS = 24 * 60 * 60 * 1000;

	private static final String FIXTURES = "fixtures";
	private static final String PAGE = "page";
	private static final String STANDINGS = "standings";
	private static final String STANDINGS_FIXTURES = "standings_fixtures";
	private static final String TEAMSEASONMAP = "teamseasonmap";
	
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
		mav.addObject(TEAMSEASONMAP, teamSeasonMap);
		mav.addObject(STANDINGS, standings);
		
		return mav;
	}
	
	@RequestMapping(value = "/standings_fixtures.html")
	public ModelAndView standingsFixtures(@RequestParam(value = "team") String teamKey,
			@RequestParam(value = "season") String seasonKey) {
		log.debug("Getting Standings for " + teamKey + "/" + seasonKey);
		TeamSeasonMap teamSeasonMap = teamService.findTeamSeasonMap(teamKey, seasonKey);
		List<Standing> standings = teamService.findStandings(teamSeasonMap.getTeam(), teamSeasonMap.getSeason());
		
		if (standings.size() == 0 ||
					new Date().after(new Date((standings.get(0).getCreated().getTime() + DAY_IN_MS)))) {
				log.debug("Updating Standings: [" + teamKey + "|" + seasonKey +"]");
				DataManager dataManager = dataManagerFactory.createDataManager(teamKey, seasonKey, "Bombastic");
				standings = dataManager.updateStandings();
		}
		
		List<Fixture> fixtures = teamService.findFixtures(teamSeasonMap.getTeam(), teamSeasonMap.getSeason());
		
		if (fixtures.size() == 0 ||
				new Date().after(new Date((fixtures.get(0).getCreated().getTime() + DAY_IN_MS)))) {
					log.debug("Updating Fixtures: [" + teamKey + "|" + seasonKey +"]");
				DataManager dataManager = dataManagerFactory.createDataManager(teamKey, seasonKey, "Bombastic");
				fixtures = dataManager.updateFixtures();
		}

		
		ModelAndView mav = setView(STANDINGS_FIXTURES);
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
