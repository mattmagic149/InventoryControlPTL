<%@page import="database.Truck"%>

<%
	Truck truck = (Truck)session.getAttribute("current_truck");
	String truck_license_tag = "Wird neu generiert";
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
	String loading_space_width = "";
	
	if (truck != null) {
		truck_license_tag = truck.getLicenceTag();
		truck_brand = truck.getBrand().getName();
		truck_type = truck.getType();
		initial_registration = truck.getFormattedInitialRegistration();
		new_vehicle_since = truck.getNewVehicleSince();
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
		loading_space_width = String.valueOf(truck.getLoadingSpaceWidth());
		
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
	
	<div id="truck_details_container">
		<div id="truck">
			<div class="description">LKW Kennzeichen:</div>
			<div class="value" id="truck_id"><%=truck_license_tag %></div>
			<div class="description">Marke:</div>
			<div class="value"><%=truck_brand %></div>
			<div class="description">Type:</div>
			<div class="value"><%=truck_type %></div>
			<div class="description">Erstmalige Zulassung:</div>
			<div class="value"><%=initial_registration %></div>
			<div class="description">Neufahrzeug seit:</div>
			<div class="value"><%=new_vehicle_since %></div>
			<div class="description">Nutzlast:</div>
			<div class="value"><%=payload %></div>
			<div class="description">Treibstoff:</div>
			<div class="value"><%=type_of_fuel_string %></div>
			<div class="description">Leistung in KW:</div>
			<div class="value"><%=performance %></div>
			<div class="description">Abgasnorm (0-6):</div>
			<div class="value"><%=emission_standard %></div>
			<div class="description">Fahrzeug Identifikationsnummer:</div>
			<div class="value"><%=fin %></div>
			<div class="description">Status:</div>
			<div class="value"><%=state %></div>
			<div class="description">Reifen vorne:</div>
			<div class="value"><%=wheels_front_string %></div>
			<div class="description">Reifen hinten:</div>
			<div class="value"><%=wheels_rear_string %></div>
			<div class="description">Ladefläche (Höhe in m):</div>
			<div class="value"><%=loading_space_height %></div>
			<div class="description">Reifen hinten:</div>
			<div class="value"><%=wheels_rear_string %></div>
			<div class="description">Reifen hinten:</div>
			<div class="value"><%=wheels_rear_string %></div>
			<div class="description">Reifen hinten:</div>
			<div class="value"><%=wheels_rear_string %></div>
		</div>
	</div>
	<button id="ok" class="color">OK</button>
	
    <footer></footer>
    			
	</div>

</body>
</html>
