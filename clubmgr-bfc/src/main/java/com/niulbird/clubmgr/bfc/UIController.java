package com.niulbird.clubmgr.bfc;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.niulbird.clubmgr.bfc.command.ContactData;
import com.niulbird.clubmgr.bfc.wordpress.WordPressDao;
import com.niulbird.clubmgr.bfc.wordpress.dao.Post;

@Controller
public class UIController extends BaseController {
	private static final Logger log = Logger.getLogger(UIController.class);

	private static final String ABOUT = "about";
	private static final String BOMBASTIC_MENS = "mens";
	private static final String BOMBASTIC_MENS_A = "mensA";
	private static final String BOMBASTIC_MENS_B = "mensB";
	private static final String BOMBASTIC_MENS_CLASSICS = "mensClassics";
	private static final String BOMBASTIC_MENS_JURASSIC = "mensJurassic";
	private static final String BOMBASTIC_WOMENS = "womens";
	private static final String CALENDAR = "calendar";
	private static final String CONTACT = "contact";
	private static final String HOME = "home";
	private static final String PAGE = "page";
	private static final String PAGE_NOT_FOUND = "page_not_found";
	private static final String SPONSORS = "sponsors";
	
	@Autowired
	private WordPressDao wordPressDao;
	
	@RequestMapping(value = "/about.html")
	public ModelAndView about() {
		return setView(ABOUT);
	}
	
	@RequestMapping(value = "/calendar.html")
	public ModelAndView calendar() {
		return setView(CALENDAR);
	}
	
	@RequestMapping(value = "/contact.html")
	public ModelAndView contact() {
		return setView(CONTACT);
	}
	
	@RequestMapping(value = "/index.html")
	public ModelAndView home() {
		return setView(HOME);
	}
	
	@RequestMapping(value = "/mens.html")
	public ModelAndView mens() {
		return setView(BOMBASTIC_MENS);
	}
	
	@RequestMapping(value = "/mensA.html")
	public ModelAndView mensA() {
		return setView(BOMBASTIC_MENS_A);
	}
	
	@RequestMapping(value = "/mensB.html")
	public ModelAndView mensB() {
		return setView(BOMBASTIC_MENS_B);
	}
	
	@RequestMapping(value = "/mensClassics.html")
	public ModelAndView mensClassics() {
		return setView(BOMBASTIC_MENS_CLASSICS);
	}
	
	@RequestMapping(value = "/mensJurassic.html")
	public ModelAndView mensJurassic() {
		return setView(BOMBASTIC_MENS_JURASSIC);
	}
	
	@RequestMapping(value = "/womens.html")
	public ModelAndView womens() {
		return setView(BOMBASTIC_WOMENS);
	}
	
	@RequestMapping(value = "/sponsors.html")
	public ModelAndView sponsors() {
		return setView(SPONSORS);
	}
	
	@RequestMapping(value = "/404.html")
	public ModelAndView pageNotFound() {
		return setView(PAGE_NOT_FOUND);
	}
	
	private ModelAndView setView(String pageName) {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName(pageName);
		mav.addObject(PAGE, pageName);
		mav.addObject("contactData", new ContactData());
		
		ArrayList<Post> posts = wordPressDao.getAllPosts();
		mav.addObject("posts", posts);
		mav.addObject("footerPosts", posts.subList(0,
				(posts.size() < Integer.parseInt(numFooterPosts)) ? posts.size()
						: Integer.parseInt(numFooterPosts)));

		log.debug("Setting view: " + pageName);
		
		return mav;
	}
}
