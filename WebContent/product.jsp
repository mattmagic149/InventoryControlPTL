<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="database.Product"%>
<%@page import="database.Truck"%>
<%@page import="database.Location"%>
<%@page import="java.util.List"%>
<%@page import="org.javatuples.Pair"%>

<%
	Object obj = session.getAttribute("current_product");
	Object obj2 = session.getAttribute("is_new");
	if (obj2 == null) {%>
		<jsp:forward page="welcome.jsp"/>
	
	<%}
	Product product = (Product)session.getAttribute("current_product");
	String current_location = "2";//id from the inventory
	
	List<Truck> trucks_for_outgoing = null;
	if (product != null && product.getRestriction() == Product.TruckRestriction.YES) {
		trucks_for_outgoing = product.getTrucksToRestrict();
	} else {
		trucks_for_outgoing = Truck.getAllTrucks();
	}
		
	String possible_outgoing_locations = "<option value='selection'>Wählen Sie einen LKW aus!</option>";
	if (trucks_for_outgoing != null) {
		for (Truck truck : trucks_for_outgoing) {
			possible_outgoing_locations += "<option value='" + truck.getId() + "'>" + truck.getLicenceTag() + "</option>";
		}
	}
	possible_outgoing_locations += "<option value='3'>Andere Location...</option>";
	
	String product_id = "0";
	String product_barcode = "Wird automatisch generiert";
	String product_name = "";
	String product_description = "";
	String product_min_quantity = "";
	String product_lager_quantity = "0";
	String product_unity = "";
	String state_string = "ACTIVE";
	List<Pair<Boolean, Truck>> truck_restrictions = null;
	boolean no_restriction = false;
	
	boolean is_new = (boolean)session.getAttribute("is_new");
	String hidden_in_new = "";		
	if (is_new) {
		hidden_in_new = "hidden";
	}
	
	List<Truck> trucks_for_ingoing = null;
	String possible_ingoing_locations = "<option value='1'>Neue Ware...</option>";
	
	if (product != null && !is_new) {
		product_id = String.valueOf(product.getId());
		product_barcode = product.getBarCodeEncoding();
		product_name = product.getName();
		product_description = product.getDescription();
		product_min_quantity = String.valueOf(product.getMinimumLimit());
		product_unity = product.getUnity().getName();
		product_lager_quantity = String.valueOf(product.getQuantityOfSpecificLocation(2)); //this means the main inventory
		
		if (product.getState() == Product.ProductState.INACTIVE) {
			state_string = "INACTIVE";			
		}
		
		truck_restrictions = product.getAllTrucksIncludingRestriction();	
		if (product.getRestriction() == Product.TruckRestriction.NO) {
			no_restriction = true; 
		}
		
		List<Pair<Location, Long>> locations_of_product = product.getAllAvailableLocationsGreaterZero();
		for (Pair<Location, Long> loc_quantity : locations_of_product) {
			if(loc_quantity.getValue0().getId() != 2) {
				possible_ingoing_locations += "<option value='" + loc_quantity.getValue0().getId() + "' quantity='" + loc_quantity.getValue1() + "'>" + loc_quantity.getValue0().getSpecificName() + "</option>";
			}
		}
		
		//		product_lager_quantity = " " + product.getUnity()
	}
	
%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
  
  <link rel="stylesheet" href="css/style.css" type="text/css" media="screen" />
  <link rel="stylesheet" href="css/dialog.css" type="text/css" media="screen" />
  <link rel="stylesheet" href="css/product.css" type="text/css" media="screen" />

  <script src="js/jquery-1.10.2.min.js" type="text/javascript"></script>
  <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
  <script src="js/JsBarcodeGenCODE128.js" type="text/javascript"></script>
  <script src="js/JsBarcodeGen.js" type="text/javascript"></script>
  <script src="js/dialog.js" type="text/javascript"></script>
  <script src="js/check_inputfields.js" type="text/javascript"></script>
  <script src="js/ajax_loader.js" type="text/javascript"></script>
  
  <script src="js/product.js" type="text/javascript"></script>
  
  <title>PTL</title>
    
