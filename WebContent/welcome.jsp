 <%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="database.Product"%>
<%@page import="database.Truck"%>
<%@page import="java.util.List"%>
<%@page import="org.javatuples.Pair"%>

<%
	int products_under_limit = (int)session.getAttribute("products_under_limit");
%>

<!DOCTYPE html>
<html>
<head>
  <link rel="icon" type="image/vnd.microsoft.icon" href="favicon.ico">
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <meta name="viewport" width="device-width">
    
 	<link rel="stylesheet" href="css/style.css" type="text/css" media="screen" />
	
  	<script src="js/jquery-1.10.2.min.js" type="text/javascript"></script>
  	<script src="js/main.js" type="text/javascript"></script>
  	<script src="js/dialog.js" type="text/javascript"></script>
	
	<title>PTL - Welcome</title>
    
</head>
<body>
	<div id="distance"></div>
	<div id="wrapper">
    <a href="Logout"><button id="logout" class="color_discreet">Logout</button></a>
    
    <h1>Willkommen<span></span>!</h1>
	<img src="img/logo.png" style="position: absolute; top: 10px; left: 10px; width: 200px;"/>

	<div id="admin_links">
      <a href="Scanning"><div class="color">Scannen<img src="img/scanning_icon.png"/></div></a>
      <a href="LkwsDetail" class="hideinmobile"><div class="color">LKWs<img src="img/lieferwagen_icon.png"/></div></a>
      <a href="LagerDetail" class="hideinmobile"><div class="color">Lager<img src="img/lager_icon.png"/></div></a>
      <a href="ProductsOverview" class="hideinmobile"><div class="color">Produkte<img src="img/product_icon.png"/></div></a>
      <a href="PrintLabels" class="hideinmobile"><div class="color">Labels drucken<img src="img/labels_icon.png"/></div></a>
<!--        <a href="PersonsDetail" class="hideinmobile"><div class="color">Personen<img src="img/personen_icon.png"/></div></a>-->
    </div>
    
    <% if(products_under_limit > 0) { %>
    <section id="pop_up_wrapper" class="hideinmobile">
  		<a href="ProductsOverview?show_under_minimum_only=true" title="Lagerbestand unter Mindestgröße"><div class="pop_up"><%=products_under_limit %></div></a>
    </section>   
    <% } %> 
	</div>
</body>
</html>
