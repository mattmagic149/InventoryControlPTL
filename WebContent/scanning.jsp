<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <meta name="viewport" width="device-width">

  <link rel="stylesheet" href="css/style.css" type="text/css" media="screen" />
  <link rel="stylesheet" href="css/scanning.css" type="text/css" media="screen" />

  <script src="js/JsBarcodeReader/jquery-1.9.0.min.js" type="text/javascript"></script>
  <script src="js/JsBarcodeReader/quagga.js" type="text/javascript"></script>
  
  <script src="js/jquery-1.10.2.min.js" type="text/javascript"></script>
  <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
  <script src="js/dialog.js" type="text/javascript"></script>
  <script src="js/scanning.js" type="text/javascript"></script>

  <title>PTL - Scanning</title>
    
</head>
<body>
	<div id="distance"></div>
	<div id="wrapper">
    <a href="Logout"><button id="logout" class="color_discreet">Logout</button></a>
    <a href="Welcome"><button id="back" class="color_discreet">&#060&#060 Übersicht</button></a>

	<div id="scanning_container">
		<h1>Scanning<span></span>!</h1>
		<h2 id="error_message"></h2>
		<div id="interactive" class="viewport"></div>
	</div>
	
    <footer></footer>
    
	</div>

</body>
</html>
