package com.niulbird.clubmgr.util.wordpress;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.niulbird.clubmgr.BaseTestCase;
import com.niulbird.clubmgr.util.wordpress.dao.Post;

public class WordPressServiceTest extends BaseTestCase {

	@Autowired
	WordPressDao wordPressDao;

	@Test
	public void getPosts() {
		ArrayList<Post> posts = null;
		posts = wordPressDao.getAllPosts();
		logger.debug("Testing the cache...");
		posts = wordPressDao.getAllPosts();
		Assert.assertNotNull(posts);
	}

	@Test
	public void getPost() {
		Post post = null;
		post = wordPressDao.getPost(1056);
		logger.debug("Testing the cache...");
		post = wordPressDao.getPost(1056);
		Assert.assertNotNull(post);
	}
}