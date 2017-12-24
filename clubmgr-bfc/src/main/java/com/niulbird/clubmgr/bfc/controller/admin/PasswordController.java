package com.niulbird.clubmgr.bfc.controller.admin;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.niulbird.clubmgr.bfc.command.LoginData;
import com.niulbird.clubmgr.bfc.command.PasswordChangeData;
import com.niulbird.clubmgr.bfc.command.PasswordEmailData;
import com.niulbird.clubmgr.bfc.command.PasswordResetData;
import com.niulbird.clubmgr.bfc.controller.BaseController;
import com.niulbird.clubmgr.db.model.PasswordReset;
import com.niulbird.clubmgr.db.model.User;
import com.niulbird.clubmgr.db.service.UserService;
import com.niulbird.clubmgr.email.service.EmailService;

@Controller
public class PasswordController extends BaseController {
	private static final Logger log = Logger.getLogger(PasswordController.class);

	private static final String LOGIN = "login";
	private static final String PASSWORD_CHANGE = "password_change";
	private static final String PASSWORD_EMAIL = "password_email";
	private static final String PASSWORD_RESET = "password_reset";

	@Autowired
	UserService userService;
	
	@Autowired
	EmailService emailService;
	
	@RequestMapping(value = "/reset.html", method = RequestMethod.GET)
	public ModelAndView reset(@ModelAttribute("passwordEmailData") PasswordEmailData passwordEmailData,
			HttpServletRequest request) {
		ModelAndView mav = setView(PASSWORD_EMAIL, null);
		
		mav.setViewName(PASSWORD_EMAIL);
		
		return mav;
	}
	
	@RequestMapping(value = "/reset.html", method = RequestMethod.POST)
	public ModelAndView reset(@Valid PasswordEmailData passwordEmailData,
			BindingResult result,
			HttpServletRequest request) {
		ModelAndView mav = setView(LOGIN, null);
		
		log.debug("Sending Reset Email: " + passwordEmailData.getEmail());
		
		if (result.hasErrors()) {
			return setView(PASSWORD_EMAIL, null);
		}
		
		UUID resetKey = userService.addResetKey(passwordEmailData.getEmail());
		log.debug("Generating email for Reset Key: " + resetKey);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("email", passwordEmailData.getEmail());
		map.put("url", request.getScheme() + "://" + request.getHeader("Host") + "/resetPassword.html?key=" + resetKey);
		
		emailService.sendPasswordResetEmail(map);
				
		mav.addObject("loginData", new LoginData());
		mav.addObject("msg", messageSource.getMessage("password_email.success", null, null));
		
		return mav;
	}
	
	@RequestMapping(value = "/resetPassword.html", method = RequestMethod.GET)
	public ModelAndView resetPassword(@ModelAttribute("passwordResetData") PasswordResetData passwordResetData,
			@RequestParam String key, HttpServletRequest request) {
		ModelAndView mav = setView(PASSWORD_RESET, null);
		Calendar expiryDate = new GregorianCalendar();
		expiryDate.add(Calendar.DAY_OF_MONTH, -3);
		
		if (key == null) {
			mav.setViewName(PASSWORD_EMAIL);
		} else {
			PasswordReset passwordReset = userService.findByResetKey(UUID.fromString(key));

			if (passwordReset == null) {
				mav.setViewName(ERROR);
				mav.addObject("errMsg", messageSource.getMessage("error.password_reset.key_not_exist", null, null));
			} else if (passwordReset.getComplete()) {
				mav.setViewName(ERROR);
				mav.addObject("errMsg", messageSource.getMessage("error.password_reset.completed", null, null));
			} else if (passwordReset.getCreated().before(expiryDate.getTime())) {
				mav.setViewName(ERROR);
				mav.addObject("errMsg", messageSource.getMessage("error.password_reset.expired", null, null));
			} else {
				request.getSession().setAttribute("resetKey", key);
			}
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/resetPassword.html", method = RequestMethod.POST)
	public ModelAndView resetPassword(@Valid PasswordResetData passwordResetData,
			BindingResult result,
			HttpServletRequest request) {
		ModelAndView mav = setView(LOGIN, null);
		
		if (!passwordResetData.getPassword().equalsIgnoreCase(passwordResetData.getPasswordRepeat())) {
			result.rejectValue("password", "error.password_reset.mismatch");
		}
		
		if (result.hasErrors()) {
			return setView(PASSWORD_RESET, null);
		}
		
		try {
			userService.updatePassword(UUID.fromString((String)request.getSession().getAttribute("resetKey")), 
					new BCryptPasswordEncoder().encode(passwordResetData.getPassword()));
		} catch (Exception e) {
			log.error("Error saving new password: " + e.getMessage(), e);
			mav.setViewName(ERROR);
			mav.addObject("errMsg", messageSource.getMessage("error.password_reset.error", null, null));
		}
		
		mav.addObject("loginData", new LoginData());
		mav.addObject("msg", messageSource.getMessage("password_reset.success", null, null));
		return mav;
	}
	
	@RequestMapping(value = "/admin/change.html", method = RequestMethod.GET)
	public ModelAndView change(HttpServletRequest request) {
		ModelAndView mav = setView(PASSWORD_CHANGE, messageSource.getMessage("password_reset.title", null, null));
		mav.addObject("passwordChangeData", new PasswordChangeData());
		return mav;
	}
	
	@RequestMapping(value = "/admin/change.html", method = RequestMethod.POST)
	public ModelAndView change(@Valid PasswordChangeData passwordChangeData,
			BindingResult result,
			HttpServletRequest request) {
		ModelAndView mav = setView(LOGIN, null);
		
		User user = (User)request.getSession().getAttribute(USER);
		
		if (!passwordChangeData.getPassword().equalsIgnoreCase(passwordChangeData.getPasswordRepeat())) {
			result.rejectValue("passwordRepeat", "error.password_change.mismatch");
		}
		
		if (user == null || !new BCryptPasswordEncoder().matches(passwordChangeData.getOldPassword(), user.getPassword())) {
			result.rejectValue("oldPassword", "error.password_change.mismatch.old");
		}
		
		if (result.hasErrors()) {
			return setView(PASSWORD_CHANGE, null);
		}
		
		try {
			userService.updatePassword(user, new BCryptPasswordEncoder().encode(passwordChangeData.getPassword()));
		} catch (Exception e) {
			log.error("Error saving new password: " + e.getMessage(), e);
			mav.setViewName(ERROR);
			mav.addObject("errMsg", messageSource.getMessage("error.password_change.error", null, null));
		}
		
		mav.addObject("loginData", new LoginData());
		mav.addObject("msg", messageSource.getMessage("password_change.success", null, null));
		return mav;
	}
}
