package com.niulbird.clubmgr.bfc.tags;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class URL {
	public static String urlEncode(String input) {
		String output = new String();
		
		try {
			output = URLEncoder.encode(input, "UTF-8");
		} catch (UnsupportedEncodingException e) {}
		
	    return output;
	}
}