package com.niulbird.clubmgr.bfc.controller.admin;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.niulbird.clubmgr.bfc.controller.BaseController;
import com.niulbird.clubmgr.db.model.Season;
import com.niulbird.clubmgr.db.model.Team;
import com.niulbird.clubmgr.db.model.User;
import com.niulbird.clubmgr.db.service.SeasonService;
import com.niulbird.clubmgr.db.service.UserService;

@Component
public abstract class AdminBaseController extends BaseController {
	private static final Logger log = LogManager.getLogger();

	protected static final String ADMIN = "ADMIN";
	protected static final String ALL_UUID = "all_uuid";
	protected static final String PLAYERS = "players";
	protected static final String PLAYER = "player";
	protected static final String SEASON = "season";
	protected static final String SEASONS = "seasons";
	protected static final String TEAM = "team";
	protected static final String TEAMS = "teams";
	protected static final String TEAM_UUID = "team_uuid";
	
	@Autowired
	SeasonService seasonService;
	
	@Autowired
	UserService userService;
	
	protected ModelAndView getFilterObjects(String page,
			String teamUuid,
			boolean addAllTeam,
			String seasonKey,
			HttpServletRequest httpServletRequest) {
		ModelAndView model = setView(page, messageSource.getMessage("admin.title", null, null), httpServletRequest);
		log.debug("Getting page[" + page + "] and team[" + teamUuid +"]");
		
		// Get the user and associate rule to determine Team list.
		String username = getPrincipal();
		User user = userService.getUser(username);
		Set<Team> teams;
		if (httpServletRequest.isUserInRole(ADMIN)) {
			teams = user.getClub().getTeams();
		} else {
			teams = user.getTeams();
		}
		
		model.addObject(USER, user);
		model.addObject(TEAMS, teams);

		UUID allUuid = UUID.randomUUID();
		model.addObject(ALL_UUID, allUuid);
		
		// Determine is a Team is selected.
		Team selectedTeam = new Team();
		if (addAllTeam) {
			selectedTeam.setUuid(allUuid);
			selectedTeam.setName(messageSource.getMessage("admin.menu.filter.all", null, null));
		} else {
			selectedTeam = teams.iterator().next();
		}
		
		if (StringUtils.isNotEmpty(teamUuid)) {
			for (Team team : teams) {
				if (teamUuid.equalsIgnoreCase(team.getUuid().toString())) {
					selectedTeam = team;
				}
			}
		}
		model.addObject(TEAM, selectedTeam);
		log.debug("Team: " + selectedTeam.getName());
		
		// Determine if a Season is selected.
		List<Season> seasons = seasonService.findAll();
		model.addObject(SEASONS, seasons);
		
		Season selectedSeason = seasons.get(0);
		
		if (StringUtils.isNotEmpty(seasonKey)) {
			for (Season season : seasons) {
				if (seasonKey.equalsIgnoreCase(season.getSeasonKey())) {
					selectedSeason = season;
				}
			}
		}
		model.addObject(SEASON, selectedSeason);
		log.debug("Season: " + selectedSeason.getName());
		
		httpServletRequest.getSession().setAttribute(USER, user);
		
		return model;
	}

	protected String getPrincipal() {
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}
}
