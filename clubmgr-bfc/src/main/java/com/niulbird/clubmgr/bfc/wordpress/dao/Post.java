package com.niulbird.clubmgr.bfc.wordpress.dao;

import java.util.ArrayList;
import java.util.Date;

public class Post {
	String id;
	String nextId;
	String previousId;
	Date createDate;
	String title;
	String excerpt;
	String content;
	String url;
	Author author;
	ArrayList<Category> categories = new ArrayList<Category>();
	ArrayList<Tag> tags = new ArrayList<Tag>();

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getNextId() {
		return nextId;
	}
	
	public void setNextId(String nextId) {
		this.nextId = nextId;
	}
	
	public String getPreviousId() {
		return previousId;
	}
	
	public void setPreviousId(String previousId) {
		this.previousId = previousId;
	}
	
	public Date getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getExcerpt() {
		return excerpt;
	}
	
	public void setExcerpt(String excerpt) {
		this.excerpt = excerpt;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ArrayList<Category> getCategories() {
		return categories;
	}

	public void setCategories(ArrayList<Category> categories) {
		this.categories = categories;
	}
	
	public Author getAuthor() {
		return this.author;
	}
	
	public void setAuthor(Author author) {
		this.author = author;
	}

	public ArrayList<Tag> getTags() {
		return tags;
	}

	public void setSlugs(ArrayList<Tag> tags) {
		this.tags = tags;
	}
	
	public void addCategory(Category category) {
		this.categories.add(category);
	}
	
	public void addTag(Tag tag) {
		this.tags.add(tag);
	}
}
