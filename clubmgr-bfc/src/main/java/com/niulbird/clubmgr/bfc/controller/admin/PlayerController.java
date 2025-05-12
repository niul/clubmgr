package com.niulbird.clubmgr.bfc.controller.admin;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.niulbird.clubmgr.bfc.command.PasswordChangeData;
import com.niulbird.clubmgr.db.model.Player;
import com.niulbird.clubmgr.db.model.Team;
import com.niulbird.clubmgr.db.model.User;
import com.niulbird.clubmgr.db.service.PlayerService;
import com.niulbird.clubmgr.db.service.PositionService;
import com.niulbird.clubmgr.db.service.RecordNotFound;
import com.niulbird.clubmgr.db.service.TeamService;

@Controller
public class PlayerController extends AdminBaseController {
	private static final Logger log = LogManager.getLogger();

	private static final String ADMIN_ADD_PLAYER = "admin/add_player";
	private static final String ADMIN_EDIT_PLAYER = "admin/edit_player";
	private static final String ADMIN_PLAYERS = "admin/players";
	private static final String PASSWORD_CHANGE = "password_change";
	private static final String POSITIONS = "positions";

	@Autowired
	PositionService positionService;
	
	@Autowired
	PlayerService playerService;
	
	@Autowired
	TeamService teamService;
	
	@RequestMapping(value = "/admin/players.html")
	public ModelAndView players(@RequestParam (name = "uuid", required = false) String uuid, 
			HttpServletRequest httpServletRequest) {
		log.debug("Getting Players for [" + getPrincipal() + "][" + uuid + "]");
		
		ModelAndView mav = new ModelAndView();
		
		String username = getPrincipal();
		User user = userService.getUser(username);
		httpServletRequest.getSession().setAttribute(USER, user);
		
		if (user.getChangePassword()) {
			mav = setView(PASSWORD_CHANGE, messageSource.getMessage("password_reset.title", null, null), httpServletRequest);
			mav.addObject("passwordChangeData", new PasswordChangeData());
			return mav;
		}
		

		mav = getFilterObjects(ADMIN_PLAYERS, uuid, true, null, httpServletRequest);
		Team selectedTeam = (Team)mav.getModel().get(TEAM);
		if (selectedTeam.getName().equalsIgnoreCase(messageSource.getMessage("admin.menu.filter.all", null, null))) {
			mav.addObject(PLAYERS, playerService.findByClub(user.getClub()));
		} else {
			mav.addObject(PLAYERS, playerService.findByTeam(selectedTeam));
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/admin/addPlayer.html", method = RequestMethod.GET)
	public ModelAndView addPlayer(@ModelAttribute("player") Player player,
			@RequestParam (name = "uuid", required = false) String uuid, 
			HttpServletRequest httpServletRequest) {
		log.debug("Adding Players for [" + getPrincipal() + "][" + uuid + "]");

		ModelAndView mav = getFilterObjects(ADMIN_ADD_PLAYER, uuid, true, null, httpServletRequest);
		mav.addObject(PLAYER, player);
		mav.addObject(POSITIONS, positionService.findAll());
		
		return mav;
	}
	
	@RequestMapping(value = "/admin/addPlayer.html", method = RequestMethod.POST)
	public ModelAndView addPlayer(@Valid Player player,
			BindingResult result,
			@RequestParam (name = "teamUuid", required = false) String teamUuid, 
			HttpServletRequest httpServletRequest) {
		log.debug("Adding Players for [" + getPrincipal() + "][" + teamUuid + "][" + player.getFirstName() + " " + player.getLastName() + "][" 
			+ player.getEmail() + "][" + player.getPosition() + "]");

		ModelAndView mav = new ModelAndView();
		
		if (result.hasErrors()) {
			mav = getFilterObjects(ADMIN_ADD_PLAYER, teamUuid, true, null, httpServletRequest);
			mav.addObject(PLAYER, player);
			mav.addObject(POSITIONS, positionService.findAll());
			return mav;
		}
		
		User user = (User) httpServletRequest.getSession().getAttribute(USER);
		
		if (playerService.findByClubAndEmail(user.getClub(), player.getEmail()) != null) {
			mav = getFilterObjects(ADMIN_ADD_PLAYER, teamUuid, true, null, httpServletRequest);
			mav.addObject(PLAYER, player);
			result.rejectValue("email", "error.player", messageSource.getMessage("Email.player.exists", null, null));
			return mav;
		}
		
		player.setClub(user.getClub());
		player.setUuid(UUID.randomUUID());
		player.setEnabled(true);
		player.setCreated(new Date());
		player.setManager(false);
		
		if (player.getTeams().size() == 0 && teamUuid != null) {
			Team team = teamService.findByUuid(teamUuid);
			if (team != null) {
				Set<Team> teams = new HashSet<Team>();
				teams.add(team);
				player.setTeams(teams);
			}
		}
		
		playerService.create(player);
		
		mav.setViewName("redirect:/admin/players.html?uuid=" + teamUuid);
		
		return mav;
	}
	
	@RequestMapping(value = "/admin/editPlayer.html", method = RequestMethod.GET)
	public ModelAndView editPlayer(@ModelAttribute("player") Player player,
			@RequestParam (name = "uuid", required = false) String uuid, 
			@RequestParam (name = "teamUuid", required = false) String teamUuid, 
			HttpServletRequest request) {
		log.debug("Editing Players for [" + getPrincipal() + "][" + uuid + "]");

		ModelAndView mav = getFilterObjects(ADMIN_EDIT_PLAYER, teamUuid, true, null, request);
		
		player = playerService.findByUuid(uuid);
		mav.addObject(PLAYER, player);
		mav.addObject(POSITIONS, positionService.findAll());
		
		request.getSession().setAttribute(TEAM_UUID, teamUuid);
		
		return mav;
	}
	
	@RequestMapping(value = "/admin/editPlayer.html", method = RequestMethod.POST)
	public ModelAndView editPlayer(@Valid Player player,
			BindingResult result,
			@RequestParam (name = "uuid", required = false) String uuid, 
			HttpServletRequest request) {
		log.debug("Editing Players for [" + getPrincipal() + "][" + uuid + "][" + player.getFirstName() + " " + player.getLastName() + "][" + player.getEmail() + "]");

		ModelAndView mav = new ModelAndView();
		
		if (result.hasErrors()) {
			mav = getFilterObjects(ADMIN_EDIT_PLAYER, (String)request.getSession().getAttribute(TEAM_UUID), true, null, request);
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
	public ModelAndView deletePlayer(@RequestParam (name = "uuid", required = false) String uuid, 
			@RequestParam (name = "teamUuid", required = false) String teamUuid, 
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
}
