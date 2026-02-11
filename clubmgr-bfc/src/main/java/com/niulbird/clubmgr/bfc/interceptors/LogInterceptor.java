package com.niulbird.clubmgr.bfc.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.slf4j.MDC;

import com.niulbird.clubmgr.bfc.util.ControllerUtility;

@Component
public class LogInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
			throws Exception {
		boolean proceedFlag = true;

		// Add local thread info to logging context using SLF4J MDC
		MDC.put("SESSIONID", request.getSession().getId());
		MDC.put("IPADDRESS", ControllerUtility.remoteAddr(request));

		return proceedFlag;
	}

	@Override
	public void postHandle(final HttpServletRequest request,
			final HttpServletResponse response, final Object handler, final ModelAndView mav)
			throws Exception {

	}
}

