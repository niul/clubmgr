package com.niulbird.clubmgr.bfc.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.niulbird.clubmgr.bfc.util.ControllerUtility;

import org.apache.log4j.MDC;

public class LogInterceptor extends HandlerInterceptorAdapter {

	/**
	 * Add objects to context for logging.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param handler
	 *            Object
	 * @return boolean
	 * @throws Exception
	 */
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		boolean proceedFlag = true;
		
		// Add local thread info to logging context
		MDC.put("SESSIONID", request.getSession().getId());
		MDC.put("IPADDRESS", ControllerUtility.remoteAddr(request));
		
		return proceedFlag;
	}

	/**
	 * Write CSRF Token to session and cookie.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param handler
	 *            Object
	 * @return boolean
	 * @throws Exception
	 */
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler, ModelAndView mav)
			throws Exception {

	}
}
