// JavaScript Document

var truck = new Object();
var is_new_truck = false;

$(document).ready(function() {
	generateBarCode();
	
	if ($("#barcode_picture").hasClass("hidden")) {
		is_new_truck = true;
		$("#ok").show();
	} else {
		$("#edit").on("click", activateTruckEditing);		
		fillTruckWithDataBecauseOfTextFields();
	}

	$("#wrapper").on("change", "#brand", handleChangeBrand);
	$("#wrapper").on("change", "#type", handleChangeType);

	$("#wrapper").on("click", "#ok", confirmTruckEditing);

});

function handleChangeBrand(e) {
	if ($(this).val() == "NEW") {
		$("#new_brand").removeClass("hidden");
		$(this).addClass("split_in_two");		
	} else {
		$("#new_brand").addClass("hidden");
		$(this).removeClass("split_in_two");		
	}
}

function handleChangeType(e) {
	if ($(this).val() == "NEW") {
		$("#new_type").removeClass("hidden");
		$(this).addClass("split_in_two");		
	} else {
		$("#new_type").addClass("hidden");
		$(this).removeClass("split_in_two");				
	}
}


function fillTruckWithDataBecauseOfTextFields() {
	truck.id = $("#truck").attr("truck_id");
	truck.license_tag = $("#license_tag").text();
	truck.brand = $("#brand").text();
	truck.type = $("#type").text();
	truck.type_of_fuel = $("#type_of_fuel").text();
	truck.payload = $("#payload").text();
	truck.performance = $("#performance").text();
	truck.emission_standard = $("#emission_standard").text();
	truck.fin = $("#fin").text();
	truck.loading_space_height = $("#loading_space_height").text();
	truck.loading_space_length = $("#loading_space_length").text();
	var wf = new Object();
	wf.tyre_type = $("#tyre_type_front").text();
	wf.size_in_mm = $("#size_in_mm_front").text();
	wf.height_in_percent = $("#height_in_percent_front").text();
	wf.size_in_inch = $("#size_in_inch_front").text();
	truck.wheels_front = wf;
	var wr = new Object();
	wr.tyre_type = $("#tyre_type_rear").text();
	wr.size_in_mm = $("#size_in_mm_rear").text();
	wr.height_in_percent = $("#height_in_percent_rear").text();
	wr.size_in_inch = $("#size_in_inch_rear").text();
	truck.wheels_rear = wr;
	if ($("#state_string").text() == "Aktiv") {
		truck.state = "ACTIVE";
	} else {
		truck.state = "SOLD";
	}
	truck.initial_registration = $("#initial_registration").text();
	truck.new_vehicle_since = $("#new_vehicle_since").text();
	
	var truck_string = JSON.stringify(truck);
	console.log("truck = " + truck_string);
}

function fillTruckWithDataBecauseOfInputFields() {
	truck.id = $("#truck").attr("truck_id");
	truck.license_tag = $("#license_tag").val();
	truck.brand = $("#brand").val();
	if (truck.brand == "NEW") {
		truck.brand = $("#new_brand").val();
	}
	truck.type = $("#type").val();
	if (truck.type == "NEW") {
		truck.type = $("#new_type").val();
	}
	
	truck.type_of_fuel = $("#type_of_fuel").val();
	truck.payload = $("#payload").val();
	truck.performance = $("#performance").val();
	truck.emission_standard = $("#emission_standard").val();
	truck.fin = $("#fin").val();
	truck.loading_space_height = $("#loading_space_height").val();
	truck.loading_space_length = $("#loading_space_length").val();
	var wf = new Object();
	wf.tyre_type = $("#tyre_type_front").val();
	wf.size_in_mm = $("#size_in_mm_front").val();
	wf.height_in_percent = $("#height_in_percent_front").val();
	wf.size_in_inch = $("#size_in_inch_front").val();
	truck.wheels_front = wf;
	var wr = new Object();
	wr.tyre_type = $("#tyre_type_rear").val();
	wr.size_in_mm = $("#size_in_mm_rear").val();
	wr.height_in_percent = $("#height_in_percent_rear").val();
	wr.size_in_inch = $("#size_in_inch_rear").val();
	truck.wheels_rear = wr;

	truck.state = $("#state").val();

	truck.initial_registration = $("#initial_registration").val();
	truck.new_vehicle_since = $("#new_vehicle_since").val();
	
	
	var truck_string = JSON.stringify(truck);
	console.log("truck modified = " + truck_string);
}

