package com.niulbird.clubmgr.bfc.controller.admin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.niulbird.clubmgr.db.model.Player;
import com.niulbird.clubmgr.db.model.Season;
import com.niulbird.clubmgr.db.model.Team;
import com.niulbird.clubmgr.db.model.TeamSeasonMap;
import com.niulbird.clubmgr.db.service.PlayerService;
import com.niulbird.clubmgr.db.service.RecordNotFound;
import com.niulbird.clubmgr.db.service.TeamService;

@Controller
public class SquadController extends AdminBaseController {
	private static final Logger log = Logger.getLogger(SquadController.class);

	private static final String ADMIN_SQUADS = "admin_squads";
	private static final String PLAYERS_ACTIVE = "players_active";
	private static final String PLAYERS_INACTIVE = "players_inactive";
	
	@Autowired
	PlayerService playerService;
	
	@Autowired
	TeamService teamService;

	@Transactional
	@RequestMapping(value = "/admin/squads.html")
	public ModelAndView players(@RequestParam (required = false) String uuid,
			@RequestParam (required = false) String seasonKey,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		Team team;
		List<Player> activePlayers = new ArrayList<Player>();
		List<Player> inactivePlayers = new ArrayList<Player>();
		
		log.debug("Getting Squad for [" + getPrincipal() + "][" + uuid + "][" + seasonKey + "]");
		
		mav = getFilterObjects(ADMIN_SQUADS, uuid, false, seasonKey, request);
		
		if (StringUtils.isNotBlank(uuid)) {
			team = teamService.findByUuid(uuid);
		} else {
			team = (Team)mav.getModel().get(TEAM);
		}
		
		if (StringUtils.isBlank(seasonKey)) {
			seasonKey = ((Season)mav.getModel().get(SEASON)).getSeasonKey();
		}
		TeamSeasonMap teamSeasonMap = teamService.findTeamSeasonMap(team.getTeamKey(), seasonKey);
		List<Player> teamPlayers = playerService.findByTeam(team);
		
		
		for (Player player : teamPlayers) {
			if (player.getTeamSeasonMaps().contains(teamSeasonMap)) {
				activePlayers.add(player);
			} else {
				inactivePlayers.add(player);
			}
		}

		mav = getFilterObjects(ADMIN_SQUADS, uuid, false, seasonKey, request);
		mav.addObject(PLAYERS_ACTIVE, activePlayers);
		mav.addObject(PLAYERS_INACTIVE, inactivePlayers);
		
		return mav;
	}
	
	@Transactional
	@RequestMapping(value = "/admin/addSquad.html")
	public ModelAndView addSquad(@RequestParam (required = false) String uuid,
			@RequestParam (required = false) String teamUuid,
			@RequestParam (required = false) String seasonKey,
			HttpServletRequest request) {
		log.debug("Adding to Squad for [" + getPrincipal() + "][" + teamUuid + "][" + uuid + "]");
		ModelAndView mav = new ModelAndView();
		
		Team team = teamService.findByUuid(teamUuid);
		TeamSeasonMap teamSeasonMap = teamService.findTeamSeasonMap(team.getTeamKey(), seasonKey);
		
		Player player = playerService.findByUuid(uuid);
		player.getTeamSeasonMaps().add(teamSeasonMap);
		try {
			playerService.update(player);
		} catch (RecordNotFound rnf) {
			log.error("Error updating player: " + rnf.getMessage(), rnf);
		}
		
		mav.setViewName("redirect:/admin/squads.html?uuid=" + teamUuid + "&seasonKey=" + seasonKey);
		
		return mav;
	}
}
