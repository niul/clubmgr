package com.niulbird.clubmgr.bfc.wordpress;

import java.util.ArrayList;

import com.niulbird.clubmgr.bfc.wordpress.dao.Post;

public interface WordPressDao {

	public Post getPost(int id);

	public ArrayList<Post> getPosts(int number);

	public ArrayList<Post> getAllPosts();
	
	public void clearAllCache();
}
