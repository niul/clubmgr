package com.niulbird.clubmgr.bfc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ErrorController extends BaseController {

    @GetMapping("/error/403")
    public ModelAndView accessDenied(HttpServletRequest httpServletRequest) {
    	ModelAndView mav = setView("error/403", null, httpServletRequest);
		return mav;
    }
    
    @GetMapping("/error/404")
    public String notFound() {
        return "error/404";
    }

    @GetMapping("/error/csrf")
    public String csrfError() {
        return "error/csrf";
    }
}