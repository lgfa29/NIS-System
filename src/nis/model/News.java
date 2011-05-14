package nis.model;

public class News {
	String url;
	String date;
	String section;
	String headline;
	String trailText;
	String thumbnail;
	String author;
	
	public News(String url, String date, String section, String headline,
			String trailText, String thumbnail, String author) {
		this.url = url;
		this.date = date;
		this.section = section;
		this.headline = headline;
		this.trailText = trailText;
		this.thumbnail = thumbnail;
		this.author = author;
	}
	
	public String getUrl() {
		return url;
	}
	public String getDate() {
		return date;
	}
	public String getSection() {
		return section;
	}
	public String getHeadline() {
		return headline;
	}
	public String getTrailText() {
		return trailText;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public String getAuthor() {
		return author;
	}
}
