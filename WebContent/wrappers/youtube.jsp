<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="nis.model.Youtube"%>
<%@page import="nis.wrapper.YoutubeWrapper"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/css.css" type="text/css" media="screen" />
</head>
<body>
<%
	String query = (String) request.getSession().getAttribute("query");
	for(Youtube video : YoutubeWrapper.getVideos(query)){
%>
		<table>
			<tr>
				<td style="padding-top: 6px;vertical-align: top;">
					<a href="<%= video.getUrl() %>" target="_blank">
						<img src="<%= video.getImageUrl() %>" alt="<%= video.getTitle() %>" />
					</a>
				</td>
				<td style="vertical-align: top;">
					<b><a href="<%= video.getUrl() %>" target="_blank"><%= video.getTitle() %></a></b><br />
					<%= video.getDescription() %><br />
					<span style="font-size: 8pt; color: #888;">
						by 
						<a href="http://www.youtube.com/user/<%= video.getUploader() %>" target="_blank">
							<%= video.getUploader() %></a>
						| <b style="color: #666;"><%= video.getView() %> views</b>
					</span>
				</td>
			</tr>
		</table>
<%
	}
%>
</body>
</html>