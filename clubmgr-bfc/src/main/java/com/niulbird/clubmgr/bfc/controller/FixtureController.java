package com.niulbird.clubmgr.bfc.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.niulbird.clubmgr.bfc.command.FixtureData;
import com.niulbird.clubmgr.bfc.command.FixtureSummary;
import com.niulbird.clubmgr.db.model.Fixture;
import com.niulbird.clubmgr.db.model.PlayerFixtureInfo;
import com.niulbird.clubmgr.db.model.Status;
import com.niulbird.clubmgr.db.service.FixtureService;

@Controller
public class FixtureController extends BaseController {
	private static final Logger log = LogManager.getLogger();
	
	private static final String FIXTURE = "fixture";
	private static final String FIXTURE_SUMMARY = "fixtureSummary";
	private static final String PLAYER_FIXTURE_INFO = "playerFixtureInfo";
	private static final String PLAYER_FIXTURE_INFO_LIST = "playerFixtureInfoList";
	private static final String STATUS_UPDATED = "statusUpdated";
	
	@Autowired
	FixtureService fixtureService;

	@Transactional
	@RequestMapping(value = "/fixture.html", method = RequestMethod.GET)
	public ModelAndView fixture(@RequestParam (required = true) String uuid,
			@RequestParam (required = false) String player,
			@RequestParam (required = false) String updatePlayer,
			@RequestParam (required = false) String status) {
		List<PlayerFixtureInfo> playerFixtureInfoList = new ArrayList<PlayerFixtureInfo>();
		PlayerFixtureInfo playerFixtureInfo = null;
		boolean statusUpdated = false;
		log.debug("Viewing Fixture for [fixture=" + uuid + "][playerFixtureInfo=" + player + "][" + status + "]");
		
		Fixture fixture = fixtureService.findFixtureByUuid(uuid);
		
		if (StringUtils.isNotEmpty(player)) {
			playerFixtureInfo = fixtureService.findByUuid(player);
			if (StringUtils.isNotEmpty(status)) {
				if (StringUtils.isNotEmpty(updatePlayer)) {
					PlayerFixtureInfo updatePlayerFixtureInfo = fixtureService.findByUuid(updatePlayer);
					updatePlayerFixtureInfo.setStatus(Status.valueOf(status));
					fixtureService.updatePlayerInfo(updatePlayerFixtureInfo);
					log.debug("Updated Fixture [" + fixture.getTeam() + "][" + updatePlayerFixtureInfo.getPlayer().getFirstName() + " " 
							+ updatePlayerFixtureInfo.getPlayer().getLastName() + "]");
				} else {
					playerFixtureInfo.setStatus(Status.valueOf(status));
					fixtureService.updatePlayerInfo(playerFixtureInfo);
					statusUpdated = true;
					log.debug("Updated Fixture [" + fixture.getTeam() + "][" + playerFixtureInfo.getPlayer().getFirstName() + " " 
							+ playerFixtureInfo.getPlayer().getLastName() + "]");
				}
			}
		}
		
		playerFixtureInfoList = fixtureService.findByFixture(fixture);
		
		FixtureSummary fixtureSummary = getFixtureSummary(playerFixtureInfoList);

		ModelAndView mav = setView(FIXTURE, messageSource.getMessage("fixture.title", null, null));
		mav.addObject(FIXTURE, fixture);
		mav.addObject(FIXTURE_SUMMARY, fixtureSummary);
		mav.addObject(PLAYER_FIXTURE_INFO, playerFixtureInfo);
		mav.addObject(PLAYER_FIXTURE_INFO_LIST, playerFixtureInfoList);
		mav.addObject(STATUS_UPDATED, statusUpdated);
		
		FixtureData fixtureData = new FixtureData(uuid, player, null);
		mav.addObject("fixtureData", fixtureData);
		
		return mav;
	}
	
	@Transactional
	@RequestMapping(value = "/fixture.html", method = RequestMethod.POST)
	public ModelAndView fixtureComment(@ModelAttribute("fixtureData") FixtureData fixtureData) {
		List<PlayerFixtureInfo> playerFixtureInfoList = new ArrayList<PlayerFixtureInfo>();
		PlayerFixtureInfo playerFixtureInfo = null;
		log.debug("Viewing Fixture for [fixture=" + fixtureData.getUuid() + "][playerFixtureInfo=" + fixtureData.getPlayer() + "][" + fixtureData.getComment() + "]");
		
		Fixture fixture = fixtureService.findFixtureByUuid(fixtureData.getUuid());
		
		if (StringUtils.isNotEmpty(fixtureData.getPlayer()) && StringUtils.isNotEmpty(fixtureData.getComment())) {
			playerFixtureInfo = fixtureService.findByUuid(fixtureData.getPlayer());
			playerFixtureInfo.setComment(fixtureData.getComment());
			fixtureService.updatePlayerInfo(playerFixtureInfo);
		}
		
		playerFixtureInfoList = fixtureService.findByFixture(fixture);
		
		FixtureSummary fixtureSummary = getFixtureSummary(playerFixtureInfoList);

		ModelAndView mav = setView(FIXTURE, messageSource.getMessage("fixture.title", null, null));
		mav.addObject(FIXTURE, fixture);
		mav.addObject(FIXTURE_SUMMARY, fixtureSummary);
		mav.addObject(PLAYER_FIXTURE_INFO, playerFixtureInfo);
		mav.addObject(PLAYER_FIXTURE_INFO_LIST, playerFixtureInfoList);
		
		return mav;
	}

	@Transactional
	@RequestMapping(value = "/fixture.png", method = RequestMethod.GET)
	public void fixturePng(@RequestParam (required = false) String player,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PlayerFixtureInfo playerFixtureInfo = null;
		log.debug("Viewing Fixture Email Tracker for [playerFixtureInfo=" + player + "]");
		
		if (StringUtils.isNoneEmpty(player)) {
			playerFixtureInfo = fixtureService.findByUuid(player);
			if (playerFixtureInfo.getViewed() == null) {
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.HOUR_OF_DAY, -7);
				playerFixtureInfo.setViewed(new java.sql.Time(calendar.getTimeInMillis()));
				fixtureService.updatePlayerInfo(playerFixtureInfo);
			}
		}
		InputStream in = request.getSession().getServletContext().getResourceAsStream("/static/images/1by1.png");
	    response.setContentType(MediaType.IMAGE_PNG_VALUE);
	    IOUtils.copy(in, response.getOutputStream());
	}
	
	private FixtureSummary getFixtureSummary(List<PlayerFixtureInfo> playerFixtureInfoList) {
		FixtureSummary fixtureSummary = new FixtureSummary();
		
		for (PlayerFixtureInfo playerFixtureInfoLocal : playerFixtureInfoList) {
			if (playerFixtureInfoLocal.getStatus() == Status.YES) {
				fixtureSummary.addYes();
			} else if (playerFixtureInfoLocal.getStatus() == Status.MAYBE) {
				fixtureSummary.addMaybe();
			} else if (playerFixtureInfoLocal.getStatus() == Status.NO) {
				fixtureSummary.addNo();
			} else if (playerFixtureInfoLocal.getStatus() == Status.PENDING) {
				fixtureSummary.addPending();
			}
		}
		return fixtureSummary;
	}
}
