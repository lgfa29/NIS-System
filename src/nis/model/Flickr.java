package nis.model;

public class Flickr {
	String thumbURL;
	String mediumURL;
	
	public Flickr() {}
	
	public Flickr(String thumbURL, String mediumURL){
		this.thumbURL = thumbURL;
		this.mediumURL = mediumURL;
	}

	public String getThumbURL() {
		return thumbURL;
	}

	public String getMediumURL() {
		return mediumURL;
	}
}
