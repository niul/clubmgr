package com.niulbird.clubmgr.bfc.controller.admin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.niulbird.clubmgr.bfc.command.PlayerFixtureStatisticList;
import com.niulbird.clubmgr.bfc.command.PlayerFixtureStatisticListValidator;
import com.niulbird.clubmgr.db.model.Fixture;
import com.niulbird.clubmgr.db.model.Season;
import com.niulbird.clubmgr.db.model.Team;
import com.niulbird.clubmgr.db.service.FixtureService;
import com.niulbird.clubmgr.db.service.TeamService;

@Controller
public class ReportController extends AdminBaseController {
	private static final Logger log = Logger.getLogger(ReportController.class);

	private static final String ADMIN_REPORTS = "admin_reports";
	private static final String ADMIN_REPORT = "admin_report";
	private static final String FIXTURES = "fixtures";
	private static final String FIXTURE = "fixture";
	
	@Autowired
	FixtureService fixtureService;
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	PlayerFixtureStatisticListValidator playerFixtureStatisticListValidator;

	@Transactional
	@RequestMapping(value = "/admin/reports.html")
	public ModelAndView players(@RequestParam (required = false) String uuid,
			@RequestParam (required = false) String seasonKey,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		Team team;
		List<Fixture> fixtures = new ArrayList<Fixture>();
		
		log.debug("Getting Match Report List for [" + getPrincipal() + "][" + uuid + "][" + seasonKey + "]");
		
		mav = getFilterObjects(ADMIN_REPORTS, uuid, false, seasonKey, request);
		
		if (StringUtils.isNotBlank(uuid)) {
			team = teamService.findByUuid(uuid);
		} else {
			team = (Team)mav.getModel().get(TEAM);
		}
		
		if (StringUtils.isBlank(seasonKey)) {
			seasonKey = ((Season)mav.getModel().get(SEASON)).getSeasonKey();
		}
		fixtures = teamService.findFixtures(team, (Season)mav.getModel().get(SEASON));

		mav.addObject(FIXTURES, fixtures);
		
		return mav;
	}
	
	@Transactional
	@RequestMapping(value = "/admin/editReport.html", method = RequestMethod.GET)
	public ModelAndView editReport(@RequestParam (required = false) String uuid,
			@ModelAttribute("playerFixtureStatisticList") PlayerFixtureStatisticList playerFixtureStatisticList,
			@RequestParam (required = false) String teamUuid,
			@RequestParam (required = false) String seasonKey,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		log.debug("Editing Players for [" + getPrincipal() + "][" + uuid + "]");

		mav = getFilterObjects(ADMIN_REPORT, teamUuid, false, seasonKey, request);
		
		Fixture fixture = fixtureService.findFixtureByUuid(uuid);
		playerFixtureStatisticList.setPlayerFixtureStatisticList(fixtureService.findPlayerStatisticsByFixture(fixture, teamUuid, seasonKey));
		
		mav.addObject(FIXTURE, fixture);
		mav.addObject("playerFixtureStatisticList", playerFixtureStatisticList);
		
		return mav;
	}
	
	@Transactional
	@RequestMapping(value = "/admin/editReport.html", method = RequestMethod.POST)
	public ModelAndView editReport(@RequestParam (required = false) String uuid,
			@Valid PlayerFixtureStatisticList playerFixtureStatisticList,
			BindingResult result,
			@RequestParam (required = false) String teamUuid,
			@RequestParam (required = false) String seasonKey,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		log.debug("Editing Report for [" + getPrincipal() + "][" + uuid + "]");

		mav = getFilterObjects(ADMIN_REPORT, teamUuid, false, seasonKey, request);

		Fixture fixture = fixtureService.findFixtureByUuid(uuid);
		playerFixtureStatisticList.setFixture(fixture);
		playerFixtureStatisticListValidator.validate(playerFixtureStatisticList, result);
		
		if (result.hasErrors()) {
			mav = getFilterObjects(ADMIN_REPORT, teamUuid, false, seasonKey, request);
			
			mav.addObject(FIXTURE, fixture);
			mav.addObject("playerFixtureStatisticList", playerFixtureStatisticList);
			
			return mav;
		}
		fixtureService.updateFixtureReport(fixture, playerFixtureStatisticList.getPlayerFixtureStatisticList());
		mav.setViewName("redirect:/admin/reports.html?uuid=" + teamUuid + "&seasonKey=" + seasonKey);
		
		return mav;
	}
	
}
