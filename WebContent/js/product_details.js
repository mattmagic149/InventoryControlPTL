// JavaScript Document

var product = new Object();
var is_new_product = false;
var restrictions_array = [];

$(document).ready(function() {
	generateBarCode();
	
	if ($("#barcode_picture").hasClass("hidden")) {
		is_new_product = true;
		$("#ok").show();
//		activateProductEditing();
	} else {
		$("#ingoing").on("click", handleIngoing);
		$("#outgoing").on("click", handleOutgoing);	
		$("#edit").on("click", activateProductEditing);		
		fillProductWithDataBecauseOfTextFields();
	}

	$("#wrapper").on("click", "#ok", confirmProductEditing);

});

function fillProductWithDataBecauseOfTextFields() {
	product.id = $("#product_id").text();
	product.name = $("#product_name").text();
	product.description = $("#product_description").text();
	product.minimum_limit = $("#product_minimum_limit").text();
	product.lkw_ids = [1,2,3];
	product.lager_quantity = $("#product_lager_quantity").text();
	product.unity = $(".product_unity").get(0).innerHTML;
	product.state = $("#product_state").text();
}

function fillProductWithDataBecauseOfInputFields() {
	product.id = $("#product_id").text();
	product.name = $("#product_name").val();
	product.description = $("#product_description").val();
	product.minimum_limit = $("#product_minimum_limit").val();
	product.lkw_ids = [1,2,3];
	product.unity = $("#product_unity").val();
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
	
	//alert("unity = " + product.unity);
}

function activateProductEditing() {
	$("#ok").show();
	fillProductWithDataBecauseOfTextFields();
	$("#product").find(".description").remove();
	$("#product").find(".value").remove();
	$("#ingoing").hide();
	$("#outgoing").hide();
	$("#barcode_picture").hide();

	var content = $('<div class="description">Produkt ID:</div>' +
				'<div class="value" id="product_id">' + product.id + '</div>' +
				'<div class="description">Produktname:</div>' +
				'<input type="text" class="value editable" id="product_name" value="'+ product.name +'"></input>' + 
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
	if (product.state == "ACTIVE") {
		$("#product_state").prop("checked", true);		
	} else {
		$("#product_state").prop("checked", false);		
	}
	var restrictions = $("#restrictions").children().clone();
	restrictions.insertAfter("#product_restrictions_container");
	
	$("#product_unity").val(product.unity).change();
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
		product.id = "P-000000";
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

