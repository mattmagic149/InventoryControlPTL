<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="database.Truck"%>
<%@page import="database.TruckBrand"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List" %>

<%
	Object obj = session.getAttribute("current_truck");
	Object obj2 = session.getAttribute("is_new");
	Object obj3 = session.getAttribute("truck_brands");

	if (obj == null || obj2 == null) {%>
		<jsp:forward page="welcome.jsp"/>
	<%}
	
	List<TruckBrand> truck_brands = new ArrayList<TruckBrand>((ArrayList) session.getAttribute("truck_brands"));
	
	
	boolean is_new = (boolean)session.getAttribute("is_new");
	Truck truck = (Truck)session.getAttribute("current_truck");
	String truck_license_tag = "";
	String truck_id = "";
	String truck_barcode = "";
	String truck_brand = "";
	String truck_type = "";
	String initial_registration = "";
	String new_vehicle_since = "";
	String payload = "";
	String type_of_fuel_string = "Diesel";
	String performance = "";
	String emission_standard = "";
	String fin = "";
	String wheels_front_string = "";
	String wheels_rear_string = "";
	String loading_space_height = "";
	String loading_space_length = "";
	String state_string = "Aktiv";
	
	String truck_state = "";
	String truck_brand_name = "";
	String tyre_type_front = "1";
	String size_in_mm_front = "2";
	String height_in_percent_front = "3";
	String size_in_inch_front = "4";
	String tyre_type_rear = "5";
	String size_in_mm_rear = "6";
	String height_in_percent_rear = "7";
	String size_in_inch_rear = "8";
	
	if (truck != null) {
		truck_license_tag = truck.getLicenceTag();
		truck_id = String.valueOf(truck.getId());
		truck_barcode = truck.getBarCodeEncoding();
		truck_brand = truck.getBrand().getName();
		truck_type = truck.getType();
		payload = String.valueOf(truck.getPayload());
		if (truck.getTypeOfFuel() == Truck.FuelType.PETROL) {
			type_of_fuel_string = "Benzin";
		}
		performance = String.valueOf(truck.getPerformance());
		emission_standard = String.valueOf(truck.getEmissionStandard());
		fin = truck.getFin();
		if (truck.getWheelsFront() != null) {
			wheels_front_string = truck.getWheelsFront().getTyreInfos();			
		}
		if (truck.getWheelsRear() != null) {
			wheels_rear_string = truck.getWheelsRear().getTyreInfos();
		}
		
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		loading_space_height = String.valueOf(truck.getLoadingSpaceHeight());
		loading_space_length = String.valueOf(truck.getLoadingSpaceLength());
		initial_registration = format.format(truck.getInitialRegistration());
		new_vehicle_since = format.format(truck.getNewVehicleSince());
		
		if (truck.getTruckState() == Truck.TruckState.SOLD) {
			state_string = "Verkauft";	
		}
		
		truck_state = String.valueOf(truck.getTruckState().ordinal());
		if (truck.getBrand() != null) {
			truck_brand_name = truck.getBrand().getName();
		}
		if (truck.getWheelsFront() != null) {
			if (truck.getWheelsFront().getTyreType() != null) {
				tyre_type_front = "" + truck.getWheelsFront().getTyreType();
			}
			size_in_mm_front = "" + truck.getWheelsFront().getSizeInmm();
			height_in_percent_front = "" + truck.getWheelsFront().getHeightInPercent();
			size_in_inch_front = "" + truck.getWheelsFront().getSizeInInch();
		}
		
		if (truck.getWheelsRear() != null) {
			if (truck.getWheelsRear().getTyreType() != null) {
				tyre_type_rear = String.valueOf(truck.getWheelsRear().getTyreType());
			}
			size_in_mm_rear = String.valueOf(truck.getWheelsRear().getSizeInmm());
			height_in_percent_rear = String.valueOf(truck.getWheelsRear().getHeightInPercent());
			size_in_inch_rear = String.valueOf(truck.getWheelsRear().getSizeInInch());
		}	
	}
	
	String hidden_in_new = "";		
	if (is_new) {
		hidden_in_new = "hidden";
		truck_id = "0";
	}
%>

