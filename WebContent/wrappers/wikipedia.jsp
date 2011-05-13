<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="nis.model.Wikipedia"%>
<%@page import="nis.wrapper.WikipediaWrapper"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" href="css/css.css" type="text/css" media="screen" />

</head>
<body>
<%
	String query = (String) session.getAttribute("query");
	for(Wikipedia wiki : WikipediaWrapper.getWikis(query)){
%>
		<a href="<%= wiki.getUrl() %>" target="_blank"><%= wiki.getTitle() %></a><br />
		<%= wiki.getDescription() %><br />
		<a href="<%= wiki.getUrl() %>" target="_blank"><%= wiki.getUrl() %></a><br />
		<br />
<%
	}
%>

</body>
</html>