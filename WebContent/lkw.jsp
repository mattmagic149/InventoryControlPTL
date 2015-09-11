<%@page import="database.Truck"%>

<%
	Truck truck = (Truck)session.getAttribute("current_truck");
		
%>

<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  
  <link rel="stylesheet" href="css/style.css" type="text/css" media="screen" />
  <link rel="stylesheet" href="css/dialog.css" type="text/css" media="screen" />
  <link rel="stylesheet" href="css/product_details.css" type="text/css" media="screen" />
  
  <script src="js/jquery-1.10.2.min.js" type="text/javascript"></script>
  <script src="js/dialog.js" type="text/javascript"></script>
  <script src="js/product_details.js" type="text/javascript"></script>

  <title>PTL - LKW</title>
    
</head>
<body>
	<div id="distance"></div>
	<div id="wrapper">
    <a href="Logout"><button id="logout" class="color_discreet">Logout</button></a>
    <a href="Welcome"><button id="back" class="color_discreet">&#060&#060 Übersicht</button></a>
    <button id="edit" class="color" >Bearbeiten</button>
	
	<div id="product_details_container">
		<div id="product">
			<div class="description">Produkt ID:</div>
			<div class="value" id="product_id"></div>
			<div class="description">Produktname:</div>
			<div class="value"></div>
			<div class="description">Beschreibung:</div>
			<div class="value"></div>
			<div class="description">Mindestmenge im Lager:</div>
			<div class="value"></div>
			<div class="description">Lagerbestand:</div>
			<div class="value"></div>
			
			<button id="ingoing" class="color">Eingang<img src="img/lager_icon_in.png"/></button>
			<button id="outgoing" class="color">Ausgang<img src="img/lager_icon_out.png"/></button>
		</div>
	</div>
	<button id="ok" class="color">OK</button>
	
    <footer></footer>
    			
	</div>

</body>
</html>
