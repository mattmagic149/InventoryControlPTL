<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  
  <link rel="stylesheet" href="css/style.css" type="text/css" media="screen" />
  <link rel="stylesheet" href="css/list.css" type="text/css" media="screen" />

  <script src="js/jquery-1.10.2.min.js" type="text/javascript"></script>
  <script src="js/lager.js" type="text/javascript"></script>

  <title>PTL - Lager</title>
    
</head>
<body>
	<div id="distance"></div>
	<div id="wrapper">
    <a href="Logout"><button id="logout" class="color_discreet">Logout</button></a>
    <a href="Welcome"><button id="back" class="color_discreet">&#060&#060 Übersicht</button></a>
    <button id="edit" class="color" >Bearbeiten</button>
      
  	<h1>Lager</h1>
	<div id="list">
    	<div class="list_entry color">
        <a href="#">
          <p>Lager 1</p>
        </a>
        <section class='close_button'></section>
		</div>
    	<div class="list_entry color">
        <a href="#">
          <p>Lager 2</p>
        </a>
        <section class='close_button'></section>
		</div>
    	<div class="list_entry color">
        <a href="#">
          <p>Lager 3</p>
        </a>
        <section class='close_button'></section>
		</div>
    </div>
    
    <footer></footer>
    
	</div>
</body>
</html>
