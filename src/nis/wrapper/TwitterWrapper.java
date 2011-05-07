package nis.wrapper;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
}
