<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="nis.wrapper.GoogleNewsWrapper"%>
<%@page import="nis.model.GoogleNews"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<ul>
<%
	for(GoogleNews gnews : GoogleNewsWrapper.getTopNews(request))
	{
%>
		<li>
			<a href="controller?url=<%= gnews.getUrl() %>&wrapper=LoadNewsWrapper">
				<%= gnews.getTitle() %>
			</a> - <%= gnews.getPublisher() %> (<%= gnews.getPublishedDate() %>)
			<p>
				<%= gnews.getContent() %>
			</p>
		</li>
<%
	}
%>
</ul>
</body>
</html>