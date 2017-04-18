package com.niulbird.clubmgr.bfc.controller;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.niulbird.clubmgr.bfc.command.ContactData;
import com.niulbird.clubmgr.util.freemarker.MessageResolverMethod;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

import com.niulbird.clubmgr.util.MailUtil;

@Controller
public class ContactController extends BaseController {
	private static final Logger log = Logger.getLogger(ContactController.class);
	
	private static final String CONTACT = "contact";
	private static final String SUCCESS = "contact_success";

	@Autowired
	Properties props;
	
	@Autowired
	private JavaMailSenderImpl mailSender;
	
	@Autowired
	private Configuration freeMarkerConfiguration;
	
	@RequestMapping(value = "/contact.html", method = RequestMethod.GET)
	public ModelAndView contactView(@ModelAttribute("contactData") ContactData contactData) {
		log.info("Entering contactView(): " + contactData.getName() + "|" 
				+ contactData.getEmail() + "|" + contactData.getMessage());
		ModelAndView mav = setView(CONTACT, messageSource.getMessage("contact.title", null, null));
		
		mav.addObject("subjects", getSubjects());
		
		return mav;
	}	
	
	@RequestMapping(value = "/contact.html", method = RequestMethod.POST)
	public ModelAndView contactPost(@Valid ContactData contactData,
			BindingResult result) {
		log.info("Entering contactPost(): " + contactData.getName() + "|" 
				+ contactData.getEmail() + "|" + contactData.getSubject() + "|" + contactData.getMessage());
		
		if (result.hasErrors()) {
			return setView(CONTACT, messageSource.getMessage("contact.title", null, null));
		} else {
			MailUtil mailUtil = new MailUtil();
			
			String body = new String();
			Map<String, Object> map = new HashMap<String, Object>();
			

			try {
				map.put("msg", new MessageResolverMethod(messageSource, null));
				map.put("contactData", contactData);
				body = FreeMarkerTemplateUtils
						.processTemplateIntoString(freeMarkerConfiguration.getTemplate("contact.ftl"), map);
			} catch (IOException | TemplateException e) {
				log.error("Error generating freemarker template: " + e.getMessage(), e);
			}
						
			String[] emailList = props.getProperty("email.toEmail.contact." + contactData.getSubject()).split("\\|");
			
			mailUtil.sendMail(mailSender, emailList, props.getProperty("email.subject"), body, props);
			ModelAndView mav = setView(SUCCESS, messageSource.getMessage("contact.title", null, null));
			mav.addObject("contactData", contactData);
			mav.addObject("subjects", getSubjects());
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
