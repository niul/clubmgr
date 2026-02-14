package com.niulbird.clubmgr.bfc.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.niulbird.clubmgr.util.wordpress.WordPressDao;
import com.niulbird.clubmgr.util.wordpress.dao.Post;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public abstract class BaseController {
	private static final Logger log = LoggerFactory.getLogger("BaseController");
	
	protected static final String ERROR = "error";
	protected static final String PAGE = "page";
	protected static final String TITLE = "title";
	protected static final String USER = "user";
	protected static final String DATE = "date";
	protected static final String HTTP_SERVLET_REQUEST = "httpServletRequest";
	protected static final String URL_ENCODED = "eUrl";
	
	@Autowired
	protected WordPressDao wordPressDao;
	
	// Property source
	@Autowired
	protected MessageSource messageSource;
	
	@Value( "${wordpress.footerpanel.posts}" ) 
	protected String numFooterPosts;
	
	@Value( "${wordpress.news.posts}" ) 
	protected String numNewsPosts;
	
	protected ModelAndView setView(String pageName, String title, HttpServletRequest httpServletRequest) {
		ModelAndView mav = new ModelAndView();
		String eUrl = new String();
		
		mav.setViewName(pageName);
		mav.addObject(PAGE, pageName);
		mav.addObject(TITLE, title);
		mav.addObject(DATE, new Date());
		mav.addObject(HTTP_SERVLET_REQUEST, httpServletRequest);

		try {
			eUrl = URLEncoder.encode(httpServletRequest.getRequestURL().toString(), java.nio.charset.StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {
			log.error("Issue encoded Uri: " + e.getMessage(), e);
		}
		mav.addObject(URL_ENCODED, eUrl);
		
		ArrayList<Post> posts = wordPressDao.getAllPosts();
		mav.addObject("posts", posts);
		mav.addObject("footerPosts", posts.subList(0,
				(posts.size() < Integer.parseInt(numFooterPosts)) ? posts.size()
						: Integer.parseInt(numFooterPosts)));

		log.debug("Setting view: " + pageName);
		
		return mav;
	}
}
