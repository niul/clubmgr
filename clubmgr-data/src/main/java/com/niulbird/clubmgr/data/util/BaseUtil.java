package com.niulbird.clubmgr.data.util;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class BaseUtil {
    private final Log logger = LogFactory.getLog(getClass());
    
    protected Properties props;
    
    protected Integer getStripedInt(String input) {
    	Integer i = new Integer(0);
    	try {
    		i = new Integer(input.trim().replaceAll("[^0-9]", ""));
    	} catch (NumberFormatException nfe) {
    		logger.error("Error parsing: " + input);
    	}
    	return i;
    }
	
	protected Time convertStringToTime(String time, String format) {
		Time t = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		long ms = 0;
		
		if (time.length() == 5)
			time = new String("0").concat(time);
		
		try {
			ms = sdf.parse(time).getTime();
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
		t = new Time(ms);
		return t;
	}
	
	protected Date convertStringToDate(String date, String format) {
		Date d = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		long ms = 0;
		try {
			ms = sdf.parse(date.replaceAll("(?:st|nd|rd|th)", "")).getTime();
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
		d = new Date(ms);
		return d;
	}
}
