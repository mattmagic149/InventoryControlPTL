// JavaScript Document

var truck = new Object();
var is_new_truck = false;

$(document).ready(function() {
	
	if ($("#truck").attr("truck_id") == 0) { //new truck
		is_new_truck = true;
		$("#ok").show();
		truck.state = "ACTIVE";

		$("#hidden_infos").remove();

		$("#truck_details_container").addClass("editing");
		
		enableDatePicker($("#initial_registration, #new_vehicle_since"));
		createInputFieldsChecker();
	} else {
		generateBarCode();
		$("#edit").on("click", activateTruckEditing);		
		$("#show_services").on("click", handleClickOnShowServices);	
		$("#show_products").on("click", handleClickOnShowProducts);	
	}

	$("#wrapper").on("change", "#brand_selection", handleChangeBrand);
	$("#wrapper").on("click", "#ok", confirmTruckEditing);
});

function handleClickOnShowServices(e) {
	var id = $("#show_services").attr("truck_id");
	location.href = "LkwServices?id=" + id;
}

function handleClickOnShowProducts(e) {
	var id = $("#show_products").attr("truck_id");
	location.href = "ProductsOverview?location_id=" + id;
}

function handleChangeBrand(e) {
	if ($(this).val() == "NEW") {
		$("#new_brand").removeClass("hidden");
		$(this).addClass("split_in_two");		
	} else {
		$("#new_brand").addClass("hidden");
		$(this).removeClass("split_in_two");		
	}
}

function fillTruckWithDataBecauseOfTextFields() {
	truck.id = $("#truck").attr("truck_id");
	truck.licence_tag = $("#license_tag").text();
	truck.brand = $("#brand").text();
	truck.type = $("#type").text();
	truck.type_of_fuel = $("#type_of_fuel").text();
	if (truck.type_of_fuel == "Benzin") {
		truck.type_of_fuel = "PETROL";
	} else {
		truck.type_of_fuel = "DIESEL";
	}
	truck.payload = $("#payload").text();
	truck.performance = $("#performance").text();
	truck.emission_standard = $("#emission_standard").text();
	truck.fin = $("#fin").text();
	truck.loading_space_height = $("#loading_space_height").text();
	truck.loading_space_length = $("#loading_space_length").text();
	truck.wheels_front = new Object();
	truck.wheels_front.tyre_type = $("#tyre_type_front").text();
	truck.wheels_front.size_in_mm = $("#size_in_mm_front").text();
	truck.wheels_front.height_in_percent = $("#height_in_percent_front").text();
	truck.wheels_front.size_in_inch = $("#size_in_inch_front").text();
	truck.wheels_rear = new Object();
	truck.wheels_rear.tyre_type = $("#tyre_type_rear").text();
	truck.wheels_rear.size_in_mm = $("#size_in_mm_rear").text();
	truck.wheels_rear.height_in_percent = $("#height_in_percent_rear").text();
	truck.wheels_rear.size_in_inch = $("#size_in_inch_rear").text();
	if ($("#state_string").text() == "Aktiv") {
		truck.state = "ACTIVE";
	} else {
		truck.state = "SOLD";
	}
	truck.initial_registration = $("#initial_registration").text();
	truck.new_vehicle_since = $("#new_vehicle_since").text();
	
	console.log("fillTruckWithDataBecauseOfTextFields: Truck = '" + JSON.stringify(truck) + "'");
}

function fillTruckWithDataBecauseOfInputFields() {
	truck = new Object();
	truck.id = $("#truck").attr("truck_id");
	truck.licence_tag = $("#licence_tag").val();
	truck.truck_brand = new Object();
	truck.truck_brand.name = $("#brand_selection").val();
	if (truck.truck_brand.name == "NEW") {
		truck.truck_brand.name = $("#new_brand").val();
	}
	truck.type = $("#type").val();
	
	truck.type_of_fuel = $("#type_of_fuel").val();
	truck.payload = $("#payload").val();
	truck.performance = $("#performance").val();
	truck.emission_standard = $("#emission_standard").val();
	truck.fin = $("#fin").val();
	truck.loading_space_height = $("#loading_space_height").val();
	truck.loading_space_length = $("#loading_space_length").val();
	truck.wheels_front = new Object();
	truck.wheels_front.tyre_type = $("#tyre_type_front").val();
	truck.wheels_front.size_in_mm = $("#size_in_mm_front").val();
	truck.wheels_front.height_in_percent = $("#height_in_percent_front").val();
	truck.wheels_front.size_in_inch = $("#size_in_inch_front").val();
	truck.wheels_rear = new Object();
	truck.wheels_rear.tyre_type = $("#tyre_type_rear").val();
	truck.wheels_rear.size_in_mm = $("#size_in_mm_rear").val();
	truck.wheels_rear.height_in_percent = $("#height_in_percent_rear").val();
	truck.wheels_rear.size_in_inch = $("#size_in_inch_rear").val();

	truck.state = $("#state").val();

	truck.initial_registration = $("#initial_registration").val();
	truck.new_vehicle_since = $("#new_vehicle_since").val();
	
	console.log("fillTruckWithDataBecauseOfInputFields: Truck = '" + JSON.stringify(truck) + "'");
}

