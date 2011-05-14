package nis.model;

public class Youtube {
	String url;
	String imageUrl;
	String title;
	String description;
	String view;
	String uploader;
	
	public Youtube(String url, String imageUrl, String title,
			String description, String view, String uploader) {
		super();
		this.url = url;
		this.imageUrl = imageUrl;
		this.title = title;
		this.description = description;
		this.view = view;
		this.uploader = uploader;
	}

	public String getUrl() {
		return url;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getView() {
		return view;
	}
	
	public String getUploader() {
		return uploader;
	}
}
