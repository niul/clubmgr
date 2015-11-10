package com.niulbird.clubmgr.bfc;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public abstract class BaseController {
	
	// Property source
	@Resource(name = "messageSource")
	MessageSource messageSource;
	
	@Value( "${wordpress.footerpanel.posts}" ) 
	protected String numFooterPosts;
	
	@Value( "${wordpress.news.posts}" ) 
	protected String numNewsPosts;
}
