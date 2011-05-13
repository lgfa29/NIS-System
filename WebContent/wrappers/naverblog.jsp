<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="nis.model.NaverBlog"%>
<%@page import="nis.wrapper.NaverBlogWrapper"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<link rel="stylesheet" href="css.css" type="text/css" media="screen" />

</head>
<body>

<%
	String query = (String) request.getSession().getAttribute("query");
	for(NaverBlog blog : NaverBlogWrapper.getBlogs(query)){
%>
		<a href="<%= blog.getUrl() %>" target="_blank"><%= blog.getTitle() %></a><br />
		<%= blog.getContent() %><br />
		<%= blog.getName() %> - <a href="<%= blog.getBlogUrl() %>"><%= blog.getBlogUrl() %></a>
		<br /><br />
<%
	}
%>

</body>
</html>