function activateTruckEditing() {
	$("#ok").show();
	$("#edit").hide();
	
	fillTruckWithDataBecauseOfTextFields();

	var all_possible_truck_brands = $("#all_possible_truck_brands").children();
	
	$("#hidden_infos").remove();
	$("#truck_details_container").addClass("editing");

	$("h1").text("");
	$("#truck").find(".entry").remove();
	$("#barcode_picture").hide();
	var content = $('<div class="entry one_row">' + 
			'	<div class="description">Kennzeichen:</div>' + 
			'	<input type="text" class="value" id="licence_tag" value="'+ truck.licence_tag +'"></input>' + 
			'</div>' + 
			'<div class="entry one_row">' + 
			'	<div class="description">Marke:</div>' + 
			'	<select class="value" id="brand_selection">' + 
			'	</select>' + 
			'	<input type="text" class="value hidden split_in_two" id="new_brand"></input>' + 
			'</div>' + 
			'<div class="entry one_row">' + 
			'	<div class="description">Fahrzeug Identifikationsnummer:</div>' + 
			'	<input type="text" class="value" id="fin" value="' + truck.fin + '"></input>' + 
			'</div>' + 
			'<div class="entry one_row">' + 
			'	<div class="description">Type:</div>' + 
			'	<input type="text" class="value" id="type" value="' + truck.type + '"></input>' + 
			'</div>' + 
			'<div class="entry one_row">' + 
			'	<div class="description">Nutzlast:</div>' + 
			'	<input type="number" class="value" id="payload" value="'+ truck.payload +'"></input>' + 
			'</div>' + 
			'<div class="entry one_row">' + 
			'	<div class="description">Leistung in KW:</div>' + 
			'	<input type="number" class="value" id="performance" value="' + truck.performance + '"></input>' + 
			'</div>' + 
			'<div class="entry one_row">' + 
			'	<div class="description">Treibstoff:</div>' + 
			'	<select class="value" id="type_of_fuel">' + 
			'		<option value="DIESEL">Diesel</option>' + 
			'		<option value="PETROL">Benzin</option>' + 
			'		<option value="ELECTRIC">Elektro</option>' + 
			'	</select>' + 
			'</div>' + 
			'<div class="entry one_row">' + 
			'	<div class="description">Abgasnorm (0-6):</div>' + 
			'	<select class="value" id="emission_standard">' + 
			'		<option value="0">0</option>' + 
			'		<option value="1">1</option>' + 
			'		<option value="2">2</option>' + 
			'		<option value="3">3</option>' + 
			'		<option value="4">4</option>' + 
			'		<option value="5">5</option>' + 
			'		<option value="6">6</option>' + 
			'	</select>' + 
			'</div>' + 
			'<div class="entry one_row">' + 
			'	<div class="description">Ladefläche in Meter (Höhe / Länge):</div>' + 
			'	<input type="number" class="value split_in_two" id="loading_space_height" value="' + truck.loading_space_height + '"></input>' + 
			'	<input type="number" class="value split_in_two" id="loading_space_length" value="' + truck.loading_space_length + '"></input>' + 
			'</div>' + 
			'<div class="entry">' + 
			'	<div class="description">Status des LKWs:</div>' + 
			'	<select class="value" id="state">' + 
			'		<option value="ACTIVE">Aktiv</option>' + 
			'		<option value="SOLD">Verkauft</option>' + 
			'	</select>' + 
			'</div>' +
			'<div class="entry">' + 
			'	<div class="description">Reifen vorne:</div>' + 
			'	<div class="description">(Reifenart, Größe in mm):</div>' + 
			'	<select class="value split_in_two" id="tyre_type_front">' + 
			'		<option value="RADIAL">RADIAL</option>' + 
			'		<option value="DIAGONAL">DIAGONAL</option>' + 
			'	</select>' + 
			'	<input type="number" class="value split_in_two" id="size_in_mm_front" value="' + truck.wheels_front.size_in_mm + '"></input>' + 
			'	<div class="description">(Höhe in Prozent, Größe in Inch):</div>' + 
			'	<input type="number" class="value split_in_two" id="height_in_percent_front" value="' + truck.wheels_front.height_in_percent + '"></input>' + 
			'	<input type="number" class="value split_in_two" id="size_in_inch_front" value="' + truck.wheels_front.size_in_inch + '"></input>' + 
			'</div>' + 
			'<div class="entry">' + 
			'	<div class="description">Reifen hinten:</div>' + 
			'	<div class="description">(Reifenart, Größe in mm):</div>' + 
			'	<select class="value split_in_two" id="tyre_type_rear">' + 
			'		<option value="RADIAL">RADIAL</option>' + 
			'		<option value="DIAGONAL">DIAGONAL</option>' + 
			'	</select>' + 
			'	<input type="number" class="value split_in_two" id="size_in_mm_rear" value="' + truck.wheels_rear.size_in_mm + '"></input>' + 
			'	<div class="description">(Höhe in Prozent, Größe in Inch):</div>' + 
			'	<input type="number" class="value split_in_two" id="height_in_percent_rear" value="' + truck.wheels_rear.height_in_percent + '"></input>' + 
			'	<input type="number" class="value split_in_two" id="size_in_inch_rear" value="' + truck.wheels_rear.size_in_inch + '"></input>' + 
			'</div>' + 
			'<div class="entry">' + 
			'	<div class="description">Erstmalige Zulassung:</div>' + 
		   	'   <input type="text" class="date" value="' + truck.initial_registration + '" placeholder="Erstzulassung" id="initial_registration"/>' +
			'</div>' + 
			'<div class="entry">' + 
			'	<div class="description">Neufahrzeug seit:</div>' + 
		   	'   <input type="text" class="date" value="' + truck.new_vehicle_since + '" placeholder="Neuwagen seit" id="new_vehicle_since"/>' +
			'</div>'
			);
	content.insertAfter("#barcode_picture");
		
	$("#brand_selection").append(all_possible_truck_brands);
	
	$("#type_of_fuel").val(truck.type_of_fuel).change();
	$("#emission_standard").val(truck.emission_standard).change();
	$("#brand_selection").val(truck.brand).change();
	$("#state").val(truck.state).change();
	$("#tyre_type_front").val("" + truck.wheels_front.tyre_type).change();
	$("#tyre_type_rear").val("" + truck.wheels_rear.tyre_type).change();

	enableDatePicker($("#initial_registration, #new_vehicle_since"));	
	createInputFieldsChecker();
}


