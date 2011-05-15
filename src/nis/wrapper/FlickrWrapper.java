package nis.wrapper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import nis.model.Flickr;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/* flickr API key
 * Key: 81851df9e2e69fecd1d937156495bdb2
 * Secret: 77c79467b6f1b451 (not used)
 * Reference:
 * http://www.flickr.com/services/api/
 * http://www.flickr.com/services/api/flickr.photos.search.html
 * http://www.flickr.com/services/api/misc.urls.html
 */


public class FlickrWrapper implements Wrapper {
	
	@Override
	public void run(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		RequestDispatcher rd = req.getRequestDispatcher("/wrappers/flickr.jsp");
		rd.forward(req, res);
	}
	
	private static String getResponse(String soap, String host, String path, String soapAction) {
		try{
			Socket sk = new Socket(host, 80);
			OutputStream os = sk.getOutputStream();
			PrintWriter pw = new PrintWriter(os);
			
			pw.println("POST " + path + " HTTP/1.0");
			pw.println("Host: " + host);
			pw.println("Accept: text/*");
			pw.println("Content-type: text/xml");
			pw.println("SOAPAction: " + soapAction);
			pw.println("Content-Length: " + soap.length());
			pw.println();
			pw.write(soap);
			pw.flush();
			
			InputStream is = sk.getInputStream();
			InputStreamReader ips = new InputStreamReader(is);
			BufferedReader in = new BufferedReader(ips);
			
			String line;
			StringBuilder strBuilder = new StringBuilder();
			
			while((line = in.readLine()) != null)
				strBuilder.append(line + "\n");
			
			return strBuilder.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	private static Document getXMLResult(String query) {
		final String SOAP_MESSAGE =
			"<s:Envelope " +
				"xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\" " +
				"xmlns:xsi=\"http://www.w3.org/1999/XMLSchema-instance\" " +
				"xmlns:xsd=\"http://www.w3.org/1999/XMLSchema\" " +
			">" +
				"<s:Body>" +
					"<x:FlickrRequest xmlns:x=\"urn:flickr\">" +
						"<method>flickr.photos.search</method>" +
						"<api_key>81851df9e2e69fecd1d937156495bdb2</api_key>" +
						"<text>" + query +"</text>" +
						"<per_page>50</per_page>" +
					"</x:FlickrRequest>" +
				"</s:Body>" +
			"</s:Envelope>";
		Document doc;
		
		String xml = getResponse(SOAP_MESSAGE, "api.flickr.com", "/services/soap/", "flickr.photos.search");
		xml = xml.substring(xml.indexOf("<?xml"));
		xml = xml.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&quot;", "\"");

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Reader reader = new StringReader(xml);
			InputSource inputsource = new InputSource(reader);			
			doc = builder.parse(inputsource);
			
			return doc;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<Flickr> getPhotosSOAP(String query) {
		List<Flickr> result = new ArrayList<Flickr>();
		
		Document doc = getXMLResult(query);
		
		NodeList photos = doc.getElementsByTagName("photo");
		for(int i = 0; i < photos.getLength(); i++){
			Node photo = photos.item(i);
			
			String farm = photo.getAttributes().getNamedItem("farm").getTextContent();
			String server = photo.getAttributes().getNamedItem("server").getTextContent();
			String id = photo.getAttributes().getNamedItem("id").getTextContent();
			String secret = photo.getAttributes().getNamedItem("secret").getTextContent();
			String owner = photo.getAttributes().getNamedItem("owner").getTextContent();
			
			result.add(new Flickr(
					"http://farm" + farm + ".static.flickr.com/" + server + "/" + id + "_" + secret + "_t.jpg", 
					"http://www.flickr.com/photos/" + owner + "/" + id));
		}
		
		return result;
	}
	
	
	
	
	//static String api_key = "81851df9e2e69fecd1d937156495bdb2";

	//private static NodeList rsp;	
	//private static Document doc;
	
	public FlickrWrapper() {
		try {
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private static Document flickr_sendQuery(String searchText)	{
		Document doc;
		NodeList rsp;
		String api_key = "81851df9e2e69fecd1d937156495bdb2";
		
		try {
			String address = "http://api.flickr.com/services/rest/?method=flickr.photos.search"
				+ "&api_key=" + api_key + "&text=" + searchText.replaceAll(" ", "%20")
				+ "&per_page=50" + "&sort=relevence&extras=url_z,url_t";
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
				// Write returned XML contents into string
				queryResult += line;
				//
			}

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Reader reader = new StringReader(queryResult);
			InputSource inputsource = new InputSource(reader);			
			doc = builder.parse(inputsource);			
			rsp = doc.getElementsByTagName("rsp");			
			
			NamedNodeMap nnodemap = rsp.item(0).getAttributes();
			// Check whether we got suitable result by checking stat attribute.
			if (nnodemap.getNamedItem("stat").getTextContent().equals("ok"))
				return doc;
			else
				return null;				
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static List<Flickr> flickr_buildResultPage(Document doc) {
		//String HTML = new String();
		//HTML += "<HTML><BODY>";
		//HTML += "<table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">";
		
		NodeList photoList = doc.getElementsByTagName("photo");
		int nodeCount = photoList.getLength();
		NamedNodeMap[] photos = new NamedNodeMap[nodeCount];
		//String[] photoThumbs = new String[nodeCount];
		//String[] photoMedium = new String[nodeCount];
		
		List<Flickr> photosArray = new ArrayList<Flickr>();
		
		for (int i = 0;i < nodeCount;i++) {
			photos[i] = photoList.item(i).getAttributes();		
		}
		for (int i = 0;i < nodeCount;i++) {
			//if (i == 0)
			//	HTML += "<tr height=110>";
			//else if ((i % 6 == 0) && (i != nodeCount - 1))
			//	HTML += "</tr><tr height=110>";
			// Sometimes url_t or url_z tag does not exist, so then, null is returned.
			// We should check them and ignore them.
			if (photos[i].getNamedItem("url_t") == null)
				continue;
			if (photos[i].getNamedItem("url_z") == null)
				continue;
			//photoThumbs[i] = photos[i].getNamedItem("url_t").getTextContent();
			//photoMedium[i] = photos[i].getNamedItem("url_z").getTextContent();
			
			photosArray.add(
				new Flickr(
					photos[i].getNamedItem("url_t").getTextContent(), 
					photos[i].getNamedItem("url_z").getTextContent())
			);
			
			//HTML += "<td width=110 align=\"center\"><a href=\"" + photoMedium[i] + 
			//		"\"><img src=\"" + photoThumbs[i] + "\"></a></td>";			
		}
		//HTML += "</tr>";
		
		//HTML += "</BODY></HTML>";
		
		//System.out.println(HTML);
		
		//return HTML;
		return photosArray;
	}
	
	public static List<Flickr> getPhotos(String query){
		return flickr_buildResultPage(flickr_sendQuery(query));
	}
}