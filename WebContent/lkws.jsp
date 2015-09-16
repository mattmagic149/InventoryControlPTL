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
  <script type="text/javascript" src="js/inline_edit/jquery.inlineedit.js"></script>
  <script src="js/editable.js" type="text/javascript"></script>
  <script src="js/lkws.js" type="text/javascript"></script>

	<title>PTL - LKWs</title>
    
</head>
<body>
	<div id="distance"></div>
	<div id="wrapper">
    <a href="Logout"><button id="logout" class="color_discreet">Logout</button></a>
    <a href="Welcome"><button id="back" class="color_discreet">&#060&#060 Ãœbersicht</button></a>
      
    <h1><span>LKWs</span></h1>

    <section id="item_header" class="color_without_hover">
      <div class="name">Kennzeichen</div>
      <div class="name">Anzahl Produkte</div>
      <div class="name">...</div>
      <div class="name">...</div>
      <div class="details">Details</div>
    </section>
    
    <section id="item_list">
      <%
		for (int i = 0; i < trucks.size(); i++) {
	  %>
		      <section class="item color_discreet" id="<%=trucks.get(i).getId() %>">
		        <div class="name"><%=trucks.get(i).getLicenceTag() %>Ÿ</div>
		        <div class="name">...</div>
		        <div class="name">...</div>
		        <div class="name">...</div>
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
