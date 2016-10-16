package com.niulbird.clubmgr.bfc;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.niulbird.clubmgr.bfc.command.ContactData;
import com.niulbird.clubmgr.bfc.command.LoginData;
import com.niulbird.clubmgr.util.wordpress.dao.Post;

@Controller
public class AdminController extends BaseController {
	private static final Logger log = Logger.getLogger(AdminController.class);
	
	private static final String ADMIN_HOME = "admin_home";
	private static final String PAGE = "page";
	private static final String TITLE = "title";
	
	@RequestMapping(value = "/admin/index.html", method = RequestMethod.GET)
	public ModelAndView adminPage() {
	  ModelAndView model = setView(ADMIN_HOME, "Spring Security Login Form - Database Authentication");
	  model.addObject("user", getPrincipal());
	  return model;

	}
	
	@RequestMapping(value = "/login.html", method = RequestMethod.GET)
	public ModelAndView login(@ModelAttribute("loginData") LoginData loginData,
			@RequestParam(value = "error", required = false) String error,
		@RequestParam(value = "logout", required = false) String logout) {

	  ModelAndView model = new ModelAndView();
	  if (error != null) {
		model.addObject("error", "Invalid username and password!");
	  }

	  if (logout != null) {
		model.addObject("msg", "You've been logged out successfully.");
	  }
	  model.setViewName("login");
	  model.addObject("loginData", loginData);

	  return model;
	}
	
	@RequestMapping(value="/logout.html", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login.html?logout";
    }
	
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {

	  ModelAndView model = new ModelAndView();

	  //check if user is login
	  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	  if (!(auth instanceof AnonymousAuthenticationToken)) {
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		model.addObject("username", userDetail.getUsername());
	  }

	  model.setViewName("access_denied");
	  return model;

	}
	
	private ModelAndView setView(String pageName, String title) {
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
	
	private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 
        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
}
