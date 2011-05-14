package nis.wrapper;

import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gdata.client.youtube.YouTubeQuery;
import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.VideoFeed;
import com.google.gdata.data.youtube.YouTubeMediaGroup;

import nis.model.Youtube;

public class YoutubeWrapper implements Wrapper
{
	@Override
	public void run(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		RequestDispatcher rd = req.getRequestDispatcher("/wrappers/youtube.jsp");
		rd.forward(req, res);
	}

    public static List<Youtube> getVideos(String keyword)
    {
    	List<Youtube> array = new ArrayList<Youtube>();
    	final String YOUTUBE_URL = "http://gdata.youtube.com/feeds/api/videos";
    	
    	try{
	    	YouTubeService service = new YouTubeService("NIS");
	    	service.setConnectTimeout(2000);
	    	YouTubeQuery query = new YouTubeQuery(new URL(YOUTUBE_URL));
	    	
	    	query.setFullTextQuery(keyword);
	    	
	    	VideoFeed videoFeed = service.query(query, VideoFeed.class);
	    	for(VideoEntry video : videoFeed.getEntries()){
	    		YouTubeMediaGroup mediaGroup = video.getMediaGroup();
	    		
	    		array.add(
	    			new Youtube(
	    				mediaGroup.getPlayer().getUrl(), 
	    				mediaGroup.getThumbnails().get(0).getUrl(), 
	    				video.getTitle().getPlainText(), 
	    				mediaGroup.getDescription().getPlainTextContent(), 
	    				NumberFormat.getInstance(Locale.ENGLISH).format(video.getStatistics().getViewCount()), 
	    				mediaGroup.getUploader())
	    		);
	    	}
    	} catch (Exception e) {
    		e.printStackTrace();
		}
    	
    	return array;
    }
}
