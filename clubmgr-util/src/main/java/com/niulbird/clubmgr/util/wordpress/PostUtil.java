package com.niulbird.clubmgr.util.wordpress;

import java.text.ParseException;
import java.util.ArrayList;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.json.JSONException;
import org.json.JSONObject;

import com.niulbird.clubmgr.util.ISO8601DateParser;
import com.niulbird.clubmgr.util.wordpress.dao.Author;
import com.niulbird.clubmgr.util.wordpress.dao.Category;
import com.niulbird.clubmgr.util.wordpress.dao.Post;
import com.niulbird.clubmgr.util.wordpress.dao.Tag;

public class PostUtil {
	private static final Logger log = LogManager.getLogger();

	public static Post getPost(JSONObject jsonObject) {
		Post post = new Post();
		post.setId(Integer.toString(jsonObject.getInt("ID")));
		post.setTitle(jsonObject.getString("title"));
		post.setExcerpt(jsonObject.getString("excerpt").replaceAll("\\<.*?\\>", ""));
		post.setContent(jsonObject.getString("content"));
		post.setUrl(jsonObject.getString("URL"));
		
		// Get Author
		JSONObject jsonAuthor = jsonObject.getJSONObject("author");
		Author author = new Author();
		author.setId(Integer.toString(jsonAuthor.getInt("ID")));
		author.setName(jsonAuthor.getString("name"));
		author.setUrl(jsonAuthor.getString("URL"));
		post.setAuthor(author);
		
		// Get Categories
		JSONObject categories = jsonObject.getJSONObject("categories");
		String[] categoryNames = JSONObject.getNames(categories);
		
		for (int i=0; i < categoryNames.length; i++) {
			JSONObject jsonCategory = categories.getJSONObject(categoryNames[i]);
			Category category = new Category();
			category.setId(Integer.toString(jsonCategory.getInt("ID")));
			category.setName(jsonCategory.getString("name"));
			category.setSlug(jsonCategory.getString("slug"));
			post.addCategory(category);
		}
		
		// Get Tags
		JSONObject tags = jsonObject.getJSONObject("tags");
		String[] tagNames = JSONObject.getNames(tags);
		
		if (tagNames != null) {
			for (int i=0; i < tagNames.length; i++) {
				JSONObject jsonTag = tags.getJSONObject(tagNames[i]);
				Tag tag = new Tag();
				tag.setId(Integer.toString(jsonTag.getInt("ID")));
				tag.setName(jsonTag.getString("name"));
				tag.setSlug(jsonTag.getString("slug"));
				post.addTag(tag);
			}
		}
		
		try {
			post.setCreateDate(ISO8601DateParser.parse(jsonObject.getString("date")));
		} catch (JSONException je) {
			log.error("Error parsing date: " + je.getMessage(), je);
		} catch (ParseException pe) {
			log.error("Error parsing date: " + pe.getMessage(), pe);
		}
		
		return post;
	}

	public static ArrayList<Post> getPosts(JSONObject jsonObject) {
		int postCount = jsonObject.getJSONArray("posts").length();
		String previousId = "-1";
		ArrayList<Post> posts = new ArrayList<Post>();
		
		for (int i = 0; i < postCount; i++) {
			JSONObject jsonPost = jsonObject.getJSONArray("posts").getJSONObject(i);
			Post post = getPost(jsonPost);
			post.setPreviousId(previousId);
			if (i > 0) {
				posts.get(posts.size()-1).setNextId(post.getId());
			}
			posts.add(post);
			
			previousId = post.getId();
		}
		
		return posts;
	}
}
