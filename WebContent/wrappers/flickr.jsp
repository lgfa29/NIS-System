<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="nis.wrapper.FlickrWrapper"%>
<%@page import="nis.model.Flickr"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
	String query = (String) request.getSession().getAttribute("query");
	for(Flickr flickr : FlickrWrapper.getPhotos(query)) {
%>
		<div style="display: inline;">
			<a href="<%= flickr.getMediumURL() %>" target="_blank">
				<img src="<%= flickr.getThumbURL() %>" />
			</a>
		</div>
<%
	}
%>
</body>
</html>