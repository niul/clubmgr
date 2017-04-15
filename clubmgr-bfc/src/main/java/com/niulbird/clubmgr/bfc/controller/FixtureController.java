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
import org.apache.log4j.Logger;
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
import com.niulbird.clubmgr.db.model.Fixture;
import com.niulbird.clubmgr.db.model.PlayerFixtureInfo;
import com.niulbird.clubmgr.db.model.Status;
import com.niulbird.clubmgr.db.service.FixtureService;

@Controller
public class FixtureController extends BaseController {
	private static final Logger log = Logger.getLogger(FixtureController.class);
	
	private static final String FIXTURE = "fixture";
	private static final String PLAYER_FIXTURE_INFO = "playerFixtureInfo";
	private static final String PLAYER_FIXTURE_INFO_LIST = "playerFixtureInfoList";
	
	@Autowired
	FixtureService fixtureService;

	@Transactional
	@RequestMapping(value = "/fixture.html", method = RequestMethod.GET)
	public ModelAndView fixture(@RequestParam (required = true) String uuid,
			@RequestParam (required = false) String player,
			@RequestParam (required = false) String status) {
		List<PlayerFixtureInfo> playerFixtureInfoList = new ArrayList<PlayerFixtureInfo>();
		PlayerFixtureInfo playerFixtureInfo = null;
		log.debug("Viewing Fixture for [" + uuid + "][" + player + "][" + status + "]");
		
		Fixture fixture = fixtureService.findFixtureByUuid(uuid);
		
		if (StringUtils.isNotEmpty(player) && StringUtils.isNotEmpty(status)) {
			playerFixtureInfo = fixtureService.findByUuid(player);
			playerFixtureInfo.setStatus(Status.valueOf(status));
			fixtureService.updatePlayerInfo(playerFixtureInfo);
		}
		
		playerFixtureInfoList = fixtureService.findByFixture(fixture);

		ModelAndView mav = setView(FIXTURE, messageSource.getMessage("fixture.title", null, null));
		mav.addObject(FIXTURE, fixture);
		mav.addObject(PLAYER_FIXTURE_INFO, playerFixtureInfo);
		mav.addObject(PLAYER_FIXTURE_INFO_LIST, playerFixtureInfoList);
		
		FixtureData fixtureData = new FixtureData(uuid, player, null);
		mav.addObject("fixtureData", fixtureData);
		
		return mav;
	}
	
	@Transactional
	@RequestMapping(value = "/fixture.html", method = RequestMethod.POST)
	public ModelAndView fixtureComment(@ModelAttribute("fixtureData") FixtureData fixtureData) {
		List<PlayerFixtureInfo> playerFixtureInfoList = new ArrayList<PlayerFixtureInfo>();
		PlayerFixtureInfo playerFixtureInfo = null;
		log.debug("Viewing Fixture for [" + fixtureData.getUuid() + "][" + fixtureData.getPlayer() + "][" + fixtureData.getComment() + "]");
		
		Fixture fixture = fixtureService.findFixtureByUuid(fixtureData.getUuid());
		
		if (StringUtils.isNotEmpty(fixtureData.getPlayer()) && StringUtils.isNotEmpty(fixtureData.getComment())) {
			playerFixtureInfo = fixtureService.findByUuid(fixtureData.getPlayer());
			playerFixtureInfo.setComment(fixtureData.getComment());
			fixtureService.updatePlayerInfo(playerFixtureInfo);
		}
		
		playerFixtureInfoList = fixtureService.findByFixture(fixture);

		ModelAndView mav = setView(FIXTURE, messageSource.getMessage("fixture.title", null, null));
		mav.addObject(FIXTURE, fixture);
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
		log.debug("Viewing Fixture Email for [" + player + "]");
		
		if (StringUtils.isNoneEmpty(player)) {
			playerFixtureInfo = fixtureService.findByUuid(player);
			if (playerFixtureInfo.getViewed() == null) {
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.HOUR_OF_DAY, -3);
				playerFixtureInfo.setViewed(new java.sql.Time(calendar.getTimeInMillis()));
				fixtureService.updatePlayerInfo(playerFixtureInfo);
			}
		}
		InputStream in = request.getSession().getServletContext().getResourceAsStream("/static/images/1by1.png");
	    response.setContentType(MediaType.IMAGE_PNG_VALUE);
	    IOUtils.copy(in, response.getOutputStream());
	}
}
