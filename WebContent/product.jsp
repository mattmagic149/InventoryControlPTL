<%@page import="database.Product"%>

<%
	Product product = (Product)session.getAttribute("current_product");
	
	String product_id = "Wird automatisch generiert";
	String product_name = "";
	String product_description = "";
	String product_min_quantity = "";
	String product_lager_quantity = "3";
	String product_unity = "";
	
	if (product != null) {
		product_id = product.getBarCodeEncoding();
		product_name = product.getName();
		product_description = product.getDescription();
		product_min_quantity = String.valueOf(product.getMinimumLimit());
		product_unity = product.getUnity();
		
//		product_lager_quantity = " " + product.getUnity()
	}	
%>

<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  
  <link rel="stylesheet" href="css/style.css" type="text/css" media="screen" />
  <link rel="stylesheet" href="css/dialog.css" type="text/css" media="screen" />
  <link rel="stylesheet" href="css/product_details.css" type="text/css" media="screen" />

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
	
	<div id="product_details_container">
		<div id="product">
			<img id="barcode_picture" />
			<div class="description">Produkt ID:</div>
			<div class="value" id="product_id"><%=product_id %></div>
			<div class="description">Produktname:</div>
			<div class="value editable" id="product_name"><%=product_name %></div>
			<div class="description">Beschreibung:</div>
			<div class="value editable" id="product_description"><%=product_description %></div>
			<div class="description">Mindestmenge im Lager:</div>
			<div class="value editable" > <span id="product_minimum_limit"><%=product_min_quantity %></span> <span class="product_unity"><%=product_unity %></span></div>
			<div class="description">Lagerbestand:</div>
			<div class="value editable"><span id="product_lager_quantity"><%=product_lager_quantity %></span> <span class="product_unity"><%=product_unity %></span></div>

			<button id="ingoing" class="color">Eingang<img src="img/lager_icon_in.png"/></button>
			<button id="outgoing" class="color">Ausgang<img src="img/lager_icon_out.png"/></button>
		</div>
	</div>
	<button id="ok" class="color">OK</button>
	
    <footer></footer>
    			
	</div>

</body>
</html>