function activateTruckEditing() {
	$("#ok").show();
	fillTruckWithDataBecauseOfTextFields();
	$("#hidden_infos").remove();
	$("h1").text("");
	$("#truck_details_container").addClass("editing");
	$("#truck").find(".description").remove();
	$("#truck").find(".value").remove();
	$("#barcode_picture").hide();

	var content = $('<div class="entry one_row">' + 
			'	<div class="description">Kennzeichen:</div>' + 
			'	<input type="text" class="value" id="license_tag" value="'+ truck.license_tag +'"></input>' + 
			'</div>' + 
			'<div class="entry one_row">' + 
			'	<div class="description">Marke:</div>' + 
			'	<select class="value" id="brand">' + 
			'		<option value="Audi">brand1</option>' + 
			'		<option value="1">brand2</option>' + 
			'		<option value="NEW">Neue Marke anlegen</option>' + 
			'	</select>' + 
			'	<input type="text" class="value hidden split_in_two" id="new_brand"></input>' + 
			'</div>' + 
			'<div class="entry one_row">' + 
			'	<div class="description">Fahrzeug Identifikationsnummer:</div>' + 
			'	<input type="text" class="value" id="fin" value="' + truck.fin + '"></input>' + 
			'</div>' + 
			'<div class="entry one_row">' + 
			'	<div class="description">Type:</div>' + 
			'	<select class="value" id="type">' + 
			'		<option value="0">type1</option>' + 
			'		<option value="1">type2</option>' + 
			'		<option value="NEW">Neue Type anlegen</option>' + 
			'	</select>' + 
			'	<input type="text" class="value hidden split_in_two" id="new_type"></input>' + 
			'</div>' + 
			'<div class="entry one_row">' + 
			'	<div class="description">Nutzlast:</div>' + 
			'	<input type="text" class="value" id="payload" value="'+ truck.payload +'"></input>' + 
			'</div>' + 
			'<div class="entry one_row">' + 
			'	<div class="description">Leistung in KW:</div>' + 
			'	<input type="text" class="value" id="performance" value="' + truck.performance + '"></input>' + 
			'</div>' + 
			'<div class="entry one_row">' + 
			'	<div class="description">Treibstoff:</div>' + 
			'	<select class="value" id="type_of_fuel">' + 
			'		<option value="Diesel">Diesel</option>' + 
			'		<option value="Benzin">Benzin</option>' + 
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
			'	<input type="text" class="value split_in_two" id="loading_space_height" value="' + truck.loading_space_height + '"></input>' + 
			'	<input type="text" class="value split_in_two" id="loading_space_length" value="' + truck.loading_space_length + '"></input>' + 
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
			'		<option value="0">RADIAL</option>' + 
			'		<option value="1">DIAGONAL</option>' + 
			'	</select>' + 
			'	<input type="text" class="value split_in_two" id="size_in_mm_front" value="' + truck.wheels_front.size_in_mm + '"></input>' + 
			'	<div class="description">(Höhe in Prozent, Größe in Inch):</div>' + 
			'	<input type="text" class="value split_in_two" id="height_in_percent_front" value="' + truck.wheels_front.height_in_percent + '"></input>' + 
			'	<input type="text" class="value split_in_two" id="size_in_inch_front" value="' + truck.wheels_front.size_in_inch + '"></input>' + 
			'</div>' + 
			'<div class="entry">' + 
			'	<div class="description">Reifen hinten:</div>' + 
			'	<div class="description">(Reifenart, Größe in mm):</div>' + 
			'	<select class="value split_in_two" id="tyre_type_rear">' + 
			'		<option value="0">RADIAL</option>' + 
			'		<option value="1">DIAGONAL</option>' + 
			'	</select>' + 
			'	<input type="text" class="value split_in_two" id="size_in_mm_rear" value="' + truck.wheels_rear.size_in_mm + '"></input>' + 
			'	<div class="description">(Höhe in Prozent, Größe in Inch):</div>' + 
			'	<input type="text" class="value split_in_two" id="height_in_percent_rear" value="' + truck.wheels_rear.height_in_percent + '"></input>' + 
			'	<input type="text" class="value split_in_two" id="size_in_inch_rear" value="' + truck.wheels_rear.size_in_inch + '"></input>' + 
			'</div>' + 
			'<div class="entry">' + 
			'	<div class="description">Erstmalige Zulassung:</div>' + 
		   	'   <input type="text" value="' + truck.initial_registration + '" placeholder="Erstzulassung" id="initial_registration"/>' +
			'</div>' + 
			'<div class="entry">' + 
			'	<div class="description">Neufahrzeug seit:</div>' + 
		   	'   <input type="text" value="' + truck.new_vehicle_since + '" placeholder="Neuwagen seit" id="new_vehicle_since"/>' +
			'</div>'
			);
	content.insertAfter("#barcode_picture");
	
	$("#type_of_fuel").val(truck.type_of_fuel).change();
	$("#emission_standard").val(truck.emission_standard).change();
	$("#brand").val(truck.brand).change();
	$("#type").val(truck.type).change();
	$("#state").val(truck.state).change();
	$("#tyre_type_front").val("" + truck.wheels_front.tyre_type).change();
	$("#tyre_type_rear").val("" + truck.wheels_rear.tyre_type).change();

}


function confirmTruckEditing() {
	fillTruckWithDataBecauseOfInputFields();
	if (is_new_truck) {
		truck.id = "0";
		var truck_string = JSON.stringify(truck);
		sendTruckToServer("Add", truck_string);
	} else {
		var truck_string = JSON.stringify(truck);
		alert("truck edit = " + truck_string);
		sendTruckToServer("Edit", truck_string);		
	}
}


function sendTruckToServer(servlet, truck_string) {
	
	$.ajax({
		type: "POST",
		url: servlet,
		data: { type: "truck",
				object: truck_string },
		cache: false,
		success: function(data, settings, xhr) {
			//alert("success");
			var id = xhr.getResponseHeader('id');
			location.href = "TruckDetail?id=" + id;
		},
		error: function(data, settings, xhr) {
			alert("error");
//			$("#pop_up_message").html(xhr.getResponseHeader('error_message'));
		}
	});
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
