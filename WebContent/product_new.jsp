<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  
  <link rel="stylesheet" href="css/style.css" type="text/css" media="screen" />
  <link rel="stylesheet" href="css/product_details.css" type="text/css" media="screen" />
  <script src="js/jquery-1.10.2.min.js" type="text/javascript"></script>
  <script type="text/javascript" src="js/inline_edit/jquery.inlineedit.js"></script>
  <script src="js/editable.js" type="text/javascript"></script>
  <script src="js/product_details_new.js" type="text/javascript"></script>
	
	<title>PTL</title>
    
</head>
<body>
	<div id="distance"></div>
	<div id="wrapper">
    <a href="Logout"><button id="logout" class="color_discreet">Logout</button></a>
    <a href="Welcome"><button id="back" class="color_discreet">&#060&#060 Übersicht</button></a>

    <div id="product">
		<div class="description">Produktname:</div>
		<div class="value"><input type="text" id="product_name"></input></div>
		<div class="description">Beschreibung:</div>
		<div class="value"><textarea id="product_details"></textarea></div>
		<div class="description">Mindestmenge im Lager:</div>
		<div class="value"><input type="number" id="product_min_quantity"></input></div>
		
		<div id="Eingang"></div>
		<div id="Ausgang"></div>
    </div>
    <button id="ok" class="color">OK</button>
    <footer></footer>
    
	</div>
</body>
</html>
