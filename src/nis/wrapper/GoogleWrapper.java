package nis.wrapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import nis.model.Google;

/*
 * Google AJAX API (JSON)
 * Reference:
 * http://ivira.name/blog/2008/04/25/using-google-search-api-from-java/
 * http://code.google.com/apis/blogsearch/v1/jsondevguide.html
 * http://json.org/java/
 */

public class GoogleWrapper implements Wrapper{
	
	//private static String endpointURL = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=test";
	
	//https://ajax.googleapis.com/ajax/services/search/blogs?v=1.0
	//http://ajax.googleapis.com/ajax/services/search/web?v=1.0
	
	// This api key is temporary. It doesn't work I think.
	//private static String api_key = "ABQIAAAAP1XL8Yhp9bEpLQTz4ER6KRRD1n4MOWjGUMHzB8_565bG7c1J0xR3M7K1mMsJWe1BHOlxfjJM0EENng";
	//private static String user_ip = "110.76.101.253";

	//private static JSONObject jsonResult;	
	
	public GoogleWrapper() {
		
	}
	
	@Override
	public void run(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		RequestDispatcher rd = req.getRequestDispatcher("/wrappers/google.jsp");
		rd.forward(req, res);
	}
		
	private static JSONObject googleweb_sendQuery(String searchText, String user_ip) {
		try {
			
			String api_key = "ABQIAAAAP1XL8Yhp9bEpLQTz4ER6KRRD1n4MOWjGUMHzB8_565bG7c1J0xR3M7K1mMsJWe1BHOlxfjJM0EENng";
			
			String address = "https://ajax.googleapis.com/ajax/services/search/web?v=1.0"
				+ "&q=" + searchText + "&key=" + api_key + "&userip=" + user_ip
				+ "&hl=en&rsz=8";
			//System.out.println(address);
			URLConnection uc = new URL(address).openConnection();
			HttpURLConnection connection = (HttpURLConnection) uc;
			connection.setDoOutput(true);
			connection.setRequestMethod("GET");
			connection.connect();
			String line;
			StringBuilder sbuilder = new StringBuilder();
			InputStreamReader is = new InputStreamReader(connection.getInputStream(), "UTF-8");
			BufferedReader reader = new BufferedReader(is);
			while((line = reader.readLine()) != null) {
				sbuilder.append(line);
			}
			JSONObject jsonResult = new JSONObject(sbuilder.toString());
			//System.out.println(jsonResult.toString());		
			return jsonResult;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static List<Google> googleweb_buildResultPage(JSONObject jsonResult) {
		try {
			//String HTML = new String();
			//HTML += "<HTML><BODY>";

			List<Google> result = new ArrayList<Google>();
			JSONObject jsonResData = jsonResult.getJSONObject("responseData");
			JSONArray jsonResults = jsonResData.getJSONArray("results");
			int jsonResultsLength = jsonResults.length();
			for (int i = 0;i < jsonResultsLength;i++) {
				JSONObject iVal = jsonResults.getJSONObject(i);
				//HTML += "<FONT SIZE = 4>";
				//HTML += "<a href=\"" + iVal.getString("url") + "\">";
				//HTML += iVal.getString("title");
				//HTML += "</a></FONT><br>";
				//HTML += iVal.getString("content") + "<br>";
				//HTML += "<font size=2 color=\"SteelBlue\">";
				//HTML += iVal.getString("visibleUrl") + " - ";
				//HTML += "<a href=\"" + iVal.getString("cacheUrl") + "\">";
				//HTML += "cached</font></a>";
				//HTML += "<br><br>";
				result.add(
					new Google(
						iVal.getString("url"),
						iVal.getString("title"),
						iVal.getString("content"),
						iVal.getString("visibleUrl"),
						iVal.getString("cacheUrl"))
					);
			}
			//System.out.println("------------------");
			//System.out.println(jsonResults.toString());	
			//System.out.println("------------------");	
			
			//HTML += "</BODY></HTML>";
			//System.out.println(HTML);
			return result;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<Google> getPages(String query, String userIp){
		return googleweb_buildResultPage(googleweb_sendQuery(query, userIp));
	}
}