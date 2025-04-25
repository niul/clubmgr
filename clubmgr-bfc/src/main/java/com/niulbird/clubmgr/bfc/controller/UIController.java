package com.niulbird.clubmgr.bfc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UIController extends BaseController {

	private static final String ABOUT = "about";
	private static final String BOMBASTIC_MENS = "mens";
	private static final String BOMBASTIC_MENS_A = "mensA";
	private static final String BOMBASTIC_MENS_B = "mensB";
	private static final String BOMBASTIC_MENS_JURASSIC = "mensJurassic";
	private static final String BOMBASTIC_WOMENS = "womens";
	private static final String CALENDAR = "calendar";
	private static final String HOME = "home";
	private static final String SPONSORS = "sponsors";
	
	@RequestMapping(value = "/about.html")
	public ModelAndView about(HttpServletRequest httpServletRequest) {
		return setView(ABOUT, messageSource.getMessage("about.title", null, null), httpServletRequest);
	}
	
	@RequestMapping(value = "/calendar.html")
	public ModelAndView calendar(HttpServletRequest httpServletRequest) {
		return setView(CALENDAR, null, httpServletRequest);
	}
	
	@RequestMapping(value = "/")
	public ModelAndView root(HttpServletRequest httpServletRequest) {
		return setView(HOME, null, httpServletRequest);
	}
	
	@RequestMapping(value = "/index.html")
	public ModelAndView home(HttpServletRequest httpServletRequest) {
		return setView(HOME, null, httpServletRequest);
	}
	
	@RequestMapping(value = "/mens.html")
	public ModelAndView mens(HttpServletRequest httpServletRequest) {
		return setView(BOMBASTIC_MENS, messageSource.getMessage("mens.title", null, null), httpServletRequest);
	}
	
	@RequestMapping(value = "/mensA.html")
	public ModelAndView mensA(HttpServletRequest httpServletRequest) {
		return setView(BOMBASTIC_MENS_A, messageSource.getMessage("mens.A.title", null, null), httpServletRequest);
	}
	
	@RequestMapping(value = "/mensB.html")
	public ModelAndView mensB(HttpServletRequest httpServletRequest) {
		return setView(BOMBASTIC_MENS_B, messageSource.getMessage("mens.B.title", null, null), httpServletRequest);
	}
	
	@RequestMapping(value = "/mensJurassic.html")
	public ModelAndView mensJurassic(HttpServletRequest httpServletRequest) {
		return setView(BOMBASTIC_MENS_JURASSIC, messageSource.getMessage("mens.jurassic.title", null, null), httpServletRequest);
	}
	
	@RequestMapping(value = "/womens.html")
	public ModelAndView womens(HttpServletRequest httpServletRequest) {
		return setView(BOMBASTIC_WOMENS, messageSource.getMessage("womens.title", null, null), httpServletRequest);
	}
	
	@RequestMapping(value = "/sponsors.html")
	public ModelAndView sponsors(HttpServletRequest httpServletRequest) {
		return setView(SPONSORS, messageSource.getMessage("sponsors.title", null, null), httpServletRequest);
	}
	
	@RequestMapping(value = "/404.html")
	public ModelAndView pageNotFound(HttpServletRequest httpServletRequest) {
		ModelAndView mav = setView(ERROR, null, httpServletRequest);
		mav.addObject("errMsg", messageSource.getMessage("error.page_not_found", null, null));
		return mav;
	}
}
