package nis.wrapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nis.model.GoogleNews;

import org.json.JSONArray;
import org.json.JSONObject;

public class GoogleNewsWrapper implements Wrapper {

	@Override
	public void run(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		req.getSession().setAttribute("newsQuery", req.getParameter("newsQuery"));
		RequestDispatcher rd = req.getRequestDispatcher("/wrappers/gnews.jsp");
		rd.forward(req, res);
	}
	
	public static List<GoogleNews> getTopNews(HttpServletRequest req) throws Exception {
		List<GoogleNews> results = new ArrayList<GoogleNews>();
		String key = "ABQIAAAA0DmdZTFM-GXJ_5YuVOzTlBT83daVXoyKrbOHEVulBRA0Z5nqahSLDHPmk8-T4gOLxyijcCe6bnkdkQ";
		String ip = req.getRemoteAddr();
		String query = "q=" + req.getSession().getAttribute("newsQuery");
		
		URL url = new URL(
				"https://ajax.googleapis.com/ajax/services/search/news?"
						+ "v=1.0&"+query.replace(" ", "%20")+"&key="+key+"&userip="+ip+"&rsz=8");
		URLConnection connection = url.openConnection();
		connection.addRequestProperty("Referer",
				"http://110.76.95.177:8080/nis/");

		String line;
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
		while ((line = reader.readLine()) != null) {
			builder.append(line);
		}

		JSONObject json = new JSONObject(builder.toString());
		JSONArray r = (JSONArray) json.getJSONObject("responseData").get("results");
		
		for(int i = 0; i < r.length(); i++){
			String content = r.getJSONObject(i).getString("content");
			String publishedDate = r.getJSONObject(i).getString("publishedDate");
			String publisher = r.getJSONObject(i).getString("publisher");
			String title = r.getJSONObject(i).getString("title");
			String urlStr = r.getJSONObject(i).getString("unescapedUrl");
			
			GoogleNews gnews = new GoogleNews(title, urlStr, publisher, publishedDate, content);
			results.add(gnews);
		}
		
		return results;
	}
}
