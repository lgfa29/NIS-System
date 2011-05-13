package nis.model;

public class Google {
	String url;
	String title;
	String content;
	String visibleUrl;
	String cachedUrl;
	
	public Google(String url, String title, String content, String visibleUrl,
			String cachedUrl) {
		this.url = url;
		this.title = title;
		this.content = content;
		this.visibleUrl = visibleUrl;
		this.cachedUrl = cachedUrl;
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

	public String getVisibleUrl() {
		return visibleUrl;
	}

	public String getCachedUrl() {
		return cachedUrl;
	}
}
