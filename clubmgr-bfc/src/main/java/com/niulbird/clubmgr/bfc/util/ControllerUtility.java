package com.niulbird.clubmgr.bfc.util;

import java.util.Date;
import java.util.Locale;
import java.util.Random;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.support.RequestContextUtils;

public final class ControllerUtility {
	
	// Static Literals
	private static final String HEADER_X_FORWARDED_FOR = "X-Forwarded-For";
	private static final int RAND_MAX = 9;
	
	/* Never called */
	private ControllerUtility () {
		
	}
	
	public static String generateNonce() {
		Random random = new Random();
		return new Date().getTime() + random.nextInt(RAND_MAX) + random.nextInt(RAND_MAX) +  random.nextInt(RAND_MAX) + "";
	}
	
	
	public static String remoteAddr(HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
        String x = request.getHeader(HEADER_X_FORWARDED_FOR);
        if (x != null) {
            remoteAddr = x;
            int idx = remoteAddr.indexOf(',');
            if (idx > -1) {
                remoteAddr = remoteAddr.substring(0, idx);
            }
        }
        return remoteAddr;
    }
	
	
  /**
   * Method to find the current Locale without request
   *
   * @return
   */
  public static Locale getCurrentLocale() {
      return getCurrentLocale(null);
  }

  /**
   * Method to find the current Locale with request
   *
   * @param request
   * @return
   */
  public static Locale getCurrentLocale(HttpServletRequest request) {
      if (request == null) {
          return java.util.Locale.getDefault();
      }

      Locale locale = RequestContextUtils.getLocale(request);
      if (locale == null) {
          locale = java.util.Locale.getDefault();
      }

      return locale;
  }
}