</head>
<body>
	<div id="distance"></div>
	<div id="wrapper">
    <a href="Logout"><button id="logout" class="color_discreet">Logout</button></a>
    <a href="Welcome"><button id="back" class="color_discreet">&#060&#060 Übersicht</button></a>
    <button id="edit" class="color hideinmobile <%=hidden_in_new %>" >Bearbeiten</button>
	
	<div id="current_location" class="hidden"><%=current_location %></div>
	<div id="possible_ingoing_locations" class="hidden"><%=possible_ingoing_locations %></div>
	<div id="possible_outgoing_locations" class="hidden"><%=possible_outgoing_locations %></div>
	<div id="restrictions" class="hidden">
	<% if (!is_new && truck_restrictions != null) { 
		if (no_restriction) { %>
			<div class="value restriction_container" > Alle LKWs<input type="checkbox" class="restriction" lkw_id="0" id="no_restriction" checked></input></div>
 		<% } else { %>
			<div class="value restriction_container" > Alle LKWs<input type="checkbox" class="restriction" lkw_id="0" id="no_restriction" ></input></div>
		<% } %>
 		<% for (Pair<Boolean, Truck> bool_truck : truck_restrictions) { %>	
 			<% if(bool_truck.getValue0() || no_restriction) { %>	
	 			<div class="value restriction_container" ><%=bool_truck.getValue1().getLicenceTag() %> <input type="checkbox" class="restriction" lkw_id="<%=bool_truck.getValue1().getId() %>" checked></input></div>
	 		<% } else { %>	
	 			<div class="value restriction_container" ><%=bool_truck.getValue1().getLicenceTag() %> <input type="checkbox" class="restriction" lkw_id="<%=bool_truck.getValue1().getId() %>"></input></div>
			<% } %>
		<% } %>
	<% } %>
	</div>
	
	
	<div id="product_details_container">
		<div id="product" value="<%=product_id %>">
			<img id="barcode_picture" class="<%=hidden_in_new %>"/>
			
			<% if (!is_new) { %>
			<div class="description">Produkt ID:</div>
			<div class="value" id="product_id"><%=product_barcode %></div>
			<div class="description">Produktname:</div>
			<div class="value editable" id="product_name"><%=product_name %></div>
			<div class="description">Beschreibung:</div>
			<div class="value editable" id="product_description"><%=product_description %></div>
			<div class="description">Mindestmenge im Lager:</div>
			<div class="value editable" > <span id="product_minimum_limit"><%=product_min_quantity %></span> <span class="product_unity"><%=product_unity %></span></div>
			<div class="description">Lagerbestand:</div>
			<div class="value editable"><span id="product_lager_quantity"><%=product_lager_quantity %></span> <span class="product_unity"><%=product_unity %></span></div>
			<div class="hidden value" id="product_state"><%=state_string %></div>
			<% } else { %>
			<div class="description">Produkt ID:</div>
			<div class="value" id="product_id"><%=product_id %></div>
			<div class="description">Produktname:</div>
			<input type="text" class="value editable" id="product_name" value=""></input>
			<div class="description">Beschreibung:</div>
			<textarea type="text" class="value editable" id="product_description"></textarea>
			<div class="description">Mindestmenge im Lager:</div>
			<input type="number" class="value editable" id="product_minimum_limit" value=""></input>
			<select class="value" id="product_unity">
				<option value="Stück">Stück</option>
				<option value="Liter">Liter</option>
				<option value="Paar">Paar</option>
				<option value="Packung">Packung</option>
				<option value="Rolle">Rolle</option>
			</select>
			<div class="description">Aktives Produkt:<input type="checkbox" id="product_state" checked></input></div>
			
			<div class="description" id="product_restrictions_container">Produkt darf nur in folgende LKWs:</div>
			<div class="value restriction_container" > Alle LKWs<input type="checkbox" class="restriction" lkw_id="0" id="no_restriction" checked></input></div>
			<% for (Truck truck : Truck.getAllTrucks()) { %>
				<div class="value restriction_container" ><%=truck.getLicenceTag() %> <input type="checkbox" class="restriction" lkw_id="<%=truck.getId() %>" checked></input></div>
			<% } %>		
			<% } %>	
		</div>
		
		<button id="ingoing" class="color <%=hidden_in_new %>">Eingang<img src="img/lager_icon_in.png"/></button>
		<button id="outgoing" class="color <%=hidden_in_new %>">Ausgang<img src="img/lager_icon_out.png"/></button>
	</div>
	
	<button id="ok" class="color">OK</button>
	
	</div>

</body>
</html>
