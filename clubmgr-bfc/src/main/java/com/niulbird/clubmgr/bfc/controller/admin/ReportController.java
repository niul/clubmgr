package com.niulbird.clubmgr.bfc.controller.admin;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.niulbird.clubmgr.bfc.command.PlayerFixtureInfoList;
import com.niulbird.clubmgr.bfc.command.PlayerFixtureInfoListValidator;
import com.niulbird.clubmgr.db.model.Fixture;
import com.niulbird.clubmgr.db.model.Season;
import com.niulbird.clubmgr.db.model.Team;
import com.niulbird.clubmgr.db.service.FixtureService;
import com.niulbird.clubmgr.db.service.TeamService;
import com.niulbird.clubmgr.email.service.EmailService;

@Controller
public class ReportController extends AdminBaseController {
	private static final Logger log = LogManager.getLogger();

	private static final String ADMIN_REPORTS = "admin/reports";
	private static final String ADMIN_REPORT = "admin/report";
	private static final String FIXTURES = "fixtures";
	private static final String FIXTURE = "fixture";
	
	@SuppressWarnings("serial")
	private static final List<Integer> RATINGS = new ArrayList<Integer>() {{
        add(0);
        add(1);
        add(2);
        add(3);
        add(4);
        add(5);
        add(6);
        add(7);
        add(8);
        add(9);
        add(10);
    }};
    
	@Autowired
	EmailService emailService;
	
	@Autowired
	FixtureService fixtureService;
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	PlayerFixtureInfoListValidator playerFixtureInfoListValidator;

	@Transactional
	@RequestMapping(value = "/admin/reports.html")
	public ModelAndView players(@RequestParam (required = false) String uuid,
			@RequestParam (required = false) String seasonKey,
			@RequestParam (required = false) boolean sendEmail,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		Team team;
		List<Fixture> fixtures = new ArrayList<Fixture>();
		
		log.debug("Getting Match Report List for [" + getPrincipal() + "][" + uuid + "][" + seasonKey + "]");
		
		mav = getFilterObjects(ADMIN_REPORTS, uuid, false, seasonKey, request);
		
		if (uuid != null && !uuid.isBlank()) {
			team = teamService.findByUuid(uuid);
		} else {
			team = (Team)mav.getModel().get(TEAM);
		}
		
		if (seasonKey != null && !seasonKey.isBlank()) {
			seasonKey = ((Season)mav.getModel().get(SEASON)).getSeasonKey();
		}
		fixtures = teamService.findFixtures(team, (Season)mav.getModel().get(SEASON));

		mav.addObject(FIXTURES, fixtures);
		mav.addObject("sendEmail", sendEmail);
		
		return mav;
	}
	
	@Transactional
	@RequestMapping(value = "/admin/editReport.html", method = RequestMethod.GET)
	public ModelAndView editReport(@RequestParam (required = false) String uuid,
			@ModelAttribute("playerFixtureInfoList") PlayerFixtureInfoList playerFixtureInfoList,
			@RequestParam (required = false) String teamUuid,
			@RequestParam (required = false) String seasonKey,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		log.debug("Editing Players for [" + getPrincipal() + "][" + uuid + "]");

		mav = getFilterObjects(ADMIN_REPORT, teamUuid, false, seasonKey, request);
		
		Fixture fixture = fixtureService.findFixtureByUuid(uuid);
		playerFixtureInfoList.setPlayerFixtureInfoList(fixtureService.findPlayerInfoByFixture(fixture, teamUuid, seasonKey));
		
		mav.addObject(FIXTURE, fixture);
		mav.addObject("playerFixtureInfoList", playerFixtureInfoList);
		mav.addObject("ratings", RATINGS);
		
		return mav;
	}
	
	@Transactional
	@RequestMapping(value = "/admin/editReport.html", method = RequestMethod.POST)
	public ModelAndView editReport(@RequestParam (required = false) String uuid,
			@Valid PlayerFixtureInfoList playerFixtureInfoList,
			BindingResult result,
			@RequestParam (required = false) String teamUuid,
			@RequestParam (required = false) String seasonKey,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		log.debug("Editing Report for [" + getPrincipal() + "][" + uuid + "]");

		mav = getFilterObjects(ADMIN_REPORT, teamUuid, false, seasonKey, request);

		Fixture fixture = fixtureService.findFixtureByUuid(uuid);
		playerFixtureInfoList.setFixture(fixture);
		playerFixtureInfoListValidator.validate(playerFixtureInfoList, result);
		
		if (result.hasErrors()) {
			mav = getFilterObjects(ADMIN_REPORT, teamUuid, false, seasonKey, request);
			
			mav.addObject(FIXTURE, fixture);
			mav.addObject("playerFixtureInfoList", playerFixtureInfoList);
			
			return mav;
		}
		fixtureService.updateFixtureReport(fixture, playerFixtureInfoList.getPlayerFixtureInfoList());
		mav.setViewName("redirect:/admin/reports.html?uuid=" + teamUuid + "&seasonKey=" + seasonKey);
		
		return mav;
	}
	
	@Transactional
	@RequestMapping(value = "/admin/sendFixtureEmail.html", method = RequestMethod.GET)
	public ModelAndView sendFixtureEmail(@RequestParam (required = false) String uuid,
			@RequestParam (required = false) String teamUuid,
			@RequestParam (required = false) String seasonKey,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		log.debug("Sending Fixture Email for [" + getPrincipal() + "][" + uuid + "]");

		emailService.sendFixtureEmailAsync(uuid, false);
		
		mav = getFilterObjects(ADMIN_REPORT, teamUuid, false, seasonKey, request);
		mav.setViewName("redirect:/admin/reports.html?uuid=" + teamUuid + "&seasonKey=" + seasonKey + "&sendEmail=true");
		
		return mav;
	}
}
