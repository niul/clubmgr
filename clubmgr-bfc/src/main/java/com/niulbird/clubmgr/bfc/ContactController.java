package com.niulbird.clubmgr.bfc;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.niulbird.clubmgr.bfc.command.ContactData;
import com.niulbird.clubmgr.bfc.util.MailUtil;
import com.niulbird.clubmgr.bfc.wordpress.WordPressDao;
import com.niulbird.clubmgr.bfc.wordpress.dao.Post;

@Controller
public class ContactController extends BaseController {
	private static final Logger log = Logger.getLogger(ContactController.class);
	
	private static final String CONTACT = "contact";
	private static final String SUCCESS = "contact_success";
	private static final String PAGE = "page";

	@Autowired
	private WordPressDao wordPressDao;
	
	@Autowired
	private JavaMailSenderImpl mailSender;
	
	@Autowired
	private SimpleMailMessage mailMessage;
	
	@RequestMapping(value = "/contact.html", method = RequestMethod.GET)
	public ModelAndView contactView(@ModelAttribute("contactData") ContactData contactData) {
		log.info("Entering contactView(): " + contactData.getName() + "|" 
				+ contactData.getEmail() + "|" + contactData.getMessage());
		return setView(CONTACT);
	}	
	
	@RequestMapping(value = "/contact.html", method = RequestMethod.POST)
	public ModelAndView contactPost(@Valid ContactData contactData,
			BindingResult result) {
		log.info("Entering contactPost(): " + contactData.getName() + "|" 
				+ contactData.getEmail() + "|" + contactData.getMessage());
		
		if (result.hasErrors()) {
			return setView(CONTACT);
		} else {
			MailUtil mailUtil = new MailUtil();
			mailUtil.sendMail(mailSender, mailMessage, contactData.getEmail(), contactData.getName(), contactData.getMessage());
			ModelAndView mav = setView(SUCCESS);
			mav.addObject("name", contactData.getName());
			return mav;
		}
	}
	
	private ModelAndView setView(String pageName) {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName(pageName);
		mav.addObject(PAGE, pageName);
		
		ArrayList<Post> posts = wordPressDao.getAllPosts();
		mav.addObject("posts", posts);
		mav.addObject("menuPosts", posts.subList(0,
				(posts.size() < Integer.parseInt(numFooterPosts)) ? posts.size()
						: Integer.parseInt(numFooterPosts)));
		
		log.debug("Setting view: " + pageName);
		
		return mav;
	}
}
