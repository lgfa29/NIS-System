<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="nis.model.Google"%>
<%@page import="nis.wrapper.GoogleWrapper"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" href="css/css.css" type="text/css" media="screen" />

</head>
<body>

<%
	String query = (String) request.getSession().getAttribute("query");
	String userIp = (String) request.getRemoteAddr();
	for(Google webpage : GoogleWrapper.getPages(query, userIp)) {
%>
		<a href="<%= webpage.getUrl() %>" target="_blank"><%= webpage.getTitle() %></a><br />
		<%= webpage.getContent() %>
		<br />
		<a href="http://<%= webpage.getVisibleUrl() %>" target="_blank"><%= webpage.getVisibleUrl() %></a> - 
		<a href="<%= webpage.getCachedUrl() %>" target="_blank">cached</a>
		<br /><br />
<%
	}
%>

</body>
</html>