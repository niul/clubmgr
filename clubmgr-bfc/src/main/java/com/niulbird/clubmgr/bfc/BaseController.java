package com.niulbird.clubmgr.bfc;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.niulbird.clubmgr.util.wordpress.WordPressDao;

@Component
public abstract class BaseController {
	
	@Autowired
	protected WordPressDao wordPressDao;
	
	// Property source
	@Resource(name = "messageSource")
	protected MessageSource messageSource;
	
	@Value( "${wordpress.footerpanel.posts}" ) 
	protected String numFooterPosts;
	
	@Value( "${wordpress.news.posts}" ) 
	protected String numNewsPosts;
}
