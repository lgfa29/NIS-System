package nis.wrapper;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import nis.model.Wikipedia;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kim Nam Hyeok
 */
public class WikipediaWrapper implements Wrapper {

    //String keyword, HTML;
    
    @Override
	public void run(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
    	RequestDispatcher rd = req.getRequestDispatcher("/wrappers/wikipedia.jsp");
		rd.forward(req, res);
	}

    private static String keywordNormalize(String keyword)
    {
        int s, e;

        for(s = 0; s < keyword.length() - 1 && keyword.charAt( s ) == ' '; s++);
        for(e = keyword.length() - 1; e > 1 && keyword.charAt( e ) == ' '; e++);
        keyword = keyword.substring( s , e + 1 );
        keyword = keyword.replace( ' ' , '&' );
        return keyword;
    }

    private static String getAddress(String keyword)
    {
        String res;
        res = "http://en.wikipedia.org/w/api.php?action=opensearch&search=";
        res += keyword;
        res += "&format=xml";
        return res;
    }

    private static String getXML( String address )
    {
        String resXML;

        resXML = "";
        try
        {
            URL url = new URL( address );
            URLConnection urlc = url.openConnection();
            urlc.setRequestProperty( "Accept" , "*/*" );
            urlc.setRequestProperty( "User-Agent" , "Mozilla/4.0" );
            InputStream is = urlc.getInputStream();
            InputStreamReader ips = new InputStreamReader( is );
            BufferedReader in = new BufferedReader( ips );
            String line;
            while( (line = in.readLine()) != null )
                resXML += line + "\r\n";
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
        return resXML;
    }
/*
    public void initializeHTML( )
    {
        HTML = "<HTML>\r\n";
        HTML += "<HEAD>\r\n";
        HTML += "<TITLE>Wikipedia Wrapper</TITLE>\r\n";
        HTML += "</HEAD>\r\n";
        HTML += "<BODY>\r\n";
    }
*/
    public static boolean isUsualChar( char a )
    {
        if(a >= '0' && a <= '9') return true;
        if(a >= 'A' && a <= 'Z') return true;
        if(a >= 'a' && a <= 'z') return true;
        if(a == '(' || a == ')') return true;

        return false;
    }
    
    private static String sanitizeString(String input){
    	String ht = "";
    	for(int i = 0; i < input.length(); i++)
        {
            if(!isUsualChar( input.charAt( i ) ))
                ht += "&#" + (int)input.charAt( i ) + ";";
            else ht += input.charAt( i );
        }
    	
    	return ht;
    }
/*    
    public void appendHTML( String title , String description , String url )
    {
        int i;
        String ht, hd;

        ht = ""; hd = "";
        for(i = 0; i < title.length(); i++)
        {
            if(!isUsualChar( title.charAt( i ) ))
                ht += "&#" + (int)title.charAt( i ) + ";";
            else ht += title.charAt( i );
        }
        for(i = 0; i < description.length(); i++)
        {
            if(!isUsualChar( description.charAt( i ) ))
                hd += "&#" + (int)description.charAt( i ) + ";";
            else hd += description.charAt( i );
        }
        HTML += "<A href=\"" + url + "\">" + ht + "</A> <BR>\r\n";
        HTML += hd + "<BR>\r\n";
        HTML += "<A href=\"" + url + "\">" + url + "</A> <BR>\r\n";
        HTML += "<BR>\r\n";
    }

    public void finishHTML( )
    {
        HTML += "</BODY>\r\n";
        HTML += "</HTML>\r\n";
    }
*/
    
    private static List<Wikipedia> parseXML( String XML )
    {
    	List<Wikipedia> wikis = new ArrayList<Wikipedia>();
        try
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance ( ) ;
            DocumentBuilder db = dbf.newDocumentBuilder ( ) ;

            Document doc = db.parse( new InputSource( new java.io.StringReader( XML ) ) );
            doc.getDocumentElement().normalize();
            NodeList nodeLst = doc.getElementsByTagName("Item");

            for (int s = 0; s < nodeLst.getLength(); s++)
            {
                Node fstNode = nodeLst.item(s);

                if (fstNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    String text, desc, url;
                    Wikipedia wiki;
                    Element fstElmnt = (Element) fstNode;

                    NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("Text");
                    Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
                    NodeList fstNm = fstNmElmnt.getChildNodes();
                    text = ((Node) fstNm.item(0)).getNodeValue();

                    NodeList scndNmElmntLst = fstElmnt.getElementsByTagName("Description");
                    Element scndNmElmnt = (Element) scndNmElmntLst.item(0);
                    NodeList scndNm = scndNmElmnt.getChildNodes();
                    desc = ((Node) scndNm.item(0)).getNodeValue();

                    NodeList lstNmElmntLst = fstElmnt.getElementsByTagName("Url");
                    Element lstNmElmnt = (Element) lstNmElmntLst.item(0);
                    NodeList lstNm = lstNmElmnt.getChildNodes();
                    url = ((Node) lstNm.item(0)).getNodeValue();
                    
                    wiki = new Wikipedia(sanitizeString(text), sanitizeString(desc), url);
                    
                    if(!(desc.indexOf( "may refer to" ) >= 0 && desc.indexOf( "may refer to" ) < desc.length()))
                    	wikis.add(wiki);
                        //appendHTML( text , desc , url );
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return wikis;
    }
/*
    public void makeFile( String Filename )
    {
        try
        {
            PrintWriter pw = new PrintWriter( new FileWriter( Filename ) );
            pw.println( HTML );
            pw.close();
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
    }
*/
    public static List<Wikipedia> getWikis( String keyword/* , String Filename */)
    {
        String address, XML;

        //this.keyword = keyword;
        address = getAddress(keywordNormalize(keyword));
        XML = getXML( address );
        //initializeHTML();
        return parseXML( XML );
        //finishHTML();
        //makeFile( Filename );
    }
}
