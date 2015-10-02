<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="database.TruckService"%>
<%@page import="database.Truck" %>
<%@page import="database.RepairShop" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List" %>
<%@page import="java.text.SimpleDateFormat"%>

<%
	Object obj = session.getAttribute("truck");
	Object obj2 = session.getAttribute("repair_shops");
	if (obj == null || obj2 == null) {%>
		<jsp:forward page="welcome.jsp"/>
	<%}
	
	Truck truck = (Truck)session.getAttribute("truck");
	List<TruckService> truck_services = truck.getServices();
	SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
	
	List<RepairShop> repair_shops = ((ArrayList) session.getAttribute("repair_shops"));
	
%>

<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  
  <link rel="stylesheet" href="css/style.css" type="text/css" media="screen" />
  <link rel="stylesheet" href="css/jquery.datetimepicker.css" type="text/css" />
  <link rel="stylesheet" href="css/dialog.css" type="text/css" media="screen" />
  <link rel="stylesheet" href="css/detail_list.css" type="text/css" media="screen" />
  <link rel="stylesheet" href="css/lkw_services.css" type="text/css" media="screen" />
  
  <script src="js/jquery-1.10.2.min.js" type="text/javascript"></script>
  <script src="js/jquery.datetimepicker.js" type="text/javascript" charset="UTF-8"></script>
  <script src="js/dialog.js" type="text/javascript"></script>
  <script src="js/check_inputfields.js" type="text/javascript"></script>
  <script src="js/ajax_loader.js" type="text/javascript"></script>	
  <script src="js/detail_list_search.js" type="text/javascript"></script>
  <script src="js/lkw_services.js" type="text/javascript"></script>

  <title>PTL - LKW-Services</title>
  
</head>
<body>
	<div id="distance"></div>
	<div id="wrapper">
    <a href="Logout"><button id="logout" class="color_discreet">Logout</button></a>
    <a href="Welcome"><button id="back" class="color_discreet">&#060&#060 Übersicht</button></a>
    
    <div style="display: none">
    	<div id="truck_id"><%=truck.getId() %></div>
    	<div id="all_possible_repair_shops">
    		<option value="-1">Werkstatt auswählen...</option>
    		<% for (RepairShop rs : repair_shops) { %>
    		<option value="<%=rs.getId() %>"><%=rs.getRepairShopDetail() %></option>
    		<% } %>
    		<option value="0">Neue Werkstatt anlegen</option>
    	</div>
   	</div>
    
    
    <h1><span>Services</span> von <%=truck.getLicenceTag() %></h1>
    
    <form id="search_form">
      <input type="text" name="search" id="search" placeholder="Suchtext" maxlength="30" autocomplete="off">
      <input type="submit" value="" id="search_button" class="icon-search" />
    </form>
    

    <section id="item_header" class="color_without_hover">
      <div class="name">Datum</div>
      <div class="name">Werkstatt</div>
      <div class="name">Beschreibung</div>
      <div class="name">Kilometerstand</div>
      <div class="details">Ändern</div>
    </section>
    
    <section id="item_list">
      <%
      	if (truck_services != null) {
  			TruckService tc;
  			
      		for (int i = 0; i < truck_services.size(); i++) {
      			tc = truck_services.get(i);
	  %>
		      <section class="item color_discreet" id="<%=tc.getId()%>">
		        <div class="name date"><%=format.format(tc.getDate()) + ""%></div>
		        <div class="name repair_shop" repair_shop_id="<%=tc.getRepairShop().getId() %>"><%=tc.getRepairShop().getName() + ""%></div>
		        <div class="name description"><%=tc.getDescription() + ""%></div>
		        <div class="name mileage"><%=tc.getMileage() + ""%></div>
				<button class="details color">&#062 &#062</button>
				<button class="delete color">x</button>
			  </section>
	  <%
			}
		}
      %>
    </section>
	
	<section id='add_button' class='color'></section>
    
    <footer></footer>
    
	</div>
</body>
</html>
