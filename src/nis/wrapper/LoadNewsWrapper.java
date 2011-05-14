package nis.wrapper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoadNewsWrapper implements Wrapper {

	private final static String header = 
		"<script type=\"text/javascript\" src=\"js/jquery-1.4.2.js\"></script>\n" +
		"<script type=\"text/javascript\" src=\"js/fancybox/jquery.fancybox-1.3.4.pack.js\"></script>\n" +
		"<script type=\"text/javascript\" src=\"js/jquery.hotkeys.js\"></script>\n" +
		
		"<link rel=\"stylesheet\" href=\"js/fancybox/jquery.fancybox-1.3.4.css\" type=\"text/css\" media=\"screen\" />\n";
	
	private final static String fancybox = 
		"<script type=\"text/javascript\">\n" +
			"function init(){\n" +
				"jQuery(\"a#menu\").fancybox({\n" +
					"'type':'iframe',\n" + 
					"'width':650,\n" +
					"'height':400,\n" +
					"'scrolling':'no'\n" +
				"});\n" +
				
				"jQuery(document).bind('keydown', 'Ctrl+q',function (evt){\n" +
					"var txt = '';\n" +
					
				    "if (window.getSelection)\n" +
				        "txt = window.getSelection();\n" +
				    "else if (document.getSelection)\n" +
				        "txt = document.getSelection();\n" +
				    "else if (document.selection)\n" +
				        "txt = document.selection.createRange().text;\n" +
			        
					"jQuery.ajax({\n" +
						"type:\"GET\",\n" +
						"url:\"saveQuery\",\n" +
						"data:\"query=\"+txt,\n" +
						"success:function(){\n" +
							"jQuery(\"a#menu\").trigger('click');\n" +
						"}\n" +
					"});\n" +
				"});\n" +
			"}\n" +
			
			"jQuery(document).ready(init);\n" +
		"</script>\n" +
		
		"<div style=\"visibility: hidden\">\n" +
			"<a id=\"menu\" href=\"menu.jsp\">Menu</a>\n" +
		"</div>\n";
	
	@Override
	public void run(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		String newsUrl = req.getParameter("url");
		
		URL url = new URL(newsUrl);
		URLConnection urlc = url.openConnection();
		urlc.setRequestProperty("Accept", "*/*");
		urlc.setRequestProperty("User-Agent", "Mozilla/4.0");
		
		InputStream is = urlc.getInputStream();
		InputStreamReader ips = new InputStreamReader(is);
		BufferedReader in = new BufferedReader(ips);
		
		PrintWriter out = res.getWriter();
		
		String line;
		
		while((line = in.readLine()) != null){
			if(!(line.contains("jquery") || line.contains("jQuery")))
				out.println(line);
			
			if(line.contains("<head"))
				out.println(header);
			
			if(line.contains("<body"))
				out.println(fancybox);
		}
	}

}
