package com.niulbird.clubmgr.bfc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = WebApplication.class)
@ActiveProfiles("test")
public abstract class BaseTestCase {

	// Logger for this class and subclasses
    protected final Logger log = LoggerFactory.getLogger(getClass());
    
	@Autowired
	protected MessageSource messageSource;
	
	@BeforeEach
	public void setup() {
		log.debug("Setting up test case");
	}
}
