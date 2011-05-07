package nis.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class GoogleNews {
	private String title;
	private String url;
	private String publisher;
	private Date publishedDate;
	private String publishedDateStr;
	private String content;
	
	public GoogleNews(
			String title, String url, 
			String publisher, String publishedDate, 
			String content) {
		this.title = title;
		this.url = url;
		this.publisher = publisher;
		
		this.publishedDateStr = publishedDate;
		
		try {
			this.publishedDate = DateFormat.getInstance().parse(publishedDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
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
		/*Calendar cal = new GregorianCalendar();
		cal.setTime(publishedDate);
		String date = 
			Integer.toString(cal.get(Calendar.MONTH)) + "/" + 
			Integer.toString(cal.get(Calendar.DAY_OF_MONTH)) + "/" +
			Integer.toString(cal.get(Calendar.YEAR)) + "-" +
			Integer.toString(cal.get(Calendar.HOUR)) + ":" +
			Integer.toString(cal.get(Calendar.MINUTE));*/
		
		return publishedDateStr;
	}

	public String getContent() {
		return content;
	}
}
