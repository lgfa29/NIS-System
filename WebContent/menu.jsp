<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<style type="text/css">

/* Basic structure */
	#container{
		height: 400px;
		/*background-color: Red;*/
	}
	#left{
		float: left;
		width: 120px;
		height: 100%;
		margin-right: 10px;
		/*background-color: aqua;*/
	}
	#logo{
		height: 50px;
		/*background-color: Blue;*/
	}
	#menu{
		/*background-color: Green;*/
	}
	#right{
		height: 100%;
		padding-left: 130px;
		/*background-color: purple;*/
	}
	#header{
		height: 50px;
		/*background-color: fuchsia;*/
	}
	#content{
		height: 320px;
		/*background-color: lime;*/
	}

/* Elements */
	/* Logo */
	#logo h1{
		margin: 0;
		font-size: 32pt;
		text-align: center;
		position: relative;
		top: -2px;
	}
	
	/* Menu */
	#menu ul {
		margin: 0;
		list-style-type: none;
		padding: 0;
	}
	
	/* Menu Buttons */
	#menu li{
		font-size: 14pt;
		font-variant: small-caps;
		padding: 5px;
		margin: 2px;
		text-decoration: none;
		text-align: center;
		background-color: RoyalBlue;
	}
	#menu li:hover{
		background-color: Red;
	}
	#menu a{
		text-decoration: none;
		color: #fff;
	}
	
	#header h1, p{
		margin: 0;
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
	
	<div id="container">
		<div id="left">
			<div id="logo">
				<h1>NIS</h1>
			</div>
			<div id="menu">
				<ul>
			  		<li><a href="controller?wrapper=TwitterWrapper" target="iframe" onclick="updateHeader('Twitter')">Twitter</a></li>
			  		<li><a href="##" target="iframe" onclick="updateHeader('Wikipedia')">Wikipedia</a></li>
	  			</ul>
			</div>
		</div>
		<div id="right">
			<div id="header">
				<h1><%= (String) session.getAttribute("query") %></h1>
				<p id="wrapper">&nbsp;</p>
			</div>
			<div id="content">
				<iframe name="iframe" frameborder="0" style="width: 99.2%; height: 340px"></iframe>
			</div>
		</div>
	</div>
</body>
</html>