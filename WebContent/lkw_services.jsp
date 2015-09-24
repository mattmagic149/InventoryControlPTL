<%@page import="database.TruckService"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="database.Truck" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List" %>
<%@page import="java.text.SimpleDateFormat"%>

<%
	Object obj = session.getAttribute("truck");
	if (obj == null) {%>
		<jsp:forward page="welcome.jsp"/>
	<%}
	
	Truck truck = (Truck)session.getAttribute("truck");
	List<TruckService> truck_services = truck.getServices();
	SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  
  <link rel="stylesheet" href="css/style.css" type="text/css" media="screen" />
  <link rel="stylesheet" href="css/detail_list.css" type="text/css" media="screen" />
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
      
    <h1><span>LKWs</span></h1>

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
			for (TruckService tc : truck_services) {
	  %>
		      <section class="item color_discreet" id="<%=tc.getId() %>">
		        <div class="name"><%=format.format(tc.getDate()) %></div>
		        <div class="name"><%=tc.getRepairShop().getName() %></div>
		        <div class="name"><%=tc.getDescription() %></div>
		        <div class="name"><%=tc.getMileage() %></div>
				<button class="details color">&#062 &#062</button>
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
