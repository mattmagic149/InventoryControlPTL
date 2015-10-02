<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="database.Truck" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List" %>

<%
	Object obj = session.getAttribute("trucks_list");
	if (obj == null) {%>
		<jsp:forward page="welcome.jsp"/>
	
	<%}
	List<Truck> trucks = new ArrayList<Truck>((ArrayList) session.getAttribute("trucks_list"));
%>

<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  
  <link rel="stylesheet" href="css/style.css" type="text/css" media="screen" />
  <link rel="stylesheet" href="css/detail_list.css" type="text/css" media="screen" />

  <script src="js/jquery-1.10.2.min.js" type="text/javascript"></script>
  <script src="js/lkws.js" type="text/javascript"></script>

  <title>PTL - LKWs</title>
    
</head>
<body>
	<div id="distance"></div>
	<div id="wrapper">
    <a href="Logout"><button id="logout" class="color_discreet">Logout</button></a>
    <a href="Welcome"><button id="back" class="color_discreet">&#060&#060 Übersicht</button></a>
      
    <h1><span>LKWs</span></h1>

	<button id="show_not_active" class="color_discreet">Inaktive anzeigen</button>
	
    <section id="item_header" class="color_without_hover">
      <div class="name">Kennzeichen</div>
      <div class="name">Marke</div>
      <div class="name">Nutzlast</div>
      <div class="name">Höhe / Länge</div>
      <div class="details">Details</div>
    </section>
    
    <section id="item_list">
      <%
		for (int i = 0; i < trucks.size(); i++) {
	  %>
	  	<% if (trucks.get(i).getTruckState() == Truck.TruckState.ACTIVE) { %>
		      <section class="item color_discreet active" id="<%=trucks.get(i).getId() %>">
		<% } else { %>
		      <section class="item color_discreet not_active hidden" id="<%=trucks.get(i).getId() %>">
		<% } %>		
		        <div class="name"><%=trucks.get(i).getLicenceTag() %></div>
		        <div class="name"><%=trucks.get(i).getBrand().getName() %></div>
		        <div class="name"><%=trucks.get(i).getPayload() %></div>
		        <div class="name"><%=trucks.get(i).getLoadingSpaceHeight() + " m / " + trucks.get(i).getLoadingSpaceLength() + " m" %> </div>
				<button class="details color">&#062 &#062</button>
			  </section>			
	  <%
		}
      %>
    </section>
	
	<section id='add_button' class='color'></section>
    
    <footer></footer>
    
	</div>
</body>
</html>
