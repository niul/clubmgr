package com.niulbird.clubmgr.bfc.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.niulbird.clubmgr.email.service.EmailService;

@Controller
public class FixtureController extends BaseController {
	private static final Logger log = LogManager.getLogger();

	private static final String FIXTURE = "fixture";
	private static final String FIXTURE_SUMMARY = "fixtureSummary";
	private static final String PLAYER_FIXTURE_INFO = "playerFixtureInfo";
	private static final String PLAYER_FIXTURE_INFO_LIST = "playerFixtureInfoList";
	private static final String STATUS_UPDATED = "statusUpdated";
	private static final String IMAGE_PATH = "/images/1by1.png";

	@Autowired
	EmailService emailService;
	
	@Autowired
	FixtureService fixtureService;

	@Transactional
	@RequestMapping(value = "/fixture.html", method = RequestMethod.GET)
	public ModelAndView fixture(@RequestParam (name = "uuid", required = true) String uuid,
			@RequestParam (name = "player", required = false) String player,
			@RequestParam (name = "updatePlayer", required = false) String updatePlayer,
			@RequestParam (name = "status", required = false) String status,
			HttpServletRequest httpServletRequest) {
		List<PlayerFixtureInfo> playerFixtureInfoList = new ArrayList<PlayerFixtureInfo>();
		PlayerFixtureInfo playerFixtureInfo = null;
		boolean statusUpdated = false;
		log.debug("Viewing Fixture for [fixture=" + uuid + "][playerFixtureInfo=" + player + "][" + status + "]");

		Fixture fixture = fixtureService.findFixtureByUuid(uuid);

		if (!player.isEmpty()) {
			playerFixtureInfo = fixtureService.findByUuid(player);
			if (!status.isEmpty()) {
				if (!updatePlayer.isEmpty()) {
					PlayerFixtureInfo updatePlayerFixtureInfo = fixtureService.findByUuid(updatePlayer);
					updatePlayerFixtureInfo.setStatus(Status.valueOf(status));
					fixtureService.updatePlayerInfo(updatePlayerFixtureInfo);
					log.debug("Updated Fixture [" + fixture.getTeam() + "][" + updatePlayerFixtureInfo.getPlayer().getFirstName() + " "
							+ updatePlayerFixtureInfo.getPlayer().getLastName() + "]");
				} else {
					Status oldStatus = playerFixtureInfo.getStatus();
					playerFixtureInfo.setStatus(Status.valueOf(status));
					fixtureService.updatePlayerInfo(playerFixtureInfo);
					statusUpdated = true;
					log.debug("Updated Fixture [" + fixture.getTeam() + "][" + playerFixtureInfo.getPlayer().getFirstName() + " "
							+ playerFixtureInfo.getPlayer().getLastName() + "]");

					// Check if Status has changed and within 1 Day of Fixture Date.
					if (oldStatus != Status.valueOf(status) &&
							System.currentTimeMillis() >= (fixture.getDate().getTime() - (TimeUnit.DAYS.toMillis(1)))) {
						// Send email to manager
						log.debug("Sending late availability message: " + System.currentTimeMillis() + "|" + (fixture.getDate().getTime() - (24 * 60 * 60 * 1000)));
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("playerFixtureInfo", playerFixtureInfo);
						map.put("oldStatus", oldStatus);
						emailService.sendAvailabilityUpdateEmail(map);					}
				}
			}
		}

		playerFixtureInfoList = fixtureService.findByFixture(fixture);

		FixtureSummary fixtureSummary = getFixtureSummary(playerFixtureInfoList);

		ModelAndView mav = setView(FIXTURE, messageSource.getMessage("fixture.title", null, null), httpServletRequest);
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
	public ModelAndView fixtureComment(@ModelAttribute("fixtureData") FixtureData fixtureData,
			HttpServletRequest httpServletRequest) {
		List<PlayerFixtureInfo> playerFixtureInfoList = new ArrayList<PlayerFixtureInfo>();
		PlayerFixtureInfo playerFixtureInfo = null;
		log.debug("Viewing Fixture for [fixture=" + fixtureData.getUuid() + "][playerFixtureInfo=" + fixtureData.getPlayer() + "][" + fixtureData.getComment() + "]");

		Fixture fixture = fixtureService.findFixtureByUuid(fixtureData.getUuid());

		if (!fixtureData.getPlayer().isEmpty() && !fixtureData.getComment().isEmpty()) {
			playerFixtureInfo = fixtureService.findByUuid(fixtureData.getPlayer());
			playerFixtureInfo.setComment(fixtureData.getComment());
			fixtureService.updatePlayerInfo(playerFixtureInfo);
			log.debug("Updating Fixture Comment for [" + playerFixtureInfo.getPlayer().getEmail() + "][" + fixtureData.getComment() + "]");
		}

		playerFixtureInfoList = fixtureService.findByFixture(fixture);

		FixtureSummary fixtureSummary = getFixtureSummary(playerFixtureInfoList);

		ModelAndView mav = setView(FIXTURE, messageSource.getMessage("fixture.title", null, null), httpServletRequest);
		mav.addObject(FIXTURE, fixture);
		mav.addObject(FIXTURE_SUMMARY, fixtureSummary);
		mav.addObject(PLAYER_FIXTURE_INFO, playerFixtureInfo);
		mav.addObject(PLAYER_FIXTURE_INFO_LIST, playerFixtureInfoList);

		return mav;
	}

	@Transactional
	@RequestMapping(value = "/fixture.png", method = RequestMethod.GET)
	public void fixturePng(@RequestParam (name = "player", required = false) String player,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PlayerFixtureInfo playerFixtureInfo = null;
		log.debug("Viewing Fixture Email Tracker for [playerFixtureInfo=" + player + "]");

		if (!player.isEmpty()) {
			playerFixtureInfo = fixtureService.findByUuid(player);
			if (playerFixtureInfo.getViewed() == null) {
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.HOUR_OF_DAY, -7);
				playerFixtureInfo.setViewed(new java.sql.Time(calendar.getTimeInMillis()));
				fixtureService.updatePlayerInfo(playerFixtureInfo);
				log.debug("Setting Email Viewed for [" + playerFixtureInfo.getPlayer().getEmail() + "][" + player + "]");
			}
		}
		try {
			request.getRequestDispatcher(IMAGE_PATH).forward(request, response);
		} catch (ServletException | IOException e) {
			log.error("Error forwarding to image path.", e);
		}
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
