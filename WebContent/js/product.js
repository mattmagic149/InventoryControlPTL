// JavaScript Document

var product = new Object();
var is_new_product = false;
var restrictions_array = [];

$(document).ready(function() {
	generateBarCode();
	
	if ($("#barcode_picture").hasClass("hidden")) {
		is_new_product = true;
		$("#ok").show();
		createInputFieldsChecker();		
	} else {
		$("#ingoing").on("click", handleIngoing);
		$("#outgoing").on("click", handleOutgoing);	
		$("#edit").on("click", activateProductEditing);		
		fillProductWithDataBecauseOfTextFields();
	}
	
	$("#wrapper").on("click", "#no_restriction", handleClickOnNoRestrictions);
	$("#wrapper").on("click", "#ok", confirmProductEditing);
	handleClickOnNoRestrictions();/*set correct state*/
});

function handleClickOnNoRestrictions() {
	if ($("#no_restriction").is(":checked")) {
		$(".restriction_container").hide();
	} else {
		$(".restriction_container").show();
	}
}

function fillProductWithDataBecauseOfTextFields() {
	product.id = $("#product").attr("value");
	product.name = $("#product_name").text();
	product.description = $("#product_description").text();
	product.minimum_limit = $("#product_minimum_limit").text();
	product.lager_quantity = $("#product_lager_quantity").text();
	product.unity = $(".product_unity").get(0).innerHTML;
	product.state = $("#product_state").text();
	
	console.log("fillProductWithDataBecauseOfTextFields: Product = '" + JSON.stringify(product) + "'");
}

function fillProductWithDataBecauseOfInputFields() {
	product.id = $("#product").attr("value");
	product.name = $("#product_name").val();
	product.description = $("#product_description").val();
	product.minimum_limit = $("#product_minimum_limit").val();
	product.unity = new Object();
	product.unity.name = $("#product_unity").val();
	if ($("#product_state").is(":checked")) {
		product.state = "ACTIVE";
	} else {
		product.state = "INACTIVE";
	}
	
	$(".restriction").each(function( index ) {
		if ($(this).is(":checked")) {
			var truck = new Object();
			truck.id = $(this).attr("lkw_id");
			restrictions_array.push(truck);
		}
	});
	product.trucks_to_restrict = restrictions_array;
	
	if ($("#no_restriction").is(":checked")){
		product.restriction = "NO";
		product.trucks_to_restrict = [];
	} else {
		product.restriction = "YES";
	}

	console.log("fillProductWithDataBecauseOfInputFields: Product = '" + JSON.stringify(product) + "'");
}

function activateProductEditing() {
	$("#ok").show();
	$("#edit").hide();

	fillProductWithDataBecauseOfTextFields();

	$("#product").find(".description").remove();
	$("#product").find(".value").remove();
	$("#ingoing").hide();
	$("#outgoing").hide();
	$("#barcode_picture").hide();

	var content = $('<div class="description">Produkt ID:</div>' +
				'<div class="value" id="product_id">' + product.id + '</div>' +
				'<div class="description">Produktname:</div>' +
				'<input type="text" class="value editable text" id="product_name" value="'+ product.name +'"></input>' + 
				'<div class="description">Beschreibung:</div>' +
				'<textarea type="text" class="value editable" id="product_description">' + product.description + '</textarea>' +
				'<div class="description">Mindestmenge im Lager:</div>' +
				'<input type="number" class="value editable" id="product_minimum_limit" value="' + product.minimum_limit + '"></input>' +
				'<select class="value" id="product_unity">' +
				'<option value="Stück">Stück</option>' +
				'<option value="Liter">Liter</option>' +
				'<option value="Paar">Paar</option>' +
				'<option value="Packung">Packung</option>' +
				'<option value="Rolle">Rolle</option>' +
				'</select>' + 
				'<div class="description">Aktives Produkt:<input type="checkbox" id="product_state"></input></div>' +
				'<div class="description" id="product_restrictions_container">Produkt darf nur in folgende LKWs:</div>'
				);
	content.insertAfter("#barcode_picture");
	
	/*set some fields*/
	if (product.state == "ACTIVE") {
		$("#product_state").prop("checked", true);		
	} else {
		$("#product_state").prop("checked", false);		
	}
	var restrictions = $("#restrictions").children();
	restrictions.insertAfter("#product_restrictions_container");
	
	$("#product_unity").val(product.unity).change();
	
	createInputFieldsChecker();
}