function confirmTruckEditing() {
	if(!(checkAllInputFields())) {
		console.log("confirmTruckEditing: checkAllInputFields dedected a problem");
		return;
	}
	
	fillTruckWithDataBecauseOfInputFields();

	if (is_new_truck) {
		truck.id = "0";
		var truck_string = JSON.stringify(truck);
		sendTruckToServer("Add", truck_string);
	} else {
		var truck_string = JSON.stringify(truck);
		sendTruckToServer("Edit", truck_string);		
	}
}

function generateBarCode() {
	var barcode_options = {
					width:  2,
					height: 100,
					quite: 0,
					format: "CODE128",
					displayValue: true,
					font:"monospace",
					textAlign:"center",
					fontSize: 17,
					backgroundColor:"",
					lineColor:"#000"
				}
	var id = $("#truck").attr("barcode");
	if (id != "") {
		$("#barcode_picture").JsBarcode(id, barcode_options);			
	}
}

function sendTruckToServer(servlet, truck_string) {
	console.log("sendTruckToServer: Servlet = '" + servlet + "', Truck = '" + truck_string + "'");

	$.ajax({
		type: "POST",
		url: servlet,
		encoding: "UTF-8",
		data: { type: "truck",
				object: truck_string },
		cache: false,
		success: function(data, settings, xhr) {
			var id = xhr.getResponseHeader('id');
			location.href = "LkwDetail?id=" + id;
		},
		error: function(data, settings, xhr) {
			var message = "Fehler beim Senden an den Server";
			createNotification("Error", message, "failure");
		}
	});
}
