<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="nis.wrapper.GoogleNewsWrapper"%>
<%@page import="nis.model.GoogleNews"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" href="css/css.css" type="text/css" media="screen" />

<style type="text/css">
body{
	margin: 0 5%;
}
</style>
</head>
<body>

<div id="header" style="margin-left:-4%;">
	<h1>NIS - News Integrating System</h1>
</div>

<ol>
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
</ol>
</body>
</html>