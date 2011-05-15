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

import nis.model.News;

import org.json.JSONArray;
import org.json.JSONObject;

public class GuardianWrapper implements Wrapper {
	
	@Override
	public void run(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		req.getSession().setAttribute("newsQuery", req.getParameter("newsQuery"));
		RequestDispatcher rd = req.getRequestDispatcher("/wrappers/news.jsp");
		rd.forward(req, res);
	}
	
	private static String parseDate(String date){
		String day = date.substring(0, 10);
		String hour = date.substring(11, 19);
		
		return day.split("-")[2] + "/" + day.split("-")[1] + "/" + day.split("-")[0] + " (" + hour + ")";  
	}
	
	public static List<News> getNews(String query) throws Exception {
		List<News> results = new ArrayList<News>();
		
		URL url = new URL(
				"http://content.guardianapis.com/search?"
						+ "q=" + query.replaceAll(" ", "+") + "&page-size=30&format=json&show-fields=all");
		URLConnection connection = url.openConnection();

		String line;
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
		while ((line = reader.readLine()) != null) {
			builder.append(line);
		}

		JSONObject json = new JSONObject(builder.toString());
		JSONArray r = (JSONArray) json.getJSONObject("response").get("results");
		
		for(int i = 0; i < r.length(); i++){
			String weburl="", date="", section="", headline="", trail="", thumb="", by="";
			
			try{ weburl = r.getJSONObject(i).getString("webUrl"); }catch (Exception e) {}
			try{ date = r.getJSONObject(i).getString("webPublicationDate"); }catch (Exception e) {}
			try{ section = r.getJSONObject(i).getString("sectionName"); }catch (Exception e) {}
			try{ headline = r.getJSONObject(i).getJSONObject("fields").getString("headline"); }catch (Exception e) {}
			try{ trail = r.getJSONObject(i).getJSONObject("fields").getString("trailText"); }catch (Exception e) {}
			try{ thumb = r.getJSONObject(i).getJSONObject("fields").getString("thumbnail"); }catch (Exception e) {}
			try{ by = r.getJSONObject(i).getJSONObject("fields").getString("byline"); }catch (Exception e) {}
			
			
			results.add(
				new News(
					weburl, 
					parseDate(date), 
					section, 
					headline,
					trail, 
					thumb, 
					by
				)
			);
		}
		
		return results;
	}
}
