package com.niulbird.clubmgr.data.util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Properties;


public abstract class BaseUtil {
    private static final Logger log = LoggerFactory.getLogger(BaseUtil.class);

    protected Properties props;

    protected Integer getStripedInt(String input) {
    	Integer i = Integer.valueOf(0);
    	try {
    		i = Integer.valueOf(input.trim().replaceAll("[^0-9]", ""));
    	} catch (NumberFormatException nfe) {
    		log.error("Error parsing: " + input);
    	}
    	return i;
    }

	protected Time convertStringToTime(String time, String format) {
		return convertStringToTime(time, format, null);
	}

	protected Time convertStringToTime(String time, String format, String format2) {
		Time t = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
		long ms = 0;

		if (time.length() == 5)
			time = new String("0").concat(time);

		try {
			try {
				ms = sdf.parse(time).getTime();
			} catch (ParseException e) {
				sdf = new SimpleDateFormat(format2);
				ms = sdf.parse(time).getTime();
			}
		} catch (ParseException e) {
			log.error(e.getMessage(), e);
			return null;
		}
		t = new Time(ms);
		return t;
	}

	protected Date convertStringToDate(String date, String format) {
		return convertStringToDate(date, format, null);
	}

	protected Date convertStringToDate(String date, String format, String format2) {
		Date d = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
		long ms = 0;
		try {
			try {
				ms = sdf.parse(date.replaceAll("(?:st|nd|rd|th)", "")).getTime();
			} catch(ParseException e) {
				sdf = new SimpleDateFormat(format2);
				ms = sdf.parse(date.replaceAll("(?:st|nd|rd|th)", "")).getTime();
			}
		} catch (ParseException e) {
			log.error(e.getMessage(), e);
			return null;
		}
		d = new Date(ms);
		return d;
	}
}
