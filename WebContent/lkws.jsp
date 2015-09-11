<%@page import="database.Truck" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List" %>

<%
	List<Truck> trucks = new ArrayList<Truck>((ArrayList) session.getAttribute("trucks_list"));
	System.out.println(trucks.size());
%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  
  <link rel="stylesheet" href="css/style.css" type="text/css" media="screen" />
  <link rel="stylesheet" href="css/list.css" type="text/css" media="screen" />
  <link rel="stylesheet" href="css/editable.css" type="text/css" media="screen" />
  <script src="js/jquery-1.10.2.min.js" type="text/javascript"></script>
  <script type="text/javascript" src="js/inline_edit/jquery.inlineedit.js"></script>
  <script src="js/editable.js" type="text/javascript"></script>
  <script src="js/lkws.js" type="text/javascript"></script>

	<title>PTL - LKWs</title>
    
</head>
<body>
	<div id="distance"></div>
	<div id="wrapper">
    <a href="Logout"><button id="logout" class="color_discreet">Logout</button></a>
    <a href="Welcome"><button id="back" class="color_discreet">&#060&#060 Übersicht</button></a>
    <button id="edit" class="color" >Bearbeiten</button>
      
  	<h1>LKWs</h1>
	<div id="list">
    	<div class="list_entry color">
        <a href="#">
          <p>GU PTL 1</p>
        </a>
        <section class='close_button'></section>
		</div>
    	<div class="list_entry color">
        <a href="#">
          <p>
            GU PTL 2
          </p>
        </a>
        <section class='close_button'></section>
		</div>
    	<div class="list_entry color">
        <a href="#">
          <p>
            GU PTL 3
          </p>
        </a>
        <section class='close_button'></section>
		</div>
    </div>
    
    <footer></footer>
    
	</div>
</body>
</html>
