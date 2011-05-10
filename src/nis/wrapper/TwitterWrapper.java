package nis.wrapper;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nis.util.Util;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class TwitterWrapper implements Wrapper {
	public void run(HttpServletRequest req, HttpServletResponse res) throws Exception {
		RequestDispatcher rd = req.getRequestDispatcher("/wrappers/twitter.jsp");
		rd.forward(req, res);
	}
	
	public static List<Tweet> search(String param) {
		Twitter twitter = new TwitterFactory().getInstance();
		Query query = new Query(param);
		QueryResult result = null;
		
		try {
			result = twitter.search(query);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		
		if(result != null)
			return result.getTweets();
		else
			return null;
		
	}
	
	public static String getParseTweet(String tweet, String selectedWord) {
		String[] array = Util.parseLinks(tweet).split(" ");
		StringBuilder parsed = new StringBuilder();
		
		for(String word : array){
			if(word.startsWith("@")){
				parsed.append(" <a href=\"http://twitter.com/#!/" + word.substring(1).replaceAll("[ \t\r\n.:,]", "") + "\" target=\"_blank\">" + word + "</a>");
			}
			else if(selectedWord.toLowerCase().contains(word.toLowerCase())){
				for(String keyword : selectedWord.split(" ")){
					if(word.equalsIgnoreCase(keyword))
						parsed.append(" <b>" + word + "</b>");
				}
			}
			else
				parsed.append(" " + word);
		}
		
		return parsed.toString();
	}
}
