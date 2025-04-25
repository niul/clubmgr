package com.niulbird.clubmgr.bfc.controller.admin;


import jakarta.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.niulbird.clubmgr.bfc.command.EmailData;
import com.niulbird.clubmgr.db.service.TeamService;
import com.niulbird.clubmgr.email.service.EmailService;

@Controller
public class EmailController extends AdminBaseController {
	private static final Logger log = LogManager.getLogger();

	private static final String ADMIN_EMAIL = "admin_email";
    
	@Autowired
	EmailService emailService;
	
	@Autowired
	TeamService teamService;

	@Transactional
	@RequestMapping(value = "/admin/email.html")
	public ModelAndView email(@ModelAttribute("email") EmailData email,
			@RequestParam (required = false) String uuid,
			@RequestParam (required = false) String seasonKey,
			@RequestParam (required = false) boolean sendEmail,
			HttpServletRequest httpServletRequest) {
		ModelAndView mav = new ModelAndView();
		
		log.debug("Email: [" + email.getSubject() + "][" + email.getMessage() + "]");
		
		mav = getFilterObjects(ADMIN_EMAIL, uuid, false, seasonKey, httpServletRequest);
		
		return mav;
	}
}
