// JavaScript Document

var truck = new Object();
var is_new_truck = false;

$(document).ready(function() {
	generateBarCode();
	
	if ($("#barcode_picture").hasClass("hidden")) {
		is_new_product = true;
		$("#ok").show();
	} else {
		$("#edit").on("click", activateTruckEditing);		
		fillProductWithDataBecauseOfTextFields();
	}

	$("#wrapper").on("click", "#ok", confirmProductEditing);

});

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
	
}

function activateTruckEditing() {
	$("#ok").show();
	fillTruckWithDataBecauseOfTextFields();
	$("#truck").find(".description").remove();
	$("#truck").find(".value").remove();
	$("#barcode_picture").hide();

	var content = $('<div class="entry one_row">' + 
			'	<div class="description">Treibstoff:</div>' + 
			'	<select class="value" id="type_of_fuel">' + 
			'		<option value="Diesel">Diesel</option>' + 
			'		<option value="Benzin">Benzin</option>' + 
			'	</select>' + 
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
			'	<div class="description">Fahrzeug Identifikationsnummer:</div>' + 
			'	<input type="text" class="value" id="fin" value="' + truck.fin + '"></input>' + 
			'</div>' + 
			'<div class="entry one_row">' + 
			'	<div class="description">Ladefläche in Meter (Höhe / Länge):</div>' + 
			'	<input type="text" class="value split_in_two" id="loading_space_height" value="' + truck.loading_space_height + '"></input>' + 
			'	<input type="text" class="value split_in_two" id="loading_space_length" value="' + truck.loading_space_length + '"></input>' + 
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
			'<div class="entry hidden">' + 
			'	<div class="description">Status des LKWs:</div>' + 
			'	<div class="value">' + "STATE" + '</div>' + 
			'</div>' + 
			'<div class="entry">' + 
			'	<div class="description">Erstmalige Zulassung:</div>' + 
			'	<div class="value"></div>' + 
			'</div>' + 
			'<div class="entry">' + 
			'	<div class="description">Neufahrzeug seit:</div>' + 
			'	<div class="value"></div>' + 
			'</div>'
			);
	content.insertAfter("#barcode_picture");
	
	$("#type_of_fuel").val(truck.type_of_fuel).change();
	$("#emission_standard").val(truck.emission_standard).change();
	//$("#tyre_type_front").val(truck.wheels_front.tyre_type).change();
	//$("#tyre_type_rear").val(truck.tyre_type_rear).change();
/*	
	if (product.state == "ACTIVE") {
		$("#product_state").prop("checked", true);		
	} else {
		$("#product_state").prop("checked", false);		
	}
	var restrictions = $("#restrictions").children();
	restrictions.insertAfter("#product_restrictions_container");
	
	$("#product_unity").val(product.unity).change();
	*/
}

function showProductBecauseOfData() {
	fillProductWithDataBecauseOfInputFields();
	$("#product").find(".description").remove();
	$("#product").find(".value").remove();
	$("#ingoing").show();
	$("#outgoing").show();
	$("#barcode_picture").show();

	var content = $('<div class="description">Produkt ID:</div>' +
				'<div class="value" id="product_id">' + product.id + '</div>' +
				'<div class="description">Produktname:</div>' +
				'<div class="value editable" id="product_name">' + product.name + '</div>' +
				'<div class="description">Beschreibung:</div>' +
				'<div class="value editable" id="product_description">' + product.description + '</div>' +
				'<div class="description">Mindestmenge im Lager:</div>' +
				'<div class="value editable"><span id="product_minimum_limit">' + product.minimum_limit + '</span><span class="product_unity">' + " " + product.unity + '</span></div>' +
				'<div class="description">Lagerbestand:</div>' +
				'<div class="value editable"><span id="product_lager_quantity">' + product.lager_quantity + '</span><span class="product_unity">' + " " + product.unity + '</span></div>' +
				'<div class="hidden value" id="product_state">' + product.state +'</div>'
				);
				
	content.insertAfter("#barcode_picture");
}

function confirmProductEditing() {
	fillProductWithDataBecauseOfInputFields();
	if (is_new_product) {
		product.id = "0";
		var product_string = JSON.stringify(product);
		sendProductToServer("Add", product_string);
	} else {
		var product_string = JSON.stringify(product);
		alert("product edit = " + product_string);
		sendProductToServer("Edit", product_string);		
	}
}


function sendProductToServer(servlet, product_string) {
	
	$.ajax({
		type: "POST",
		url: servlet,
		data: { type: "product",
				object: product_string },
		cache: false,
		success: function(data, settings, xhr) {
			//alert("success");
			var content = xhr.getResponseHeader('content');
			var id = xhr.getResponseHeader('id');
			location.href = "ProductDetail?id=" + id;
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


function handleIngoing() {
	createTransactionPopUp("Eingang...", "Wo kommt es her?",  $("#possible_ingoing_locations"));
//	createPopUp("Eingang...", "message");
	createPopUpButtons("Verbuchen", "Abbrechen");
	$("#confirm_pop_up_button").on("click", confirmIngoingTransaction);
}

function handleOutgoing() {
	createTransactionPopUp("Ausgang...", "Wo geht es hin?", $("#possible_outgoing_locations"));
	createPopUpButtons("Verbuchen", "Abbrechen");
	$("#confirm_pop_up_button").on("click", confirmOutgoingTransaction);
}

function confirmIngoingTransaction() {
	$("#confirm_pop_up_button").off("click", confirmIngoingTransaction);
	var quantity = $("#quantity").val();
	var from = $("#location").val();
	var to = $("#current_location").text();
	sendTransaction(quantity, from, to);
	location.href = location.href;
}
function confirmOutgoingTransaction() {
	$("#confirm_pop_up_button").off("click", confirmOutgoingTransaction);
	var quantity = $("#quantity").val();
	var from = $("#current_location").text();
	var to = $("#location").val();
	sendTransaction(quantity, from, to);
	location.href = location.href;
}

function sendTransaction(quantity, from, to) {
	$.ajax({
		type: "POST",
		url: "CommitTransaction",
		data: { from: from,
				to: to, 
				quantity: quantity },
		cache: false,
		success: function(data, settings, xhr) {
			alert("success, transaction was sent");
			var content = xhr.getResponseHeader('content');
			
		},
		error: function(data, settings, xhr) {
			alert("error message = " + xhr.getResponseHeader('error_message'));
//			$("#pop_up_message").html(xhr.getResponseHeader('error_message'));
		}
	});
}

