package nis.model;

public class NaverBlog {
	String url;
	String title;
	String content;
	String name;
	String blogUrl;
	
	public NaverBlog(String url, String title, String content, String name,
			String blogUrl) {
		this.url = url;
		this.title = title;
		this.content = content;
		this.name = name;
		this.blogUrl = blogUrl;
	}

	public String getUrl() {
		return url;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public String getName() {
		return name;
	}

	public String getBlogUrl() {
		return blogUrl;
	}
}
