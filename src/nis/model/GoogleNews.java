package nis.model;

public class GoogleNews {
	private String title;
	private String url;
	private String publisher;
	private String publishedDate;
	private String content;
	
	public GoogleNews(
			String title, String url, 
			String publisher, String publishedDate, 
			String content) {
		this.title = title;
		this.url = url;
		this.publisher = publisher;
		
		this.publishedDate = publishedDate;
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public String getUrl() {
		return url;
	}

	public String getPublisher() {
		return publisher;
	}

	public String getPublishedDate() {
		return publishedDate;
	}

	public String getContent() {
		return content;
	}
}
