package com.niulbird.clubmgr.bfc.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.niulbird.clubmgr.bfc.command.ContactData;
import com.niulbird.clubmgr.email.service.EmailService;

@Controller
public class ContactController extends BaseController {
	private static final Logger log = LoggerFactory.getLogger(ContactController.class);
	
	private static final String CONTACT = "contact";
	private static final String SUCCESS = "contact_success";
	
	// Whitelist of valid contact subjects to prevent injection attacks
	// Must match keys from bfc-messages.properties: contact.subjects=GA=General|MO=Mens Open|M45=Mens Over 45s|WO=Womens
	private static final Set<String> VALID_SUBJECTS = Collections.unmodifiableSet(
		Set.of("GA", "MO", "M45", "WO")
	);

	@Autowired
	EmailService emailService;
	
	@Autowired
	Properties props;
	
	@RequestMapping(value = "/contact.html", method = RequestMethod.GET)
	public ModelAndView contactView(@ModelAttribute("contactData") ContactData contactData,
			HttpServletRequest httpServletRequest) {
		log.info("Entering contactView(): " + contactData.getName() + "|" 
				+ contactData.getEmail() + "|" + contactData.getMessage());
		ModelAndView mav = setView(CONTACT, messageSource.getMessage("contact.title", null, null), httpServletRequest);
		
		mav.addObject("subjects", getSubjects());
		mav.addObject("recaptchaKey", props.getProperty("recaptcha.public"));
		
		return mav;
	}	
	
	@RequestMapping(value = "/contact.html", method = RequestMethod.POST)
	public ModelAndView contactPost(@Valid ContactData contactData,
			BindingResult result,
			final @RequestParam(name = "g-recaptcha-response") String captchaResponse,
			HttpServletRequest httpServletRequest) {
		log.info("Entering contactPost(): " + contactData.getName() + "|" 
				+ contactData.getEmail() + "|" + contactData.getSubject() + "|" + contactData.getMessage() + "|" + captchaResponse);
		
		// Validate subject against whitelist to prevent injection attacks
		if (contactData.getSubject() == null || !VALID_SUBJECTS.contains(contactData.getSubject())) {
			log.warn("Invalid contact subject submitted: " + contactData.getSubject());
			result.rejectValue("subject", "error.contact.invalid_subject");
		}
		
		String remoteAddr = httpServletRequest.getRemoteAddr();
		
		JSONTokener tokener = null;
		try {
			URI u = new URI(props.getProperty("recaptcha.baseUrl") + 
					"?secret=" + props.getProperty("recaptcha.secret") + 
					"&response=" + captchaResponse + 
					"&remoteip=" + remoteAddr);
			tokener = new JSONTokener(u.toURL().openStream());
		} catch (JSONException  e) {
			log.error("Error with Google Captcha: " + e.getMessage(), e);
		} catch (MalformedURLException e) {
			log.error("Error with Google Captcha: " + e.getMessage(), e);
		} catch (IOException e) {
			log.error("Error with Google Captcha: " + e.getMessage(), e);
		} catch (URISyntaxException e) {
			log.error("Error with Google Captcha: " + e.getMessage(), e);
		}
		JSONObject jsonObject = new JSONObject(tokener);
		log.debug("Google Captcha Response: " + jsonObject);
		
		if (result.hasErrors() || !jsonObject.getBoolean("success")) {
			ModelAndView mav = setView(CONTACT, messageSource.getMessage("contact.title", null, null), httpServletRequest);
			mav.addObject("subjects", getSubjects());
			mav.addObject("recaptchaKey", props.getProperty("recaptcha.public"));
			return mav;
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("email", contactData.getEmail());
			map.put("message", contactData.getMessage());
			map.put("name", contactData.getName());
			map.put("subject", contactData.getSubject());
			
			emailService.sendContactEmail(map);
			
			ModelAndView mav = setView(SUCCESS, messageSource.getMessage("contact.title", null, null), httpServletRequest);
			mav.addObject("contactData", contactData);
			mav.addObject("subjects", getSubjects());
			mav.addObject("recaptchaKey", props.getProperty("recaptcha.public"));
			return mav;
		}
	}
	
	private Map<String, String> getSubjects () {
		String subjectsStr = messageSource.getMessage("contact.subjects", null, null);
		Map<String, String> subjects = new LinkedHashMap<String,String>();
		
		for (String subject : subjectsStr.split("\\|")) {
			String[] subjectArr = subject.split("=");
			subjects.put(subjectArr[0], subjectArr[1]);
		}
		
		return subjects;
	}
}