<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  
  <link rel="stylesheet" href="css/style.css" type="text/css" media="screen" />
  <link rel="stylesheet" href="css/dialog.css" type="text/css" media="screen" />
  <link rel="stylesheet" href="css/jquery.datetimepicker.css" type="text/css" />
  <link rel="stylesheet" href="css/lkw.css" type="text/css" media="screen" />
  
  <script src="js/jquery-1.10.2.min.js" type="text/javascript"></script>

  <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
  <script src="js/JsBarcodeGenCODE128.js" type="text/javascript"></script>
  <script src="js/JsBarcodeGen.js" type="text/javascript"></script>

  <script src="js/dialog.js" type="text/javascript"></script>
  <script src="js/jquery.datetimepicker.js" type="text/javascript" charset="UTF-8"></script>
  
  <script src="js/lkw.js" type="text/javascript"></script>

  <title>PTL - LKW</title>
    
</head>
<body>
	<div id="distance"></div>
	<div id="wrapper">
    <a href="Logout"><button id="logout" class="color_discreet">Logout</button></a>
    <a href="Welcome"><button id="back" class="color_discreet">&#060&#060 Übersicht</button></a>
    <button id="edit" class="color hideinmobile <%=hidden_in_new %>" >Bearbeiten</button>
	
	<div id="hidden_infos">
		<div class="hidden" id="state"><%=truck_state %></div>
		<div class="hidden" id="brand"><%=truck_brand %></div>
		<div class="hidden" id="tyre_type_front"><%=tyre_type_front %></div>
		<div class="hidden" id="size_in_mm_front"><%=size_in_mm_front %></div>
		<div class="hidden" id="height_in_percent_front"><%=height_in_percent_front  %></div>
		<div class="hidden" id="size_in_inch_front"><%=size_in_inch_front %></div>
	
		<div class="hidden" id="tyre_type_rear"><%=tyre_type_rear %></div>
		<div class="hidden" id="size_in_mm_rear"><%=size_in_mm_rear %></div>
		<div class="hidden" id="height_in_percent_rear"><%=height_in_percent_rear %></div>
		<div class="hidden" id="size_in_inch_rear"><%=size_in_inch_rear %></div>
		
		<div class="hidden" id="all_possible_truck_brands">
			<% for (TruckBrand tb : truck_brands) { %>
			<option value="<%=tb.getName() %>"><%=tb.getName() %></option> 
			<% } %>
			<option value="NEW">Neue Marke anlegen</option>
		</div>
	</div>
	
	<div id="truck_details_container">
		<div id="truck" truck_id="<%=truck_id %>" barcode="<%=truck_barcode %>">
			
			<% if (!is_new) { %>
			<h1><span id="license_tag"><%=truck_license_tag %></span> (<span id="brand"><%=truck_brand %></span>, <span id="type"><%=truck_type %></span>)</h1>
			<img id="barcode_picture" class="<%=hidden_in_new %>"/>
			<div class="entry">
				<div class="description">Treibstoff:</div>
				<div class="value" id="type_of_fuel"><%=type_of_fuel_string %></div>
			</div>
			<div class="entry">
				<div class="description">Nutzlast:</div>
				<div class="value" id="payload"><%=payload %></div>
			</div>
			<div class="entry">
				<div class="description">Leistung in KW:</div>
				<div class="value" id="performance"><%=performance %></div>
			</div>
			<div class="entry">
				<div class="description">Abgasnorm (0-6):</div>
				<div class="value" id="emission_standard"><%=emission_standard %></div>
			</div>
			<div class="entry one_line_in_mobile">
				<div class="description">Fahrzeug Identifikationsnummer:</div>
				<div class="value" id="fin"><%=fin %></div>
			</div>
			<div class="entry one_line_in_mobile">
				<div class="description">Ladefläche (Höhe / Länge):</div>
				<div class="value"><span id="loading_space_height"><%=loading_space_height %></span> m / <span id="loading_space_length"><%=loading_space_length %></span> m</div>
			</div>
			<div class="entry">
				<div class="description">Reifen vorne:</div>
				<div class="value"><%=wheels_front_string %></div>
			</div>
			<div class="entry">
				<div class="description">Reifen hinten:</div>
				<div class="value"><%=wheels_rear_string %></div>
			</div>
			<div class="entry hidden">
				<div class="description">Status des LKWs:</div>
				<div class="value" id="state_string"><%=state_string %></div>
			</div>
			<div class="entry">
				<div class="description">Erstmalige Zulassung:</div>
				<div class="value" id="initial_registration"><%=initial_registration %></div>
			</div>
			<div class="entry">
				<div class="description">Neufahrzeug seit:</div>
				<div class="value" id="new_vehicle_since"><%=new_vehicle_since %></div>
			</div>
			<div class="entry one_line"> 
				<button class="value color" id="show_services" truck_id="<%=truck_id %>">Services anzeigen</button> 
			</div>
			
			<% } else { %>
			<h1>Neuer LKW:</h1>
			<div class="entry one_row">
				<div class="description">Kennzeichen:</div>
				<input type="text" class="value" id="license_tag" value=""></input>
			</div> 
			<div class="entry one_row"> 
				<div class="description">Marke:</div> 
				<select class="value" id="brand_selection"> 
					<% for (TruckBrand tb : truck_brands) { %>
					<option value="<%=tb.getName() %>"><%=tb.getName() %></option> 
					<% } %>
					<option value="NEW">Neue Marke anlegen</option> 
				</select>
				<input type="text" class="value hidden split_in_two" id="new_brand"></input> 
			</div>
			<div class="entry one_row"> 
				<div class="description">Fahrzeug Identifikationsnummer:</div> 
				<input type="text" class="value" id="fin" value=""></input> 
			</div> 
			<div class="entry one_row"> 
				<div class="description">Type:</div> 
				<input type="text" class="value" id="type" value=""></input> 
			</div> 
			<div class="entry one_row"> 
				<div class="description">Nutzlast:</div> 
				<input type="text" class="value" id="payload" value=""></input> 
			</div> 
			<div class="entry one_row"> 
				<div class="description">Leistung in KW:</div> 
				<input type="text" class="value" id="performance" value=""></input> 
			</div> 
			<div class="entry one_row"> 
				<div class="description">Treibstoff:</div> 
				<select class="value" id="type_of_fuel"> 
					<option value="DIESEL">Diesel</option> 
					<option value="PETROL">Benzin</option> 
				</select> 
			</div> 
			<div class="entry one_row"> 
				<div class="description">Abgasnorm (0-6):</div> 
				<select class="value" id="emission_standard"> 
					<option value="0">0</option> 
					<option value="1">1</option> 
					<option value="2">2</option> 
					<option value="3">3</option> 
					<option value="4">4</option> 
					<option value="5">5</option> 
					<option value="6">6</option> 
				</select> 
			</div> 
			<div class="entry one_row"> 
				<div class="description">Ladefläche in Meter (Höhe / Länge):</div> 
				<input type="text" class="value split_in_two" id="loading_space_height" value=""></input> 
				<input type="text" class="value split_in_two" id="loading_space_length" value=""></input> 
			</div> 
			<div class="entry">
				<div class="description">Status des LKWs:</div> 
				<select class="value" id="state"> 
					<option value="ACTIVE">Aktiv</option> 
					<option value="SOLD">Verkauft</option> 
				</select> 
			</div>
			<div class="entry"> 
				<div class="description">Reifen vorne:</div> 
				<div class="description">(Reifenart, Größe in mm):</div> 
				<select class="value split_in_two" id="tyre_type_front"> 
					<option value="RADIAL">RADIAL</option> 
					<option value="DIAGONAL">DIAGONAL</option> 
				</select> 
				<input type="text" class="value split_in_two" id="size_in_mm_front" value=""></input> 
				<div class="description">(Höhe in Prozent, Größe in Inch):</div> 
				<input type="text" class="value split_in_two" id="height_in_percent_front" value=""></input> 
				<input type="text" class="value split_in_two" id="size_in_inch_front" value=""></input> 
			</div> 
			<div class="entry"> 
				<div class="description">Reifen hinten:</div> 
				<div class="description">(Reifenart, Größe in mm):</div> 
				<select class="value split_in_two" id="tyre_type_rear"> 
					<option value="RADIAL">RADIAL</option> 
					<option value="DIAGONAL">DIAGONAL</option> 
				</select> 
				<input type="text" class="value split_in_two" id="size_in_mm_rear" value=""></input> 
				<div class="description">(Höhe in Prozent, Größe in Inch):</div> 
				<input type="text" class="value split_in_two" id="height_in_percent_rear" value=""></input> 
				<input type="text" class="value split_in_two" id="size_in_inch_rear" value=""></input> 
			</div> 
			<div class="entry"> 
				<div class="description">Erstmalige Zulassung:</div> 
		   	   <input type="text" value="" placeholder="Erstzulassung" id="initial_registration"/>
			</div> 
			<div class="entry"> 
				<div class="description">Neufahrzeug seit:</div> 
		   	   <input type="text" value="" placeholder="Neuwagen seit" id="new_vehicle_since"/>
			</div>
			<% } %>
			
		</div>
	</div>
	<button id="ok" class="color hideinmobile">OK</button>
    <footer></footer>
    			
	</div>

</body>
</html>
