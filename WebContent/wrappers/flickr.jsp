<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="nis.wrapper.FlickrWrapper"%>
<%@page import="nis.model.Flickr"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	String query = (String) request.getSession().getAttribute("query");
	for(Flickr flickr : FlickrWrapper.getPhotosSOAP(query)) {
%>
		<div style="display: inline;">
			<a href="<%= flickr.getMediumURL() %>" target="_blank">
				<img src="<%= flickr.getThumbURL() %>"  style="border: 1px solid #666; padding: 3px;"/>
			</a>
		</div>
<%
	}
%>
</body>
</html>