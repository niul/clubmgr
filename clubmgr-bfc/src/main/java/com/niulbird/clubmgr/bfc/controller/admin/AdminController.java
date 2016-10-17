package com.niulbird.clubmgr.bfc.controller.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.niulbird.clubmgr.bfc.controller.BaseController;
import com.niulbird.clubmgr.db.model.Player;
import com.niulbird.clubmgr.db.model.Team;
import com.niulbird.clubmgr.db.model.User;
import com.niulbird.clubmgr.db.service.PlayerService;
import com.niulbird.clubmgr.db.service.RecordNotFound;
import com.niulbird.clubmgr.db.service.TeamService;
import com.niulbird.clubmgr.db.service.UserService;

@Controller
public class AdminController extends BaseController {
	private static final Logger log = Logger.getLogger(AdminController.class);

	private static final String ADMIN = "ADMIN";
	private static final String ADMIN_ADD_PLAYER = "admin_add_player";
	private static final String ADMIN_EDIT_PLAYER = "admin_edit_player";
	private static final String ADMIN_PLAYERS = "admin_players";
	private static final String ALL_UUID = "all_uuid";
	private static final String PLAYERS = "players";
	private static final String PLAYER = "player";
	private static final String TEAMS = "teams";
	private static final String TEAM = "team";
	private static final String TEAM_UUID = "team_uuid";
	private static final String USER = "user";

	
	@Autowired
	PlayerService playerService;
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/admin/players.html")
	public ModelAndView players(@RequestParam (required = false) String uuid, 
			HttpServletRequest request) {
		log.debug("Getting Players for [" + getPrincipal() + "][" + uuid + "]");
		
		return getFilteredPlayers(ADMIN_PLAYERS, uuid, request);
	}
	
	@RequestMapping(value = "/admin/addPlayer.html", method = RequestMethod.GET)
	public ModelAndView addPlayer(@ModelAttribute("player") Player player,
			@RequestParam (required = false) String uuid, 
			HttpServletRequest request) {
		log.debug("Adding Players for [" + getPrincipal() + "][" + uuid + "]");

		ModelAndView mav = getFilteredPlayers(ADMIN_ADD_PLAYER, uuid, request);
		mav.addObject(PLAYER, player);
		
		return mav;
	}
	
	@RequestMapping(value = "/admin/addPlayer.html", method = RequestMethod.POST)
	public ModelAndView addPlayer(@Valid Player player,
			BindingResult result,
			@RequestParam (required = false) String uuid, 
			HttpServletRequest request) {
		log.debug("Adding Players for [" + getPrincipal() + "][" + uuid + "]");

		ModelAndView mav = new ModelAndView();
		
		if (result.hasErrors()) {
			mav = getFilteredPlayers(ADMIN_ADD_PLAYER, uuid, request);
			mav.addObject(PLAYER, player);
			return mav;
		}
		
		User user = (User) request.getSession().getAttribute(USER);
		
		if (playerService.findByClubAndEmail(user.getClub(), player.getEmail()) != null) {
			mav = getFilteredPlayers(ADMIN_ADD_PLAYER, uuid, request);
			mav.addObject(PLAYER, player);
			result.rejectValue("email", "error.player", messageSource.getMessage("Email.player.exists", null, null));
			return mav;
		}
		
		player.setClub(user.getClub());
		player.setUuid(UUID.randomUUID());
		player.setEnabled(true);
		player.setCreated(new Date());
		
		Team team = teamService.findByUuid(uuid);
		if (team != null) {
			List<Team> teams = new ArrayList<Team>();
			teams.add(team);
			player.setTeams(teams);
		}
		
		playerService.create(player);
		
		mav.setViewName("redirect:/admin/players.html?uuid=" + uuid);
		
		return mav;
	}
	
