package nis.model;

public class Wikipedia {
	String title;
	String description;
	String url;
	
	public Wikipedia(){}
	
	public Wikipedia(String title, String description, String url) {
		this.title = title;
		this.description = description;
		this.url = url;
	}
	
	public String getTitle() {
		return title;
	}
	public String getDescription() {
		return description;
	}
	public String getUrl() {
		return url;
	}
}