function confirmProductEditing() {
	if(!(checkAllInputFields())) {
		console.log("confirmProductEditing: checkAllInputFields detected a problem");
		return;
	}
	
	fillProductWithDataBecauseOfInputFields();
	
	if (is_new_product) {
		product.id = "0";
		var product_string = JSON.stringify(product);
		sendProductToServer("Add", product_string);
	} else {
		var product_string = JSON.stringify(product);
		sendProductToServer("Edit", product_string);		
	}
}

function generateBarCode() {
	var barcode_options = {
					width:  2,
					height: 100,
					quite: 10,
					format: "CODE128",
					displayValue: true,
					font:"monospace",
					textAlign:"center",
					fontSize: 17,
					backgroundColor:"",
					lineColor:"#000"
				}
	var id = $("#product_id").text();
	if (id != "") {
		$("#barcode_picture").JsBarcode(id, barcode_options);			
	}
}


function handleIngoing() {
	createTransactionPopUp("Eingang...", "Wo kommt es her?", $("#possible_ingoing_locations"));
	createPopUpButtons("Verbuchen", "Abbrechen");
	createInputFieldsChecker();
	$("#confirm_pop_up_button").on("click", confirmIngoingTransaction);
}

function handleOutgoing() {
	createTransactionPopUp("Ausgang...", "Wo geht es hin?", $("#possible_outgoing_locations"));
	createPopUpButtons("Verbuchen", "Abbrechen");
	createInputFieldsChecker();
	$("#confirm_pop_up_button").on("click", confirmOutgoingTransaction);
}

function confirmIngoingTransaction() {
	if(!(checkAllInputFields())) {
		console.log("confirmIngoingTransaction: checkAllInputFields has problem");
		return;
	}
		
	var quantity = $("#quantity").val();
	var from = $("#location").val();
	var to = $("#current_location").text();
	var quantity_of_that_location = parseInt($("#location").find(":selected").attr("quantity"));
	
	if (quantity > quantity_of_that_location) {
		console.log("confirmIngoingTransaction: quantity > quantity_of_that_location");
		createNotification("Error", "Es sind nur " + quantity_of_that_location + " " + $(".product_unity").first().text() + " vorhanden", "failure");
		return;
	}
	
	$("#confirm_pop_up_button").off("click", confirmIngoingTransaction);

	sendTransaction(quantity, from, to);
}
function confirmOutgoingTransaction() {
	if(!(checkAllInputFields())) {
		console.log("confirmOutgoingTransaction: checkAllInputFields has problem");
		return;
	}
	
	var quantity = parseInt($("#quantity").val());
	var from = $("#current_location").text();
	var to = $("#location").val();
	var lager_quantity = parseInt($("#product_lager_quantity").text());

	if (quantity > lager_quantity) {
		console.log("confirmIngoingTransaction: quantity > lager_quantity");
		createNotification("Error", "Es sind nur " + lager_quantity + " " + $(".product_unity").first().text() + " auf Lager", "failure");
		return;
	}
	if ($("#location").val() == "selection") {
		console.log("confirmIngoingTransaction: no target selected");
		createNotification("Error", "Kein Ziel ausgewählt", "failure");
		return;		
	}
	
	$("#confirm_pop_up_button").off("click", confirmOutgoingTransaction);

	sendTransaction(quantity, from, to);
}

function sendProductToServer(servlet, product_string) {
	console.log("sendProductToServer: Servlet = '" + servlet + "' Product = '" + product_string + "'");
	
	$.ajax({
		type: "POST",
		url: servlet,
		data: { type: "product",
				object: product_string },
		cache: false,
		success: function(data, settings, xhr) {
			var id = xhr.getResponseHeader('id');
			location.href = "ProductDetail?id=" + id;
		},
		error: function(data, settings, xhr) {
			var message = "Fehler beim Senden an den Server";
			createNotification("Error", message, "failure");
		}
	});
}

function sendTransaction(quantity, from, to) {
	var product_id = $("#product").attr("value");

	console.log("sendTransaction: product_id = " + product_id + ": quantity = " + quantity + ", from = " + from + ", to = " + to);
	
	$.ajax({
		type: "POST",
		url: "CommitTransaction",
		data: { product_id: product_id,
				from: from,
				to: to, 
				quantity: quantity },
		cache: false,
		success: function(data, settings, xhr) {
			closeAddingInput();
			location.reload();
		},
		error: function(data, settings, xhr) {
			var message = "Fehler beim Senden der Buchung";
			createNotification("Error", message, "failure");
		}
	});
}

