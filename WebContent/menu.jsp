<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<style type="text/css">
	#container {
		height: 380px;
	}
	#content {
		float: left;
	  	border-left: solid 1px #AAA;
	  	width: 500px;
	  	height: 100%;
	}
	#navbar {
	  	float: left;
	  	width: 100px;
	  	margin-right: 10px;
	}
</style>

<script type="text/javascript">
	function updateHeader(wrapper){
		document.getElementById("wrapper").innerHTML = "From <b>" + wrapper + "</b>";
	}
</script>

<link rel="stylesheet" href="css.css" type="text/css" media="screen" />

<title>Insert title here</title>
</head>
<body style="margin: 0;">

	<div style="float:left;width:100px;">
		<ul style="list-style-type: none; padding: 0">
	  		<li><a href="controller?wrapper=TwitterWrapper" target="iframe" onclick="updateHeader('Twitter')">Twitter</a></li>
	  		<li><a href="#" target="iframe" onclick="updateHeader('Wikipedia')">Wikipedia</a></li>
	  	</ul>
	</div>
	<div id="container" style="float:left;">
		<div id="header">
			<div style="margin-bottom: 10px;">
				<h2 style="margin:0;"><%= (String) session.getAttribute("query") %></h2>
				<p style="margin:0;" id="wrapper">&nbsp;</p>
			</div>
		</div>
		<div id="content">
			<iframe name="iframe" frameborder="0" style="width: 100%; height: 100%"></iframe>
		</div>
	</div>
	
	<!-- 
	<div id="container">
	  <div id="navbar">
	  	<ul style="list-style-type: none; padding: 0">
	  		<li><a href="controller?wrapper=TwitterWrapper" target="iframe" onclick="updateHeader('Twitter')">Twitter</a></li>
	  	</ul>
	  </div>
	  <div id="content">
		<iframe name="iframe" frameborder="0" style="width: 100%; height: 100%"></iframe>
		</div>
	</div> -->
</body>
</html>