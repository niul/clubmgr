package com.niulbird.clubmgr.bfc.wordpress;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.niulbird.clubmgr.bfc.BaseTestCase;
import com.niulbird.clubmgr.bfc.wordpress.WordPressDao;
import com.niulbird.clubmgr.bfc.wordpress.dao.Post;

public class WordPressServiceTest extends BaseTestCase {

	@Autowired 
	WordPressDao wordPressDao;
	
	@Test
	public void getPosts() {
		ArrayList<Post> posts = null;
		posts = wordPressDao.getAllPosts();
		Assert.assertNotNull(posts);
	}
}
