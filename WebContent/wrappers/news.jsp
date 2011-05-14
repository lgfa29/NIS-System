<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<%@page import="nis.model.News"%>
<%@page import="nis.wrapper.GuardianWrapper"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<link href="css/styles.css" rel="stylesheet" type="text/css" />

<style type="text/css">
	body{
		margin: 0 5%;
	}
	
	.trail p {
		margin-top: 0; 
		margin-bottom: 5px; 
		font-size: 11pt; 
		color: #444;
	}
	
	li {
		font-size: 10pt;
		font-weight: bold;
		color: #444;
		margin-bottom: 20px;
		list-style-type: none;
	}
	
	#buttons a:hover{
		background: none;
	}
</style>
</head>
<body>
<div id="main">
<div id="header">
	<div id="logo"></div>
	<div id="buttons" style="margin-top:0;">
        <a href="index.html" style="margin: 0; padding: 0;"><ul>NEWS Information System</ul></a>
    </div></div>
	<div id="content" style="padding: 10px;">
		<form action="controller" method="get" style="margin-bottom: 20px;">
			<input type="text" name="newsQuery" size="50px" style="font-size: 15pt; font-family: serif;" value="<%= request.getSession().getAttribute("newsQuery") %>" />
			<input type="submit" value="News Search" style="font-size: 15pt; font-family:times;" />
			<input type ="hidden" name="wrapper" value="GuardianWrapper" />
		</form>
		<ul>
		<%
			String query = (String) request.getSession().getAttribute("newsQuery");
			for(News news : GuardianWrapper.getNews(query)){
		%>
				<li>
					<h2 style="margin: 0; font-weight: normal; font-size: 14pt;">
						<a href="controller?url=<%= news.getUrl() %>&wrapper=LoadNewsWrapper" style="text-decoration: none;">
							<%= news.getHeadline() %>
						</a>
					</h2>
					<table>
						<tr>
							<td style="vertical-align: top;">
							<% if(!news.getThumbnail().equals("")){ %>
								<div style="border: 1px solid #666; padding: 2px; background-color: #fff;">
									<img src="<%= news.getThumbnail() %>" style="border: 1px solid #666" />
								</div>
							<%} %>
							</td>
							<td class="trail" style="vertical-align: top; text-align: left;">
								<p style="">
									<%= news.getTrailText() %>
								</p>
								<span style="font-size: 8pt;">
									<%= news.getSection() %> - <%= news.getAuthor() %> - <%= news.getDate() %>
								</span>
							</td>
						</tr>
					</table>
				</li>
		<%
			}
		%>
		</ul>
	</div>
</div>
</body>
</html>