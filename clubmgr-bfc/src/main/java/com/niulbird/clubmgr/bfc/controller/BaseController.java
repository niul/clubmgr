package com.niulbird.clubmgr.bfc.controller;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.niulbird.clubmgr.util.wordpress.WordPressDao;
import com.niulbird.clubmgr.util.wordpress.dao.Post;

@Component
public abstract class BaseController {
	Logger log = LogManager.getLogger();
	
	protected static final String ERROR = "error";
	protected static final String PAGE = "page";
	protected static final String TITLE = "title";
	protected static final String USER = "user";
	
	@Autowired
	protected WordPressDao wordPressDao;
	
	// Property source
	@Resource(name = "messageSource")
	protected MessageSource messageSource;
	
	@Value( "${wordpress.footerpanel.posts}" ) 
	protected String numFooterPosts;
	
	@Value( "${wordpress.news.posts}" ) 
	protected String numNewsPosts;
	
	protected ModelAndView setView(String pageName, String title) {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName(pageName);
		mav.addObject(PAGE, pageName);
		mav.addObject(TITLE, title);
		
		ArrayList<Post> posts = wordPressDao.getAllPosts();
		mav.addObject("posts", posts);
		mav.addObject("footerPosts", posts.subList(0,
				(posts.size() < Integer.parseInt(numFooterPosts)) ? posts.size()
						: Integer.parseInt(numFooterPosts)));

		log.debug("Setting view: " + pageName);
		
		return mav;
	}
}
