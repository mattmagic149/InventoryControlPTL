<%@page import="database.Product"%>

<%
	Product product = (Product)session.getAttribute("current_product");
	
	String product_id = "";
	String product_name = "";
	String product_description = "";
	String product_min_quantity = "";
	String product_lager_quantity = "";
	
	if (product != null) {
		product_id = product.getId();
		product_name = product.
		product_description = product.
		product_min_quantity = product.
		product_lager_quantity = product.
	}
	
%>

<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  
  <link rel="stylesheet" href="css/style.css" type="text/css" media="screen" />
  <link rel="stylesheet" href="css/dialog.css" type="text/css" media="screen" />
  <link rel="stylesheet" href="css/product_details.css" type="text/css" media="screen" />

  <script src="js/JsBarcodeReader/jquery-1.9.0.min.js" type="text/javascript"></script>
  <script src="js/JsBarcodeReader/quagga.js" type="text/javascript"></script>
  <script src="js/JsBarcodeRead.js" type="text/javascript"></script>
  
  <script src="js/jquery-1.10.2.min.js" type="text/javascript"></script>
  <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
  <script src="js/JsBarcodeGenCODE128.js" type="text/javascript"></script>
  <script src="js/JsBarcodeGen.js" type="text/javascript"></script>
  <script src="js/dialog.js" type="text/javascript"></script>
  <script src="js/product_details.js" type="text/javascript"></script>


  <title>PTL</title>
    
</head>
<body>
	<div id="distance"></div>
	<div id="wrapper">
    <a href="Logout"><button id="logout" class="color_discreet">Logout</button></a>
    <a href="Welcome"><button id="back" class="color_discreet">&#060&#060 Übersicht</button></a>
    <button id="edit" class="color" >Bearbeiten</button>

	<div id="scanning_container">
		<h1>Scanning<span></span>!</h1>
		<h2 id="error_message"></h2>
		<div id="interactive" class="viewport"></div>
	</div>
	
	<div id="product_details_container" class="toggle">
		<div id="product">
			<img id="barcode_picture" />
			<div class="description">Produkt ID:</div>
			<div class="value" id="product_id"><% product_id %></div>
			<div class="description">Produktname:</div>
			<div class="value"><% product_name %></div>
			<div class="description">Beschreibung:</div>
			<div class="value"><% product_description %></div>
			<div class="description">Mindestmenge im Lager:</div>
			<div class="value"><% product_min_quantity %></div>
			<div class="description">Lagerbestand:</div>
			<div class="value"><% product_lager_quantity %></div>
			
			<button id="ingoing" class="color">Eingang<img src="img/lager_icon_in.png"/></button>
			<button id="outgoing" class="color">Ausgang<img src="img/lager_icon_out.png"/></button>
		</div>
	</div>
	<button id="ok" class="color">OK</button>
	
    <footer></footer>
    			
	</div>

</body>
</html>
