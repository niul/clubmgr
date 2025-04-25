package com.niulbird.clubmgr;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:META-INF/spring/test-context.xml")
@EnableAutoConfiguration(exclude = { FreeMarkerAutoConfiguration.class })
public abstract class BaseTestCase {

	// Logger for this class and subclasses
    protected final Log logger = LogFactory.getLog(getClass());
    
	@Autowired
	protected MessageSource messageSource;
	
	@Before
	public void setup() {
		System.out.println("Test");
	}
}