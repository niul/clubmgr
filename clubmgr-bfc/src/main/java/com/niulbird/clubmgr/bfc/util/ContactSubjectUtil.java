package com.niulbird.clubmgr.bfc.util;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 * Utility for parsing and validating contact form subjects.
 * Centralizes the logic for subject parsing and validation.
 */
@Component
public class ContactSubjectUtil {
	
	@Autowired
	private MessageSource messageSource;
	
	// Whitelist of valid contact subjects to prevent injection attacks
	// Must match keys from bfc-messages.properties: contact.subjects=GA=General|MO=Mens Open|M45=Mens Over 45s|WO=Womens
	private static final Set<String> VALID_SUBJECTS = Collections.unmodifiableSet(
		Set.of("GA", "MO", "M45", "WO")
	);
	
	/**
	 * Parse contact subjects from message source and return as map.
	 * Expected format: "GA=General|MO=Mens Open|M45=Mens Over 45s|WO=Womens"
	 */
	public Map<String, String> getSubjects() {
		String subjectsStr = messageSource.getMessage("contact.subjects", null, null);
		Map<String, String> subjects = new LinkedHashMap<>();
		
		for (String subject : subjectsStr.split("\\|")) {
			String[] subjectArr = subject.split("=");
			subjects.put(subjectArr[0], subjectArr[1]);
		}
		
		return subjects;
	}
	
	/**
	 * Validate that a subject is in the allowed whitelist.
	 * @param subject the subject to validate
	 * @return true if subject is valid, false otherwise
	 */
	public boolean isValidSubject(String subject) {
		return subject != null && VALID_SUBJECTS.contains(subject);
	}
}
