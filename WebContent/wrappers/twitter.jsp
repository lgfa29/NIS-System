<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="nis.wrapper.TwitterWrapper"%>
<%@page import="twitter4j.Tweet"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<link rel="stylesheet" href="css.css" type="text/css" media="screen" />

</head>
<body style="margin: 0">
	<table>
<%
	String query = (String) session.getAttribute("query");
	for(Tweet tweet : TwitterWrapper.search(query)){
%>
			<tr>
				<td>
					<img src="<%= tweet.getProfileImageUrl() %>" style="width: 48px; height: 48px;" />
				</td>
				<td>
					<a href="http://twitter.com/<%= tweet.getFromUser() %>" target="_blank"><%= tweet.getFromUser() %></a>
					<%= TwitterWrapper.getParsedTweet(tweet.getText(), query) %>
				</td>
			</tr>
<%
	}
%>
	</table>
</body>
</html>