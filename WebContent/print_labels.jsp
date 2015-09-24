<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="database.Product" %>
<%@page import="database.Truck" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List" %>

<%

	Object obj = session.getAttribute("products_list");
	Object obj2 = session.getAttribute("trucks_list");
	if (obj == null || obj2 == null) {%>
		<jsp:forward page="welcome.jsp"/>
	
	<%}
	List<Product> products = new ArrayList<Product>((ArrayList) session.getAttribute("products_list"));
	List<Truck> trucks = new ArrayList<Truck>((ArrayList) session.getAttribute("trucks_list"));
	System.out.println(products.size());
	System.out.println(trucks.size());
%>


<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title></title>
    <link rel="stylesheet" href="css/style.css" type="text/css" media="screen" />
    <link rel="stylesheet" href="css/print_labels.css" type="text/css" media="screen" />
	<link rel="stylesheet" href="css/print.css" type="text/css" media="print" />
	<script src="js/jquery-1.10.2.min.js" type="text/javascript"></script>
	<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <script src="js/JsBarcodeGenCODE128.js" type="text/javascript"></script>
	<script src="js/JsBarcodeGen.js" type="text/javascript"></script>
	<script src="js/print_labels.js" type="text/javascript"></script>
	
</head>
<body>
	<div id="distance" class="not_to_print"></div>
    <div id="wrapper" class="not_to_print">
    <a href="Logout" class="not_to_print"><button id="logout" class="color_discreet">Logout</button></a>
    <a href="Welcome" class="not_to_print"><button id="back" class="color_discreet">&#060&#060 Übersicht</button></a>
    
    <h1 class="not_to_print">Barcode-Labels ausdrucken</h1>
    
    <div id="container" class="not_to_print">
    	<% for (Product p : products) { %>
    		<div class="product not_to_print" product_barcode_string="<%=p.getBarCodeEncoding() %>">
    			<div class="description not_to_print"><span class="name"><%=p.getName() %></span> [<span class="des"><%=p.getDescription()%></span>]</div>
    			<input type="number" class="value not_to_print" value="0"></input>
    		</div>    		
    	<% } %>
    	<% for (Truck truck : trucks) { %>
    		<div class="product not_to_print" product_barcode_string="<%=truck.getBarCodeEncoding() %>">
    			<div class="description not_to_print"><span class="name"><%=truck.getLicenceTag() %></span></div>
    			<input type="number" class="value not_to_print" value="0"></input>
    		</div>    		
    	<% } %>
    </div>
    
    <button id="ok" class="color not_to_print">Drucken</button>
    <footer class="not_to_print"></footer>
    
    </div>
    
	<div id="labels" class="hidden">
				
	</div>
</body>
</html>