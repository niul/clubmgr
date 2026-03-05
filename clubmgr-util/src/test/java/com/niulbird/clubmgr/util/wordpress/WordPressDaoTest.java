package com.niulbird.clubmgr.util.wordpress;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.niulbird.clubmgr.BaseTestCase;

public class WordPressDaoTest extends BaseTestCase {
	@Autowired
	WordPressDao wordPressDao;
	
	@Test
	public void testWordPressDao() {
		Assertions.assertNotNull(wordPressDao);
	}
}
