package nis.wrapper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoadNewsWrapper implements Wrapper {

	private final static String header = 
		"<script type=\"text/javascript\" src=\"js/jquery-1.4.2.js\"></script>\n" +
		"<script type=\"text/javascript\"> var myjQuery = $.noConflict(true); </script>" +
		"<script type=\"text/javascript\" src=\"js/fancybox/jquery.fancybox-1.3.4.pack.js\"></script>\n" +
		"<script type=\"text/javascript\" src=\"js/jquery.hotkeys.js\"></script>\n" +
		
		"<link rel=\"stylesheet\" href=\"js/fancybox/jquery.fancybox-1.3.4.css\" type=\"text/css\" media=\"screen\" />\n" +
		"<style type=\"text/css\">\n" +
			"#nis_footer {\n" +
				"background-color: #cdcdcd;\n" +
				"text-align: center;\n" +
				"font-size:10pt;\n" +
				"color:#1380BA;\n" +
				"font-family:Verdana;\n" +
				"padding-top: 5px;\n" +
				"width: 100%;\n" +
				"position:fixed;\n" +
				"left: 0px;\n" +
				"bottom: 0px;\n" +
				"z-index: 1000;\n" +
				"height: 20px;\n" +
				"}\n" +
		"</style>"+
	
		"<script type=\"text/javascript\">\n" +
			"function init(){\n" +
				"myjQuery(\"a#menu\").fancybox({\n" +
					"'type':'iframe',\n" + 
					"'width':650,\n" +
					"'height':400,\n" +
					"'scrolling':'no'\n" +
				"});\n" +
				
				"myjQuery(document).bind('keydown', 'Ctrl+q',function (evt){\n" +
					"var txt = '';\n" +
					
				    "if (window.getSelection)\n" +
				        "txt = window.getSelection();\n" +
				    "else if (document.getSelection)\n" +
				        "txt = document.getSelection();\n" +
				    "else if (document.selection)\n" +
				        "txt = document.selection.createRange().text;\n" +
			        
					"myjQuery.ajax({\n" +
						"type:\"GET\",\n" +
						"url:\"saveQuery\",\n" +
						"data:\"query=\"+txt,\n" +
						"success:function(){\n" +
							"myjQuery(\"a#menu\").trigger('click');\n" +
						"}\n" +
					"});\n" +
				"});\n" +
			"}\n" +
			
			"myjQuery(document).ready(init);\n" +
		"</script>\n";
	
	private final static String fancybox = 
		"<div style=\"visibility: hidden\">\n" +
			"<a id=\"menu\" href=\"menu.jsp\">Menu</a>\n" +
		"</div>\n";
	
	private static String footer =
		"<div id=\"nis_footer\">Select a piece of the text and press <b>Ctrl + q</b></div>";
	
	@Override
	public void run(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		String newsUrl = req.getParameter("url");
		
		URL url = new URL(newsUrl);
		URLConnection urlc = url.openConnection();
		urlc.setRequestProperty("Accept", "*/*");
		urlc.setRequestProperty("User-Agent", "Mozilla/4.0");
		
		InputStream is = urlc.getInputStream();
		InputStreamReader ips = new InputStreamReader(is, "UTF-8");
		BufferedReader in = new BufferedReader(ips);
		
		//PrintWriter out = res.getWriter();
		PrintWriter out = new PrintWriter(new OutputStreamWriter(res.getOutputStream(), "UTF8"), true);
		
		
		String line;
		Boolean head=false, div=false, foot=false;
		
		while((line = in.readLine()) != null){
			if(line.contains("</body>") && !foot){
				out.println(footer);
				foot = true;
			}
			
			if(line.replaceAll("[ \t\r\n]", "").contains("<bodyclass=\"article\">"))
				out.println("<body class=\"article\" style=\"margin-bottom: 20px;\">");
			else
				out.println(line);
			
			if(line.contains("<head") && !head){
				out.println(header);
				head = true;
			}
			
			if(line.contains("<body") && !div){
				out.println(fancybox);
				div = true;
			}
		}
	}

}
