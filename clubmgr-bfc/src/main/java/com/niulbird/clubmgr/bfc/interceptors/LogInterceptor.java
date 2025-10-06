package com.niulbird.clubmgr.bfc.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.niulbird.clubmgr.bfc.util.ControllerUtility;

import org.apache.logging.log4j.ThreadContext;

@Component
public class LogInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
			throws Exception {
		boolean proceedFlag = true;

		// Add local thread info to logging context
		ThreadContext.put("SESSIONID", request.getSession().getId());
		ThreadContext.put("IPADDRESS", ControllerUtility.remoteAddr(request));

		return proceedFlag;
	}

	@Override
	public void postHandle(final HttpServletRequest request,
			final HttpServletResponse response, final Object handler, final ModelAndView mav)
			throws Exception {

	}
}

