package nis.wrapper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import nis.model.NaverBlog;

/* naver API key
 * Key: 2cca953a836d26f29204c6221db19288
 * http://dev.naver.com/openapi/apis/search/blog
 *  *** not implemented yet ***
 */

public class NaverBlogWrapper implements Wrapper {

	//static String api_key = "2cca953a836d26f29204c6221db19288";
	//private static Document doc;
	
	public NaverBlogWrapper() {
		
	}
	
	@Override
	public void run(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		RequestDispatcher rd = req.getRequestDispatcher("/wrappers/naverblog.jsp");
		rd.forward(req, res);
	}

	private static Document naverblog_sendQuery(String searchText)	{
		String api_key = "2cca953a836d26f29204c6221db19288";
		Document doc;
		
		try {
			String address = "http://openapi.naver.com/search"
				+ "?key=" + api_key + "&target=blog" + "&query=" + searchText
				+ "&display=20" + "&start=1" + "&sort=sim";
			//System.out.println(address);
			URL url = new URL(address);
			URLConnection urlc = url.openConnection();
			urlc.setRequestProperty("Accept", "*/*");
			urlc.setRequestProperty("User-Agent", "Mozilla/4.0");
			InputStream is = urlc.getInputStream();
			InputStreamReader ips = new InputStreamReader(is, "UTF-8");
			BufferedReader in = new BufferedReader(ips);
			String line;
			String queryResult = new String();
			while ((line=in.readLine()) != null) {
				//
				queryResult += line;				
				//
			}
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Reader reader = new StringReader(queryResult);
			InputSource inputsource = new InputSource(reader);
			//System.out.println(queryResult);
			doc = builder.parse(inputsource);
			return doc;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static List<NaverBlog> naverblog_buildResultPage(Document doc) {
		//String HTML = new String();
		//HTML += "<HTML><BODY>";
		
		List<NaverBlog> blogs = new ArrayList<NaverBlog>();
		
		NodeList blogList = doc.getElementsByTagName("item");
		int nodeCount = blogList.getLength();
		NodeList[] items = new NodeList[nodeCount];
		
		for (int i = 0;i < nodeCount;i++) {
			items[i] = blogList.item(i).getChildNodes();	
		}
		for (int i = 0;i < nodeCount;i++) {
			//HTML += "<FONT SIZE = 4>";
			//HTML += "<a href=\"" + items[i].item(1).getTextContent() + "\">";
			//HTML += items[i].item(0).getTextContent();
			//HTML += "</a></FONT><br>";
			//HTML += items[i].item(2).getTextContent() + "<br>";
			//HTML += "<a href=\"" + items[i].item(4).getTextContent() + "\">";
			//HTML += "<font size=2 color=\"SteelBlue\">";
			//HTML += items[i].item(3).getTextContent() + " - ";
			//HTML += items[i].item(4).getTextContent() + "</font>";
			// HTML += "</a>";
			//HTML += "<br><br>";
			
			blogs.add(
				new NaverBlog(
					items[i].item(1).getTextContent(),
					items[i].item(0).getTextContent(),
					items[i].item(2).getTextContent(),
					items[i].item(3).getTextContent(),
					items[i].item(4).getTextContent())
				);
		}
		//HTML += "</tr>";
		
		//HTML += "</BODY></HTML>";
		
		//System.out.println(HTML);
		
		return blogs;
	}
	
	public static List<NaverBlog> getBlogs(String query){
		return naverblog_buildResultPage(naverblog_sendQuery(query));
	}
}