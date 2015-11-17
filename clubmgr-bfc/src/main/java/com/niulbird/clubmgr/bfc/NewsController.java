package com.niulbird.clubmgr.bfc;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.niulbird.clubmgr.bfc.command.ContactData;
import com.niulbird.clubmgr.bfc.wordpress.dao.Post;

@Controller
public class NewsController extends BaseController {
	private static final Logger log = Logger.getLogger(NewsController.class);

	private static final String NEWS = "news";
	private static final String PAGE = "page";
	private static final String POST = "post";
	private static final String TITLE = "title";
	
	@RequestMapping(value = "/news.html")
	public ModelAndView news(@RequestParam(value = "start", defaultValue = "0") int start) {
		ModelAndView mav = new ModelAndView();
		log.debug("Start: " + start);
		
		mav.setViewName(NEWS);
		mav.addObject(PAGE, NEWS);
		mav.addObject(TITLE, messageSource.getMessage("news.title", null, null));

		ArrayList<Post> posts = wordPressDao.getAllPosts();
		mav.addObject("previous", start - Integer.parseInt(numNewsPosts));
		mav.addObject("next", ((start + Integer.parseInt(numNewsPosts) > posts.size() ) ? 0 : start + Integer.parseInt(numNewsPosts)));
		mav.addObject("posts", posts.subList(start,
				(posts.size() < start + Integer.parseInt(numNewsPosts)) ? posts.size()
						: start + Integer.parseInt(numNewsPosts)));
		mav.addObject("footerPosts", posts.subList(0,
				(posts.size() < Integer.parseInt(numFooterPosts)) ? posts.size()
						: Integer.parseInt(numFooterPosts)));
		
		mav.addObject("contactData", new ContactData());
		
		log.debug("Setting view: " + PAGE);
		
		return mav;
	}
	
	@RequestMapping(value = "/post.html")
	public ModelAndView post(@RequestParam(value="id") int code) {
		ModelAndView mav = new ModelAndView();

		ArrayList<Post> posts = wordPressDao.getAllPosts();
		Post post = null;
		for (Post p : posts ) {
			if (Integer.parseInt(p.getId()) == code) {
				post = p;
				break;
			}
		}
		
		mav.setViewName(POST);
		mav.addObject(PAGE, POST);
		mav.addObject(TITLE, post.getTitle());
		mav.addObject("post", post);

		mav.addObject("posts", posts);
		mav.addObject("footerPosts", posts.subList(0,
				(posts.size() < Integer.parseInt(numFooterPosts)) ? posts.size()
						: Integer.parseInt(numFooterPosts)));
		
		log.debug("Setting view: " + PAGE);
		
		return mav;
	}
	

	@RequestMapping(value = "/refreshCache.html")
	public ModelAndView refreshCache() {
		wordPressDao.clearAllCache();
		
		return news(0);
	}
}
