<%@page import="database.Truck"%>


<%
	Object obj = session.getAttribute("current_truck");
	Object obj2 = session.getAttribute("is_new");
	if (obj == null || obj2 == null) {%>
		<jsp:forward page="welcome.jsp"/>

	<%}
	boolean is_new = (boolean)session.getAttribute("is_new");
	Truck truck = (Truck)session.getAttribute("current_truck");
	String truck_license_tag = "";
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
	
	if (truck != null) {
		truck_license_tag = truck.getLicenceTag();
		truck_brand = truck.getBrand().getName();
		truck_type = truck.getType();
		//initial_registration = truck.getFormattedInitialRegistration();
		//new_vehicle_since = truck.getNewVehicleSince();
		payload = String.valueOf(truck.getPayload());
		if (truck.getTypeOfFuel() == Truck.FuelType.PETROL) {
			type_of_fuel_string = "Benzin";
		}
		performance = String.valueOf(truck.getPerformance());
		emission_standard = String.valueOf(truck.getEmissionStandard());
		fin = truck.getFin();
		wheels_front_string = truck.getWheelsFront().getTyreInfos();
		wheels_rear_string = truck.getWheelsRear().getTyreInfos();
		loading_space_height = String.valueOf(truck.getLoadingSpaceHeight());
		loading_space_length = String.valueOf(truck.getLoadingSpaceLength());
		if (truck.getTruckState() == Truck.TruckState.SOLD) {
			state_string = "Verkauft";	
		}
	}
	
	String hidden_in_new = "";		
	if (is_new) {
		hidden_in_new = "hidden";
	}
%>

<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  
  <link rel="stylesheet" href="css/style.css" type="text/css" media="screen" />
  <link rel="stylesheet" href="css/dialog.css" type="text/css" media="screen" />
  <link rel="stylesheet" href="css/truck_details.css" type="text/css" media="screen" />
  
  <script src="js/jquery-1.10.2.min.js" type="text/javascript"></script>

  <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
  <script src="js/JsBarcodeGenCODE128.js" type="text/javascript"></script>
  <script src="js/JsBarcodeGen.js" type="text/javascript"></script>

  <script src="js/dialog.js" type="text/javascript"></script>
  <script src="js/truck_details.js" type="text/javascript"></script>

  <title>PTL - LKW</title>
    
</head>
<body>
	<div id="distance"></div>
	<div id="wrapper">
    <a href="Logout"><button id="logout" class="color_discreet">Logout</button></a>
    <a href="Welcome"><button id="back" class="color_discreet">&#060&#060 Übersicht</button></a>
    <button id="edit" class="color" >Bearbeiten</button>
	
	<div class="hidden" id="tyre_type_front"><%=truck.getWheelsFront().getTyreType().ordinal() %></div>
	<div class="hidden" id="size_in_mm_front"><%=truck.getWheelsFront().getSizeInmm() %></div>
	<div class="hidden" id="height_in_percent_front"><%=truck.getWheelsFront().getHeightInPercent() %></div>
	<div class="hidden" id="size_in_inch_front"><%=truck.getWheelsFront().getSizeInInch() %></div>

	<div class="hidden" id="tyre_type_rear"><%=truck.getWheelsRear().getTyreType().ordinal() %></div>
	<div class="hidden" id="size_in_mm_rear"><%=truck.getWheelsRear().getSizeInmm() %></div>
	<div class="hidden" id="height_in_percent_rear"><%=truck.getWheelsRear().getHeightInPercent() %></div>
	<div class="hidden" id="size_in_inch_rear"><%=truck.getWheelsRear().getSizeInInch() %></div>
	
	<div id="truck_details_container">
		<h1><span id="license_tag"><%=truck_license_tag %></span> (<span id="brand"><%=truck_brand %></span>, <span id="type"><%=truck_type %></span>)</h1>
		<div id="truck" truck_id="<%=truck.getId() %>" barcode="<%=truck.getBarCodeEncoding() %>">
			<img id="barcode_picture" class="<%=hidden_in_new %>"/>
			
			<% if (!is_new) { %>
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
			<div class="entry">
				<div class="description">Fahrzeug Identifikationsnummer:</div>
				<div class="value" id="fin"><%=fin %></div>
			</div>
			<div class="entry">
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
				<div class="value" id="initial_registration"><%=truck.getInitialRegistration() %></div>
			</div>
			<div class="entry">
				<div class="description">Neufahrzeug seit:</div>
				<div class="value" id="new_vehicle_since"><%=truck.getNewVehicleSince() %></div>
			</div>
			<% } else { %>
			<div class="entry one_row">
				<div class="description" id="type_of_fuel">Treibstoff:</div>
				<select class="value" id="type_of_fuel">
					<option value="Diesel">Diesel</option>
					<option value="Benzin">Benzin</option>
				</select>
			</div>
			<div class="entry one_row">
				<div class="description">Nutzlast:</div>
				<input type="text" class="value" id="payload"></input>
			</div>
			<div class="entry one_row">
				<div class="description">Leistung in KW:</div>
				<input type="text" class="value" id="performance"></input>
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
				<div class="description">Fahrzeug Identifikationsnummer:</div>
				<input type="text" class="value" id="fin"></input>
			</div>
			<div class="entry one_row">
				<div class="description">Ladefläche in Meter (Höhe / Länge):</div>
				<input type="text" class="value split_in_two" id="loading_space_height"></input>
				<input type="text" class="value split_in_two" id="loading_space_length"></input>
			</div>
			<div class="entry">
				<div class="description">Reifen vorne:</div>
				<div class="description">(Reifenart, Größe in mm):</div>
				<select class="value split_in_two" id="tyre_type_front">
					<option value="0">RADIAL</option>
					<option value="1">DIAGONAL</option>
				</select>
				<input type="text" class="value split_in_two" id="size_in_mm_front"></input>
				<div class="description">(Höhe in Prozent, Größe in Inch):</div>
				<input type="text" class="value split_in_two" id="height_in_percent_front"></input>
				<input type="text" class="value split_in_two" id="size_in_inch_front"></input>
			</div>
			<div class="entry">
				<div class="description">Reifen hinten:</div>
				<div class="description">(Reifenart, Größe in mm):</div>
				<select class="value split_in_two" id="tyre_type_rear">
					<option value="0">RADIAL</option>
					<option value="1">DIAGONAL</option>
				</select>
				<input type="text" class="value split_in_two" id="size_in_mm_rear"></input>
				<div class="description">(Höhe in Prozent, Größe in Inch):</div>
				<input type="text" class="value split_in_two" id="height_in_percent_rear"></input>
				<input type="text" class="value split_in_two" id="size_in_inch_rear"></input>
			</div>
			<div class="entry hidden">
				<div class="description">Status des LKWs:</div>
				<div class="value"><%=state_string %></div>
			</div>
			<div class="entry">
				<div class="description">Erstmalige Zulassung:</div>
				<div class="value"><%=truck.getInitialRegistration() %></div>
			</div>
			<div class="entry">
				<div class="description">Neufahrzeug seit:</div>
				<div class="value"><%=truck.getNewVehicleSince() %></div>
			</div>
			
			
			<% } %>
			
		</div>
	</div>
	<button id="ok" class="color">OK</button>
	
    <footer></footer>
    			
	</div>

</body>
</html>