	@RequestMapping(value = "/admin/editPlayer.html", method = RequestMethod.GET)
	public ModelAndView addPlayer(@ModelAttribute("player") Player player,
			@RequestParam (required = false) String uuid, 
			@RequestParam (required = false) String teamUuid, 
			HttpServletRequest request) {
		log.debug("Editing Players for [" + getPrincipal() + "][" + uuid + "]");

		ModelAndView mav = getFilteredPlayers(ADMIN_EDIT_PLAYER, teamUuid, request);
		
		player = playerService.findByUuid(uuid);
		mav.addObject(PLAYER, player);
		
		request.getSession().setAttribute(TEAM_UUID, teamUuid);
		
		return mav;
	}
	
	@RequestMapping(value = "/admin/editPlayer.html", method = RequestMethod.POST)
	public ModelAndView editPlayer(@Valid Player player,
			BindingResult result,
			@RequestParam (required = false) String uuid, 
			HttpServletRequest request) {
		log.debug("Adding Players for [" + getPrincipal() + "][" + uuid + "]");

		ModelAndView mav = new ModelAndView();
		
		if (result.hasErrors()) {
			mav = getFilteredPlayers(ADMIN_EDIT_PLAYER, (String)request.getSession().getAttribute(TEAM_UUID), request);
			mav.addObject(PLAYER, player);
			return mav;
		}

		try {
			player.setEnabled(true);
			playerService.update(player);
		} catch (RecordNotFound rnf) {
			log.error("Cannot find Player: " + rnf.getMessage(), rnf);
		}
		
		mav.setViewName("redirect:/admin/players.html?uuid=" + (String)request.getSession().getAttribute(TEAM_UUID));
		
		return mav;
	}
	
	@RequestMapping(value = "/admin/deletePlayer.html")
	public ModelAndView deletePlayer(@RequestParam (required = false) String uuid, 
			@RequestParam (required = false) String teamUuid, 
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		log.debug("Deleting Player for [" + getPrincipal() + "][" + uuid + "]");
		
		Player player = playerService.findByUuid(uuid);
		
		if (player != null) {
			player.setEnabled(false);
			try {
				playerService.update(player);
			} catch (RecordNotFound rnf) {
				log.error("Cannot find Player: " + rnf.getMessage(), rnf);
			}
		}
		
		mav.setViewName("redirect:/admin/players.html?uuid=" + teamUuid);
		
		return mav;
	}
	
	private ModelAndView getFilteredPlayers(String page,
			String teamUuid,
			HttpServletRequest request) {
		ModelAndView model = setView(page, messageSource.getMessage("admin.title", null, null));
		log.debug("Getting page[" + page + "] and team[" + teamUuid +"]");
		
		
		Boolean isTeamSelected = false;
		String username = getPrincipal();
		User user = userService.getUser(username);
		List<Team> teams = new ArrayList<Team>();
		if (request.isUserInRole(ADMIN)) {
			teams = user.getClub().getTeams();
		} else {
			teams = user.getTeams();
		}
		
		model.addObject(USER, user);
		model.addObject(TEAMS, teams);

		UUID allUuid = UUID.randomUUID();
		model.addObject(ALL_UUID, allUuid);
		
		Team selectedTeam = new Team();
		selectedTeam.setUuid(allUuid);
		selectedTeam.setName(messageSource.getMessage("admin.menu.filter.all", null, null));
		
		if (StringUtils.isNotEmpty(teamUuid)) {
			for (Team team : teams) {
				if (teamUuid.equalsIgnoreCase(team.getUuid().toString())) {
					selectedTeam = team;
					isTeamSelected = true;
				}
			}
		}
		model.addObject(TEAM, selectedTeam);
		log.debug("Team: " + selectedTeam.getName());
		
		if (isTeamSelected) {
			model.addObject(PLAYERS, playerService.findByTeam(selectedTeam));
		} else {
			model.addObject(PLAYERS, playerService.findByClub(user.getClub()));
		}
		
		request.getSession().setAttribute(USER, user);
		
		return model;
	}

	private String getPrincipal() {
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
