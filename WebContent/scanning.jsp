 <%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <meta name="viewport" width="device-width">

  <link rel="stylesheet" href="css/style.css" type="text/css" media="screen" />
  <link rel="stylesheet" href="css/scanning.css" type="text/css" media="screen" />

  <script src="js/jquery-1.10.2.min.js" type="text/javascript"></script>
  <script src="js/JsBarcodeReader/quagga.js" type="text/javascript"></script>
  <script src="js/dialog.js" type="text/javascript"></script>
  <script src="js/scanning.js" type="text/javascript"></script>

  <title>PTL - Scannen</title>
    
</head>
<body>
	<div id="distance"></div>
	<div id="wrapper">
    <a href="Logout"><button id="logout" class="color_discreet">Logout</button></a>
    <a href="Welcome"><button id="back" class="color_discreet">&#060&#060 Übersicht</button></a>

	<h1>Scannen<span></span>!</h1>
	
	<div id="interactive" class="viewport"></div>
	
    <footer></footer>
    
	</div>

</body>
</html>
