package com.niulbird.clubmgr.bfc.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.niulbird.clubmgr.bfc.command.LoginData;
import com.niulbird.clubmgr.bfc.controller.BaseController;
import com.niulbird.clubmgr.db.service.UserService;

@Controller
public class LoginController extends BaseController {
	private static final Logger log = Logger.getLogger(LoginController.class);

	private static final String LOGIN = "login";

	@Autowired
	UserService userService;
	

	@RequestMapping(value = "/login.html", method = RequestMethod.GET)
	public ModelAndView login(@ModelAttribute("loginData") LoginData loginData,
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {
		ModelAndView model = setView(LOGIN, null);
		log.debug("Login page request: " + loginData.getUsername());
		if (error != null) {
			log.debug("Error: " + error);
			model.addObject("error", messageSource.getMessage("login.error.invalid.credentials", null, null));
		}

		if (logout != null) {
			model.addObject("msg", messageSource.getMessage("login.loggedout", null, null));
		}
		model.addObject("loginData", loginData);

		return model;
	}

	@RequestMapping(value = "/logout.html", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login.html?logout";
	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {
		ModelAndView mav = setView(ERROR, null);
		mav.addObject("errMsg", messageSource.getMessage("error.access_denied", null, null));

		// check if user is login
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			mav.addObject("username", userDetail.getUsername());
		}

		return mav;

	}
}
