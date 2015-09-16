<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  
	<link rel="stylesheet" href="css/style.css" type="text/css" media="screen" />
	<link rel="stylesheet" href="css/dialog.css" type="text/css" media="screen" />
	
  	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js" type="text/javascript"></script>
  	<script src="js/dialog.js" type="text/javascript"></script>
  	<script src="js/index.js" type="text/javascript"></script>
  	<script src="js/ajax_loader.js" type="text/javascript"></script>
	
	<title>PTL - Login</title>
    
</head>
<body>
	<div id="distance"></div>
	<div id="wrapper">
    <!-- <a href="Register"><button id="registration" class="color">Registrieren</button></a> -->
    
  	<h1>Lagerverwaltung</h1>
	<img src="img/logo.png" style="position: absolute; top: 10px; left: 10px; width: 200px;"/>
	
	<form id="login_form">
      <input type="text" name="email" id="email" placeholder="E-Mail" maxlength="30" required="reguired">
      <input type="password" name="password" id="password" maxlength="30" required="reguired"
      			 placeholder="&#9679&#9679&#9679&#9679&#9679&#9679&#9679&#9679&#9679&#9679">
      <section id="login_button" class="color">Anmelden</section>
      <a href="Login" id="forgotten"><p>Passwort vergessen?</p></a>
    </form>
	</div>
</body>
</html>
