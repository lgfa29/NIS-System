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
		background-color: #708090;
	}
	#logo{
		height: 50px;
		background-color: #fff;
	}
	#menu{
		/*background-color: Green;*/
	}
	#right{
		height: 100%;
		padding-left: 140px;
		/*background-color: purple;*/
	}
	#header{
		height: 50px;
		border-bottom: 1px solid #000;
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
		background-color: #6495ed;
	}
	#menu li:hover{
		background-color: #4682b4;
	}
	#menu a{
		text-decoration: none;
		color: #fff;
	}
	
	/* Header */
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
			  		<a href="controller?wrapper=TwitterWrapper" target="iframe" onclick="updateHeader('Twitter')"><li>Twitter</li></a>
			  		<a href="#" onclick="updateHeader('Wikipedia')"><li>Wikipedia</li></a>
	  			</ul>
			</div>
		</div>
		<div id="right">
			<div id="header">
				<h1><%= (String) session.getAttribute("query") %></h1>
				<p id="wrapper">&nbsp;</p>
			</div>
			<div id="content">
				<iframe name="iframe" style="width: 99.1%; height: 350px; border: 0;"></iframe>
			</div>
		</div>
	</div>
</body>
</html>