package com.niulbird.clubmgr.util.wordpress;

import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

import com.niulbird.clubmgr.util.wordpress.dao.Category;
import com.niulbird.clubmgr.util.wordpress.dao.Post;

@Component
public class DefaultWordPressDao  implements WordPressDao {
	private static final Logger log = Logger.getLogger(DefaultWordPressDao.class);
	
	private static String GET_POSTS = "/posts/{0}";
	private static String GET_POSTS_ALL = "/posts";
	private static String GET_POSTS_NUM = "/posts/?number={0}";
	
	private String baseUrl;
	private String siteName;
	
	@Override
	@Cacheable(value = "postCache")	
	public Post getPost(int id) {
		Post post = null;
		String uri = baseUrl + siteName + MessageFormat.format(GET_POSTS, String.valueOf(id));
		
		log.debug("WordPress URL: " + uri);
		
		try {
			JSONTokener tokener = new JSONTokener(new URL(uri).openStream());
			JSONObject jsonObject = new JSONObject(tokener);
		
			post = PostUtil.getPost(jsonObject);
						
			log.debug("ID: " + post.getId());
			log.debug("Date: " + post.getCreateDate());
			log.debug("Title: " + post.getTitle());
			log.debug("Excerpt: " + post.getExcerpt());
			log.debug("Content: " + post.getContent());
			log.debug("URL: " + post.getUrl());
		} catch (IOException ioe) {
			log.error("IOException: " + ioe.getMessage(), ioe);
		}
		return post;
	}
	
	@Override
	@Cacheable(value = "postsCache")
	public ArrayList<Post> getPosts(int number) {
		ArrayList<Post> posts = new ArrayList<Post>();
		log.debug("Entering getPosts()");
		try {
			String uri;
			if (number >= 0) {
				uri = baseUrl + siteName + MessageFormat.format(GET_POSTS_NUM, String.valueOf(number));
			} else {
				uri = baseUrl + siteName + GET_POSTS_ALL;
			}
					
			log.debug("WordPress URL: " + uri);
			JSONTokener tokener = new JSONTokener(new URL(uri).openStream());
			JSONObject jsonObject = new JSONObject(tokener);
			
			posts = PostUtil.getPosts(jsonObject);
				
			for (Post post : posts) {
				String categories = new String();
				log.debug("ID: " + post.getId());
				log.debug("Date: " + post.getCreateDate());
				log.debug("Title: " + post.getTitle());
				log.debug("Excerpt: " + post.getExcerpt());
				log.debug("Content: " + post.getContent());
				log.debug("URL: " + post.getUrl());
				for (Category category : post.getCategories())
					categories += category.getName() + "|";
				log.debug("Categories: " + categories);
			}
		} catch (IOException ioe) {
			log.error("IOException: " + ioe.getMessage(), ioe);
		}
		return posts;
	}

	@Override
	@Cacheable(value = "postsCache")	
	public ArrayList<Post> getAllPosts() {
		return getPosts(-1);
	}
	
	@Override
	@Caching(evict = { @CacheEvict("postCache"), @CacheEvict("postsCache") })
	public void clearAllCache() {
		
	}
	
	public void setBaseUrl(String baseUrl) {
		log.debug("BaseURL: " + baseUrl);
		this.baseUrl = baseUrl;
	}
	
	public void setSiteName(String siteName) {
		log.debug("SiteName: " + siteName);
		this.siteName = siteName;
	}
}
